package cn.hopever.platform.cms.web.controller;

import cn.hopever.platform.cms.config.CommonMethods;
import cn.hopever.platform.cms.service.TemplateTableService;
import cn.hopever.platform.cms.vo.TemplateVo;
import cn.hopever.platform.utils.web.GenericController;
import cn.hopever.platform.utils.web.ModuleAuthorize;
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
@RequestMapping(value = "/template", produces = "application/json")
public class TemplateController implements GenericController<TemplateVo> {
    Logger logger = LoggerFactory.getLogger(TemplateController.class);
    @Autowired
    private TemplateTableService templateTableService;

    @Override
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    @ModuleAuthorize("templateList")
    public Map getList(@RequestBody TableParameters body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Map filter = CommonMethods.generateInitFilter(body.getFilters(), httpServletRequest);
        if (filter != null) {
            body.setFilters(filter);
            Page<TemplateVo> list = templateTableService.getList(body, principal);
            Map<String, Object> map = new HashMap<>();
            List<HashMap<String, Object>> listReturn = null;
            if (list != null && list.iterator().hasNext()) {
                listReturn = new ArrayList<>();
                for (TemplateVo cv : list) {
                    HashMap<String, Object> mapTemp = new HashMap<>();
                    mapTemp.put("key", cv.getId());
                    List<Object> listTmp = new ArrayList<>();
                    listTmp.add(cv.getName());
                    listTmp.add(cv.getContentPosition());
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
    public TemplateVo info(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return templateTableService.info(key, principal);
    }

    @RequestMapping(value = "/copy", method = {RequestMethod.GET})
    public void copy(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        templateTableService.copy(key);
    }

    @RequestMapping(value = "/preview", method = {RequestMethod.GET})
    public String preview(@RequestParam Long key, @RequestParam(required = false) String originPath, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (originPath == null) {
            originPath = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ((httpServletRequest.getServerPort() == 80) ? "" : (":" + httpServletRequest.getServerPort()));
        }
        return templateTableService.preview(key, originPath);
    }

    @Override
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam Long key, @RequestBody TemplateVo templateVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        templateVo.setId(key);
        return templateTableService.update(templateVo, null, principal);
    }

    @Override
    public VueResults.Result update(@RequestParam(name = "key") Long key, @RequestParam(name = "files", required = false) MultipartFile[] files, TemplateVo templateVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestBody TemplateVo templateVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        // 存储的时候只有save，更新的时候则是都有的
        Map<String, Long> keys = CommonMethods.generateKey(httpServletRequest);
        if (keys.size() > 0) {
            templateVo.setThemeId(keys.get("themeId"));
            templateVo.setWebsiteId(keys.get("websiteId"));
            return templateTableService.save(templateVo, null, principal);
        }
        return VueResults.generateError("创建失败", "模板必须和主题或者网站关联");
    }

    @Override
    public VueResults.Result save(@RequestParam(name = "files", required = false) MultipartFile[] files, TemplateVo templateVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        templateTableService.delete(key, principal);
    }

    @Override
    @RequestMapping(value = "/form/rulechange", method = {RequestMethod.GET, RequestMethod.POST})
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        //没有相关的变化
        return null;
    }
}
