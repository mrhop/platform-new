package cn.hopever.platform.user.web.controller;

import cn.hopever.platform.user.service.ResourceScopeTableService;
import cn.hopever.platform.user.vo.ResourceScopeVo;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
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
 * Created by Donghui Huo on 2017/8/18.
 */
@RestController
@RequestMapping(value = "/resourcescope", produces = "application/json")
public class ResourceScopeController {
    Logger logger = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    private ResourceScopeTableService resourceScopeTableService;

    @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestBody TableParameters body) {
        Page<ResourceScopeVo> list = resourceScopeTableService.getList(body);
        Map<String, Object> map = new HashMap<>();
        List<HashMap<String, Object>> listReturn = null;
        if (list != null && list.iterator().hasNext()) {
            listReturn = new ArrayList<>();
            for (ResourceScopeVo cv : list) {
                HashMap<String, Object> mapTemp = new HashMap<>();
                mapTemp.put("key", cv.getId());
                List<Object> listTmp = new ArrayList<>();
                listTmp.add(cv.getName());
                listTmp.add(cv.getScopeId());
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

    @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/info", method = {RequestMethod.GET})
    public ResourceScopeVo info(@RequestParam Long key) {
        //返回user是无法解析的，要使用对象解析为map 的形式
        return resourceScopeTableService.getById(key);
    }


    @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam Long key, @RequestBody ResourceScopeVo resourceScopeVo) {
        resourceScopeVo.setId(key);
        return resourceScopeTableService.update(resourceScopeVo);
    }

    @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestBody ResourceScopeVo resourceScopeVo) {
        return resourceScopeTableService.save(resourceScopeVo);
    }

    @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key) {
        this.resourceScopeTableService.delete(key);
    }

}
