package cn.hopever.platform.user.web.controller;

import cn.hopever.platform.user.domain.ClientTable;
import cn.hopever.platform.user.domain.RoleTable;
import cn.hopever.platform.user.service.ClientRoleTableService;
import cn.hopever.platform.user.service.ClientTableService;
import cn.hopever.platform.user.service.RoleTableService;
import cn.hopever.platform.user.vo.ClientVoAssembler;
import cn.hopever.platform.utils.json.JacksonUtil;
import cn.hopever.platform.utils.test.PrincipalSample;
import cn.hopever.platform.utils.web.TableParameters;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

/**
 * Created by Donghui Huo on 2016/8/29.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/client", produces = "application/json")
public class ClientController {
    Logger logger = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    private ClientTableService clientTableService;

    @Autowired
    private ClientRoleTableService clientRoleTableService;

    @Autowired
    private RoleTableService roleTableService;

    @Autowired
    private ClientVoAssembler clientVoAssembler;


    @PreAuthorize("hasRole('ROLE_super_admin') or hasRole('ROLE_admin')")
    @RequestMapping(value = "/list/options", method = {RequestMethod.POST})
    public Map getListOptions(@RequestBody JsonNode body, Principal principal) {
        Map mapReturn = null;
        List listOptions = null;
        List listOptionsSelected = null;
        String authority = ((OAuth2Authentication) principal).getAuthorities().iterator().next().getAuthority();
        Long roleId = body.get("roleId").asLong();
        RoleTable rt = roleTableService.get(roleId);
        String roleName = rt != null ? rt.getAuthority() : null;
        Long userId = null;
        if (body.get("userId") != null && !body.get("userId").isNull()) {
            userId = body.get("userId").asLong();
        }
        Iterable<ClientTable> list = null;
        List<ClientTable> listSelected = null;
        List<ClientTable> listSelectedUpdated = new ArrayList<>();
        //然后根据roleName
        if ("ROLE_admin".equals(roleName) || "ROLE_common_user".equals(roleName)) {
            //do the get
            if (authority.equals("ROLE_super_admin")) {
                //get all client
                list = clientTableService.getAll();
            } else {
                list = clientTableService.getByUserName(principal.getName());
            }
            if (userId != null) {
                listSelected = clientTableService.getByUserId(userId);
            }
        }
        if (list != null && list.iterator().hasNext()) {
            listOptions = new ArrayList<>();
            for (ClientTable ct : list) {
                if (!"user_admin_client".equals(ct.getClientId())) {
                    Map mapOption = new HashMap<>();
                    mapOption.put("label", ct.getClientName());
                    mapOption.put("value", ct.getId());
                    listOptions.add(mapOption);
                    if (listSelected != null && listSelected.contains(ct)) {
                        listSelectedUpdated.add(ct);
                    }
                }
            }
        }

        if (listSelectedUpdated.size() > 0) {
            listOptionsSelected = new ArrayList<>();
            for (ClientTable ct : listSelectedUpdated) {
                for (ClientTable ctOption : list) {
                }
                listOptionsSelected.add(ct.getId());
            }
        }
        if (listOptions != null) {
            mapReturn = new HashMap<>();
            mapReturn.put("clients", listOptions);
            mapReturn.put("clientsSelected", listOptionsSelected);
        }
        return mapReturn;
    }

    @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/list/module/options", method = {RequestMethod.GET})
    public List getListOptionsForModules() {
        List listOptions = null;
        Iterable<ClientTable> list = clientTableService.getAll();
        if (list != null && list.iterator().hasNext()) {
            listOptions = new ArrayList<>();
            for (ClientTable ct : list) {
                if (!"user_admin_client".equals(ct.getClientId())) {
                    Map mapOption = new HashMap<>();
                    mapOption.put("label", ct.getClientName());
                    mapOption.put("value", ct.getId());
                    listOptions.add(mapOption);
                }
            }
        }
        return listOptions;
    }


    // @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestBody TableParameters body, Principal principal) {
        principal = new PrincipalSample("admin");
        Page<ClientTable> list = clientTableService.getList(body);
        Map<String, Object> map = new HashMap<>();
//        List<HashMap<String, Object>> listReturn = null;
//        if (list != null && list.iterator().hasNext()) {
//            listReturn = new ArrayList<>();
//            for (ClientTable ut : list) {
//                HashMap<String, Object> mapTemp = new HashMap<>();
//                mapTemp.put("key", ut.getId());
//                List<Object> listTmp = new ArrayList<>();
//                listTmp.add(ut.getUsername());
//                listTmp.add(ut.getName());
//                listTmp.add(ut.getEmail());
//                listTmp.add(ut.getPhone());
//                listTmp.add(ut.isEnabled());
//                listTmp.add(ut.getCreateUser() == null ? null : ut.getCreateUser().getUsername());
//                listTmp.add(ut.getCreatedDate() != null ? ut.getCreatedDate().getTime() : null);
//                mapTemp.put("value", listTmp);
//                listReturn.add(mapTemp);
//            }
//            map.put("rows", listReturn);
//            map.put("totalCount", list.getTotalElements());
//
//        } else {
//            map.put("rows", null);
//            map.put("totalCount", 0);
//        }
//        map.put("pager", body.getPager());
//        map.put("filters", body.getFilters());
//        map.put("sorts", body.getSorts());
        return map;
    }

    @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/info", method = {RequestMethod.GET})
    public Map info(@RequestParam Long id, Principal principal) {
        //返回user是无法解析的，要使用对象解析为map 的形式
        ClientTable ct = clientTableService.getById(id);
        return JacksonUtil.mapper.convertValue(clientVoAssembler.toResource(ct), Map.class);
    }


    @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public Map updateClient(@RequestBody JsonNode body, Principal principal) {
        Map map = JacksonUtil.mapper.convertValue(body.get("data"), Map.class);
        ClientTable ct = clientTableService.getById(Long.valueOf(map.get("id").toString()));
        if (ct.getClientId().equals("user_admin_client")) {
            return null;
        }
        if (body.get("data").get("clientName") != null && !body.get("data").get("clientName").isNull()) {
            ct.setClientName(body.get("data").get("clientName").textValue());
        }
        if (body.get("data").get("clientSecret") != null && !body.get("data").get("clientSecret").isNull()) {
            ct.setClientSecret(body.get("data").get("clientSecret").textValue());
        }
        if (body.get("data").get("authorizedGrantTypes") != null && !body.get("data").get("authorizedGrantTypes").isNull()) {
            Set set = JacksonUtil.mapper.convertValue(body.get("data").get("authorizedGrantTypes"), Set.class);
            if (set.contains("authorization_code")) {
                set.add("refresh_token");
            } else {
                set.remove("refresh_token");
            }
            ct.setAuthorizedGrantTypes(set);
        } else {
            ct.setAuthorizedGrantTypes("");
        }
        boolean isInternalClient = false;
        if (body.get("data").get("internalClient") != null && !body.get("data").get("internalClient").isNull()) {
            List<Boolean> b = JacksonUtil.mapper.convertValue(body.get("data").get("internalClient"), List.class);
            if (b.size() > 0) {
                isInternalClient = b.get(0);
            }
        }

        clientTableService.save(ct);
        return null;
    }

    @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Map saveUser(@RequestBody JsonNode body, Principal principal) {
        Map map = JacksonUtil.mapper.convertValue(body.get("data"), Map.class);
        ClientTable ct = new ClientTable();
        if (body.get("data").get("clientId") != null && !body.get("data").get("clientId").isNull()) {
            if (clientTableService.getByClientId(body.get("data").get("clientId").asText()) != null) {
                Map mapReturn = new HashMap<>();
                mapReturn.put("message", "客户端账户已存在");
                return mapReturn;
            }
            ct.setClientId(body.get("data").get("clientId").textValue());
        }
        if (body.get("data").get("clientName") != null && !body.get("data").get("clientName").isNull()) {
            ct.setClientName(body.get("data").get("clientName").textValue());
        }
        if (body.get("data").get("clientSecret") != null && !body.get("data").get("clientSecret").isNull()) {
            ct.setClientSecret(body.get("data").get("clientSecret").textValue());
        }
        if (body.get("data").get("authorizedGrantTypes") != null && !body.get("data").get("authorizedGrantTypes").isNull()) {
            Set set = JacksonUtil.mapper.convertValue(body.get("data").get("authorizedGrantTypes"), Set.class);
            if (set.contains("authorization_code")) {
                set.add("refresh_token");
            } else {
                set.remove("refresh_token");
            }
            ct.setAuthorizedGrantTypes(set);
        } else {
            ct.setAuthorizedGrantTypes("");
        }
        boolean isInternalClient = false;
        if (body.get("data").get("internalClient") != null && !body.get("data").get("internalClient").isNull()) {
            List<Boolean> b = JacksonUtil.mapper.convertValue(body.get("data").get("internalClient"), List.class);
            if (b.size() > 0) {
                isInternalClient = b.get(0);
            }
        }
        clientTableService.save(ct);
        return null;
    }

    @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long id, Principal principal) {
        ClientTable ct = this.clientTableService.getById(id);
        if (!ct.getClientId().equals("user_admin_client")) {
            this.clientTableService.delete(ct);
        }
    }
}
