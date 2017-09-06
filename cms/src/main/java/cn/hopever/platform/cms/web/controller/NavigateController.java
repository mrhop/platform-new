package cn.hopever.platform.cms.web.controller;

import cn.hopever.platform.cms.service.NavigateTableService;
import cn.hopever.platform.cms.vo.NavigateVo;
import cn.hopever.platform.utils.web.GenericController;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Donghui Huo on 2017/9/5.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/navigate", produces = "application/json")
public class NavigateController implements GenericController<NavigateVo> {
    Logger logger = LoggerFactory.getLogger(NavigateController.class);
    @Autowired
    private NavigateTableService navigateTableService;

    @Override
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestBody TableParameters body, Principal principal) {
        Page<NavigateVo> list = navigateTableService.getList(body, principal);
        Map<String, Object> map = new HashMap<>();
        List<HashMap<String, Object>> listReturn = null;
        if (list != null && list.iterator().hasNext()) {
            listReturn = new ArrayList<>();
            for (NavigateVo cv : list) {
                HashMap<String, Object> mapTemp = new HashMap<>();
                mapTemp.put("key", cv.getId());
                List<Object> listTmp = new ArrayList<>();
                listTmp.add(cv.getName());
                listTmp.add(cv.getParentId());
                listTmp.add(cv.getNavigateOrder());
                listTmp.add(cv.getWebsiteName());
                listTmp.add(cv.getArticleTitle());
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

    @Override
    @RequestMapping(value = "/info", method = {RequestMethod.GET})
    public NavigateVo info(@RequestParam Long key, Principal principal) {
        return navigateTableService.info(key, principal);
    }

    @Override
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam Long key, @RequestBody NavigateVo navigateVo, Principal principal) {
        navigateVo.setId(key);
        return navigateTableService.update(navigateVo, null, principal);
    }

    @Override
    public VueResults.Result update(@RequestParam(name = "key") Long key, @RequestParam(name = "screenshots", required = false) MultipartFile[] files, NavigateVo navigateVo, Principal principal) {
        return null;
    }

    @Override
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestBody NavigateVo navigateVo, Principal principal) {
        return navigateTableService.save(navigateVo, null, principal);
    }

    @Override
    public VueResults.Result save(@RequestParam(name = "screenshots", required = false) MultipartFile[] files, NavigateVo navigateVo, Principal principal) {
        return null;
    }

    @Override
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key, Principal principal) {
        navigateTableService.delete(key, principal);
    }

    @Override
    @RequestMapping(value = "/form/rulechange", method = {RequestMethod.GET, RequestMethod.POST})
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body, Principal principal) {
        //首先parenttree，before select
        Map mapReturn = new HashMap<>();
        if (body != null) {
            if (body.get("websiteId") != null) {
                Long websiteId = Long.valueOf(body.get("websiteId").toString());
                mapReturn.put("parentTree", navigateTableService.getParentsOptions(websiteId, key));
                if("form".equals(body.get("type"))){
                    Long parentId = null;
                    if (body.get("parentId") != null) {
                        parentId = Long.valueOf(body.get("websiteId").toString());
                    }
                    mapReturn.put("beforeNavigates", navigateTableService.getBeforeOptions(parentId, websiteId, key));
                }
            }
        }
        return mapReturn;
    }
}
