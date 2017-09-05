package cn.hopever.platform.user.web.controller;

import cn.hopever.platform.user.domain.RoleTable;
import cn.hopever.platform.user.service.RoleTableService;
import cn.hopever.platform.utils.web.TableParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Donghui Huo on 2016/8/29.
 */
@RestController
@RequestMapping(value = "/role", produces = "application/json")
public class RoleController {
    Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    private RoleTableService roleTableService;


    @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Map getList(@RequestBody TableParameters body) {
        Page<RoleTable> list = roleTableService.getList(body);
        Map<String, Object> map = new HashMap<>();
        List<HashMap<String, Object>> listReturn = null;
        if (list != null && list.iterator().hasNext()) {
            listReturn = new ArrayList<>();
            for (RoleTable rt : list) {
                HashMap<String, Object> mapTemp = new HashMap<>();
                mapTemp.put("key", rt.getId());
                List<Object> listTmp = new ArrayList<>();
                listTmp.add(rt.getName());
                listTmp.add(rt.getAuthority());
                listTmp.add(rt.getLevel());
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
}
