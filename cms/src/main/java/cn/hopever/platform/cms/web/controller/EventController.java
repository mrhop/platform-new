package cn.hopever.platform.cms.web.controller;

import cn.hopever.platform.cms.config.CommonMethods;
import cn.hopever.platform.cms.service.ArticleTableService;
import cn.hopever.platform.cms.service.ArticleTagTableService;
import cn.hopever.platform.cms.service.TemplateTableService;
import cn.hopever.platform.cms.vo.ArticleVo;
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
@RequestMapping(value = "/event", produces = "application/json")
public class EventController implements GenericController<ArticleVo> {
    Logger logger = LoggerFactory.getLogger(EventController.class);
    @Autowired
    private ArticleTableService articleTableService;
    @Autowired
    private ArticleTagTableService articleTagTableService;
    @Autowired
    private TemplateTableService templateTableService;

    @Override
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestBody TableParameters body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Map filter = CommonMethods.generateInitFilter(body.getFilters(), httpServletRequest);
        if (filter != null && filter.containsKey("websiteId")) {
            body.setFilters(filter);
            Page<ArticleVo> list = articleTableService.getEventList(body, principal);
            Map<String, Object> map = new HashMap<>();
            List<HashMap<String, Object>> listReturn = null;
            if (list != null && list.iterator().hasNext()) {
                listReturn = new ArrayList<>();
                for (ArticleVo cv : list) {
                    HashMap<String, Object> mapTemp = new HashMap<>();
                    mapTemp.put("key", cv.getId());
                    List<Object> listTmp = new ArrayList<>();
                    listTmp.add(cv.getTitle());
                    listTmp.add(cv.getAliasUrl());
                    listTmp.add(cv.getArticleTagsStr());
                    listTmp.add(cv.isPublished());
                    listTmp.add(cv.getPublishDate());
                    listTmp.add(cv.getCreateUser());
                    listTmp.add(cv.getCreatedDate());
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
    public ArticleVo info(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return articleTableService.info(key, principal);
    }

    @Override
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam Long key, @RequestBody ArticleVo articleVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        articleVo.setId(key);
        return articleTableService.updateEvent(articleVo, principal);
    }

    @Override
    public VueResults.Result update(@RequestParam(name = "key") Long key, @RequestParam(name = "files", required = false) MultipartFile[] files, ArticleVo articleVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestBody ArticleVo articleVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Map<String, Long> keys = CommonMethods.generateKey(httpServletRequest);
        if (keys.get("websiteId") != null) {
            articleVo.setWebsiteId(keys.get("websiteId"));
            return articleTableService.saveEvent(articleVo, principal);
        }
        return VueResults.generateError("创建错误", "无法找到正确从属网站");
    }

    @Override
    public VueResults.Result save(@RequestParam(name = "screenshots", required = false) MultipartFile[] files, ArticleVo articleVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        articleTableService.delete(key, principal);
    }

    @Override
    @RequestMapping(value = "/form/rulechange", method = {RequestMethod.GET, RequestMethod.POST})
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }
}
