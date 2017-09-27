package cn.hopever.platform.cms.web.controller;

import cn.hopever.platform.cms.config.CommonMethods;
import cn.hopever.platform.cms.service.ArticleTableService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private ArticleTableService articleTableService;

    @Override
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestBody TableParameters body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Map filter = CommonMethods.generateInitFilter(body.getFilters(), httpServletRequest);
        if (filter != null && filter.containsKey("websiteId")) {
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
                    listTmp.add(cv.getArticleTitle());
                    listTmp.add(cv.getParentName());
                    listTmp.add(cv.isActivated());
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
        return null;
    }

    @Override
    @RequestMapping(value = "/info", method = {RequestMethod.GET})
    public NavigateVo info(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return navigateTableService.info(key, principal);
    }

    @Override
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam Long key, @RequestBody NavigateVo navigateVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        navigateVo.setId(key);
        return navigateTableService.update(navigateVo, null, principal);
    }

    @Override
    public VueResults.Result update(@RequestParam(name = "key") Long key, @RequestParam(name = "screenshots", required = false) MultipartFile[] files, NavigateVo navigateVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestBody NavigateVo navigateVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Map<String, Long> keys = CommonMethods.generateKey(httpServletRequest);
        if (keys.get("websiteId") != null) {
            navigateVo.setWebsiteId(keys.get("websiteId"));
            return navigateTableService.save(navigateVo, null, principal);
        }
        return VueResults.generateError("创建失败", "导航必须和网站关联");
    }

    @Override
    public VueResults.Result save(@RequestParam(name = "screenshots", required = false) MultipartFile[] files, NavigateVo navigateVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        navigateTableService.delete(key, principal);
    }

    @Override
    @RequestMapping(value = "/form/rulechange", method = {RequestMethod.GET, RequestMethod.POST})
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        //首先parenttree，before select
        Map<String, Long> keys = CommonMethods.generateKey(httpServletRequest);
        if (keys.get("websiteId") != null) {
            Long websiteId = keys.get("websiteId");
            Map mapReturn = new HashMap<>();
            boolean changed = httpServletRequest.getParameter("changed") == null ? false : Boolean.valueOf(httpServletRequest.getParameter("changed"));
            Long parentId = null;
            if (httpServletRequest.getParameter("parentId") != null) {
                parentId = Long.valueOf(httpServletRequest.getParameter("parentId"));
            }
            if (!changed) {
                mapReturn.put("parentTree", navigateTableService.getParentsOptions(websiteId, key));
                mapReturn.put("beforeNavigates", navigateTableService.getBeforeOptions(parentId, websiteId, key));
            }
            if ("form".equals(httpServletRequest.getParameter("type"))) {
                if (httpServletRequest.getParameter("relateType") != null) {
                    Short relateType = Short.valueOf(httpServletRequest.getParameter("relateType"));
                    mapReturn.put("articles", articleTableService.getArticleOptionsForNavigate(relateType, websiteId));
                } else {

                    mapReturn.put("beforeNavigates", navigateTableService.getBeforeOptions(parentId, websiteId, key));
                }
            }
            return mapReturn;
        }
        return null;
    }

    @RequestMapping(value = "/activate", method = {RequestMethod.GET})
    public VueResults.Result updateActivated(@RequestParam Long key, @RequestParam Boolean activated) {
        return navigateTableService.updateActivated(key, activated);
    }
}
