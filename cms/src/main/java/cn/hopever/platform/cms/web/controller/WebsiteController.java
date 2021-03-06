package cn.hopever.platform.cms.web.controller;

import cn.hopever.platform.cms.service.RelatedUserTableService;
import cn.hopever.platform.cms.service.ThemeTableService;
import cn.hopever.platform.cms.service.WebsiteTableService;
import cn.hopever.platform.cms.vo.WebsiteVo;
import cn.hopever.platform.oauth2client.config.Oauth2Properties;
import cn.hopever.platform.utils.security.CommonMethods;
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
@RequestMapping(value = "/website", produces = "application/json")
public class WebsiteController implements GenericController<WebsiteVo> {
    Logger logger = LoggerFactory.getLogger(WebsiteController.class);

    @Autowired
    private Oauth2Properties oauth2Properties;

    @Autowired
    private WebsiteTableService websiteTableService;

    @Autowired
    private RelatedUserTableService relatedUserTableService;

    @Autowired
    private ThemeTableService themeTableService;

    //当用户角色为管理员时，可以处理该列表，以及其他的处理
    @Override
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    //@PreAuthorize("hasRole('ROLE_admin')")
    public Map getList(@RequestBody TableParameters body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Page<WebsiteVo> list = websiteTableService.getList(body, principal);
        Map<String, Object> map = new HashMap<>();
        List<HashMap<String, Object>> listReturn = null;
        if (list != null && list.iterator().hasNext()) {
            listReturn = new ArrayList<>();
            for (WebsiteVo cv : list) {
                HashMap<String, Object> mapTemp = new HashMap<>();
                mapTemp.put("key", cv.getId());
                List<Object> listTmp = new ArrayList<>();
                listTmp.add(cv.getName());
                listTmp.add(cv.getWebsiteId());
                listTmp.add(cv.getThemeId());
                listTmp.add(cv.getUrl());
                listTmp.add(cv.getEmail());
                listTmp.add(cv.getRelatedUserAccounts());
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
    public WebsiteVo info(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return websiteTableService.info(key, principal);
    }

    @Override
    public VueResults.Result update(@RequestParam Long key, @RequestBody WebsiteVo websiteVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam(name = "key") Long key, @RequestParam(name = "screenshotFiles", required = false) MultipartFile[] files, WebsiteVo websiteVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        websiteVo.setId(key);
        return websiteTableService.update(websiteVo, files, principal);
    }

    @RequestMapping(value = "/updateinfo", method = {RequestMethod.POST})
    public VueResults.Result updateinfo(@RequestParam(name = "key") Long key, @RequestParam(name = "screenshotFiles", required = false) MultipartFile[] files, WebsiteVo websiteVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        websiteVo.setId(key);
        websiteTableService.update(websiteVo, files, principal);
        return VueResults.generateSuccess("更新成功", "更新成功");
    }

    @Override
    public VueResults.Result save(@RequestBody WebsiteVo websiteVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestParam(name = "screenshotFiles", required = false) MultipartFile[] files, WebsiteVo websiteVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return websiteTableService.save(websiteVo, files, principal);
    }

    @Override
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        websiteTableService.delete(key, principal);
    }

    @Override
    @RequestMapping(value = "/form/rulechange", method = {RequestMethod.GET, RequestMethod.POST})
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Map mapReturn = new HashMap<>();
        try {
            if (CommonMethods.isAdmin(principal)) {
                mapReturn.put("relatedUsers", relatedUserTableService.getRelatedUserOptions(principal));
            }
            if (key == null) {
                mapReturn.put("themes", themeTableService.getOptions());
            }
        } catch (Exception e) {
            logger.error("website rulechange:" + e);
        }
        return mapReturn;
    }
}
