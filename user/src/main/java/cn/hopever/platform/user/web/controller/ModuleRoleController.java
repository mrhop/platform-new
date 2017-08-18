package cn.hopever.platform.user.web.controller;

import cn.hopever.platform.user.domain.ClientTable;
import cn.hopever.platform.user.domain.ModuleRoleTable;
import cn.hopever.platform.user.domain.ModuleTable;
import cn.hopever.platform.user.domain.RoleTable;
import cn.hopever.platform.user.service.ClientTableService;
import cn.hopever.platform.user.service.ModuleRoleTableService;
import cn.hopever.platform.user.service.ModuleTableService;
import cn.hopever.platform.user.service.RoleTableService;
import cn.hopever.platform.user.vo.ModuleRoleVo;
import cn.hopever.platform.user.vo.ModuleRoleVoAssembler;
import cn.hopever.platform.utils.json.JacksonUtil;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Donghui Huo on 2016/11/14.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/modulerole", produces = "application/json")
public class ModuleRoleController {

    Logger logger = LoggerFactory.getLogger(ModuleRoleController.class);
    @Autowired
    private ModuleRoleTableService moduleRoleTableService;

    @Autowired
    private ModuleTableService moduleTableService;

    @Autowired
    private RoleTableService roleTableService;

    @Autowired
    private ClientTableService clientTableService;

    @Autowired
    ModuleRoleVoAssembler moduleRoleVoAssembler;


    @PreAuthorize("hasRole('ROLE_super_admin') or hasRole('ROLE_admin')")
    @RequestMapping(value = "/list/options", method = {RequestMethod.POST})
    public Map getListOptions(@RequestBody JsonNode body, Principal principal) {
        Map mapReturn = null;
        List listOptions = null;
        List listOptionsSelected = null;
        RoleTable roleTable = roleTableService.get(body.get("roleId").asLong());
        String authority = roleTable.getAuthority();
        if ("ROLE_common_user".equals(authority)) {
            List<ModuleRoleTable> list = moduleRoleTableService.getByClients(JacksonUtil.mapper.convertValue(body.get("clientIds"), List.class));
            Long userId = null;
            if (body.get("userId") != null && !body.get("userId").isNull()) {
                userId = body.get("userId").asLong();
            }
            List<ModuleRoleTable> listSelected = null;
            List<ModuleRoleTable> listSelectedUpdate = new ArrayList<>();
            if (userId != null) {
                listSelected = moduleRoleTableService.getByUserId(userId);
            }
            if (list.size() > 0) {
                listOptions = new ArrayList<>();
                for (ModuleRoleTable mrt : list) {
                    Map mapOption = new HashMap<>();
                    mapOption.put("label", mrt.getName());
                    mapOption.put("value", mrt.getId());
                    listOptions.add(mapOption);
                    if (listSelected != null && listSelected.contains(mrt)) {
                        listSelectedUpdate.add(mrt);
                    }
                }
            }

            if (listSelectedUpdate.size() > 0) {
                listOptionsSelected = new ArrayList<>();
                for (ModuleRoleTable mrt : listSelectedUpdate) {
                    listOptionsSelected.add(mrt.getId());
                }
            }
            if (listOptions != null) {
                mapReturn = new HashMap<>();
                mapReturn.put("moduleRoles", listOptions);
                mapReturn.put("moduleRolesSelected", listOptionsSelected);
            }
        }
        return mapReturn;
    }

    @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/list/module/options", method = {RequestMethod.POST})
    public Map getListOptionsOfModule(@RequestBody JsonNode body, Principal principal) {
        Map mapReturn = null;
        List listOptions = null;
        List listOptionSelected = null;
        ClientTable ct = clientTableService.getById(body.get("clientId").asLong());
        List<ModuleRoleTable> list = ct.getModuleRoles();
        if (list != null && list.size() > 0) {
            listOptions = new ArrayList<>();
            for (ModuleRoleTable mrt : list) {
                Map mapOption = new HashMap<>();
                mapOption.put("label", mrt.getName());
                mapOption.put("value", mrt.getId());
                listOptions.add(mapOption);
            }
        }
        if (body.get("moduleId") != null && !body.get("moduleId").isNull()) {
            Long moduleId = body.get("moduleId").asLong();
            ModuleTable mt = this.moduleTableService.getById(moduleId);
            if (mt.getAuthorities() != null) {
                listOptionSelected = new ArrayList<>();
                for (ModuleRoleTable mrt : mt.getAuthorities()) {
                    if (list.contains(mrt)) {
                        listOptionSelected.add(mrt.getId());
                    }
                }
            }
        }
        if (listOptions != null) {
            mapReturn = new HashMap<>();
            mapReturn.put("items", listOptions);
            mapReturn.put("defaultValue", listOptionSelected);
        }
        return mapReturn;
    }


    //@PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestBody TableParameters body) {
        Page<ModuleRoleVo> list = moduleRoleTableService.getList(body);
        Map<String, Object> map = new HashMap<>();
        List<HashMap<String, Object>> listReturn = null;
        if (list != null && list.iterator().hasNext()) {
            listReturn = new ArrayList<>();
            for (ModuleRoleVo cv : list) {
                HashMap<String, Object> mapTemp = new HashMap<>();
                mapTemp.put("key", cv.getId());
                List<Object> listTmp = new ArrayList<>();
                listTmp.add(cv.getName());
                listTmp.add(cv.getAuthority());
                listTmp.add(cv.getClientName());
                mapTemp.put("value", listTmp);
                listReturn.add(mapTemp);
            }
            map.put("rows", listReturn);
            map.put("totalCount", list.getTotalElements());

        } else {
            map.put("rows", null);
            map.put("totalCount", 0);
        }
        map.put("pager", body.getPager());
        map.put("filters", body.getFilters());
        map.put("sorts", body.getSorts());
        return map;
    }

    //@PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/info", method = {RequestMethod.GET})
    public ModuleRoleVo info(@RequestParam Long key) {
        return moduleRoleTableService.getById(key);
    }


    // @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result updateUser(@RequestParam Long key, @RequestBody ModuleRoleVo moduleRoleVo) {
        moduleRoleVo.setId(key);
        return moduleRoleTableService.update(moduleRoleVo);
    }

    // @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result saveUser(@RequestBody ModuleRoleVo moduleRoleVo) {
        return moduleRoleTableService.save(moduleRoleVo);
    }

    @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key, Principal principal) {
        this.moduleRoleTableService.deleteById(key);
    }
}
