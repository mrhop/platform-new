package cn.hopever.platform.user.web.controller;

import cn.hopever.platform.user.service.ClientTableService;
import cn.hopever.platform.user.service.ModuleRoleTableService;
import cn.hopever.platform.user.service.ModuleTableService;
import cn.hopever.platform.user.service.RoleTableService;
import cn.hopever.platform.user.vo.ModuleRoleVo;
import cn.hopever.platform.user.vo.ModuleRoleVoAssembler;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public VueResults.Result update(@RequestParam Long key, @RequestBody ModuleRoleVo moduleRoleVo) {
        moduleRoleVo.setId(key);
        return moduleRoleTableService.update(moduleRoleVo);
    }

    // @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestBody ModuleRoleVo moduleRoleVo) {
        return moduleRoleTableService.save(moduleRoleVo);
    }

    // @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key, Principal principal) {
        this.moduleRoleTableService.deleteById(key);
    }

    // @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/form/rulechange", method = {RequestMethod.GET, RequestMethod.POST})
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body) {
        Map mapReturn = new HashMap<>();
        if (body == null) {
            mapReturn.put("clientIds", moduleRoleTableService.getClientsOptions());
            if (key != null) {
                mapReturn.put("moduleIds", moduleRoleTableService.getParentsOptions(null, key));
            }
        } else {
            if (body.get("clientId") != null) {
                mapReturn.put("moduleIds", moduleRoleTableService.getParentsOptions(Long.valueOf(body.get("clientId").toString()), null));
            }
        }
        return mapReturn;
    }

}
