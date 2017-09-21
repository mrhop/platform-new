package cn.hopever.platform.cms.web.controller;

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
@RequestMapping(value = "/news", produces = "application/json")
public class NewsController implements GenericController<ArticleVo> {

    Logger logger = LoggerFactory.getLogger(NewsController.class);
    @Autowired
    private ArticleTableService articleTableService;
    @Autowired
    private ArticleTagTableService articleTagTableService;
    @Autowired
    private TemplateTableService templateTableService;

    @Override
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestBody TableParameters body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Page<ArticleVo> list = articleTableService.getNewsList(body, principal);
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
                listTmp.add(cv.getWebsiteName());
                listTmp.add(cv.getTemplateId());
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

    @Override
    @RequestMapping(value = "/info", method = {RequestMethod.GET})
    public ArticleVo info(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return articleTableService.info(key, principal);
    }

    @Override
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam Long key, @RequestBody ArticleVo articleVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        articleVo.setId(key);
        return articleTableService.updateNews(articleVo, principal);
    }

    @Override
    public VueResults.Result update(@RequestParam(name = "key") Long key, @RequestParam(name = "files", required = false) MultipartFile[] files, ArticleVo articleVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestBody ArticleVo articleVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return articleTableService.saveNews(articleVo, principal);
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
        // TAG 进行过滤，
        // 其他，当有templateId的时候，需要变动其下面的position，预览如何处理，直接放置在前端的关联id里面，不更新页面，则不作处理，当结束页面时，将localstorage里面的数据删除
        Map mapReturn = new HashMap<>();
        if (body != null) {
            if (body.get("websiteId") != null) {
                mapReturn.put("articleTags", articleTagTableService.getArticleTagOptions(Long.valueOf(body.get("websiteId").toString()), principal));
                mapReturn.put("templates", templateTableService.getOptionsByWebsiteId(Long.valueOf(body.get("websiteId").toString())));
            }
            if (body.get("templateId") != null) {
                //此处需要是否在前端处理，另给一个panel（tab，【div 块】【点击第二个tab块的内容修改】，【panel右上角的全屏和预览】），但是在同一个页面，就像我们这个关联的static resource【这个在panel中是一个列表，点击后，panel隐藏，同时新增panel显示】
                //这种思路同样可以放在website和theme中以及template中
            }
        }
        return mapReturn;
    }
}
