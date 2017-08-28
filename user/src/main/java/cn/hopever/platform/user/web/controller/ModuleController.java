package cn.hopever.platform.user.web.controller;

import cn.hopever.platform.user.service.ClientTableService;
import cn.hopever.platform.user.service.ModuleRoleTableService;
import cn.hopever.platform.user.service.ModuleTableService;
import cn.hopever.platform.user.vo.ModuleVo;
import cn.hopever.platform.user.vo.ModuleVoAssembler;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
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
@RequestMapping(value = "/module", produces = "application/json")
public class ModuleController {

    Logger logger = LoggerFactory.getLogger(ModuleController.class);

    @Autowired
    private ClientTableService clientTableService;

    @Autowired
    private ModuleRoleTableService moduleRoleTableService;

    @Autowired
    private ModuleTableService moduleTableService;


    @Autowired
    ModuleVoAssembler moduleVoAssembler;


    @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestBody TableParameters body) {
        Page<ModuleVo> list = moduleTableService.getList(body);
        Map<String, Object> map = new HashMap<>();
        List<HashMap<String, Object>> listReturn = null;
        if (list != null && list.iterator().hasNext()) {
            listReturn = new ArrayList<>();
            for (ModuleVo cv : list) {
                HashMap<String, Object> mapTemp = new HashMap<>();
                mapTemp.put("key", cv.getId());
                List<Object> listTmp = new ArrayList<>();
                listTmp.add(cv.getModuleName());
                listTmp.add(cv.isActivated());
                listTmp.add(cv.getClientId());
                listTmp.add(cv.getParentName());
                listTmp.add(cv.getAuthoritiesStr());
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
        map.put("sorts", body.getSorts());
        return map;
    }

    @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/info", method = {RequestMethod.GET})
    public ModuleVo info(@RequestParam Long key) {
        return moduleTableService.getById(key);
    }


    @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam Long key, @RequestBody ModuleVo moduleVo) {
        moduleVo.setId(key);
        return moduleTableService.update(moduleVo);
    }


    @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestBody ModuleVo moduleVo) {
        return moduleTableService.save(moduleVo);
    }

    @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key, Principal principal) {
        this.moduleTableService.deleteById(key);
    }


    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/leftmenu", method = {RequestMethod.GET})
    public List leftmenu(Principal principal) {
        return this.moduleTableService.getLeftMenu(principal, "user_admin_client");
    }


    @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/form/rulechange", method = {RequestMethod.GET, RequestMethod.POST})
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body) {
        Map mapReturn = new HashMap<>();
        if (body == null) {
            mapReturn.put("clientIds", moduleTableService.getClientsOptions());
            ModuleVo moduleVo = null;
            if (key != null) {
                moduleVo = moduleTableService.getById(key);
                mapReturn.put("parentTree", moduleTableService.getParentsOptions(moduleVo.getClientId(), key));
                mapReturn.put("beforeIds", moduleTableService.getBeforeOptions(moduleVo.getParentId(), moduleVo.getClientId(), key));
            }
        } else {
            Long clientId = null;
            Long parentId = null;
            if (body.get("clientId") != null) {
                clientId = Long.valueOf(body.get("clientId").toString());
                mapReturn.put("parentTree", moduleTableService.getParentsOptions(clientId, key));
            }
            if (body.get("parentId") != null) {
                parentId = Long.valueOf(body.get("parentId").toString());
            }
            if (parentId != null || clientId != null) {
                mapReturn.put("beforeIds", moduleTableService.getBeforeOptions(parentId, clientId, key));
            }
        }
        return mapReturn;
    }

}
