package cn.hopever.platform.cms.web.controller;

import cn.hopever.platform.cms.service.ThemeTableService;
import cn.hopever.platform.cms.service.WebsiteTableService;
import cn.hopever.platform.oauth2client.config.Oauth2Properties;
import cn.hopever.platform.oauth2client.web.common.CommonMethods;
import cn.hopever.platform.utils.json.JacksonUtil;
import cn.hopever.platform.utils.web.CommonResult;
import cn.hopever.platform.utils.web.SelectOption;
import cn.hopever.platform.utils.web.TreeOption;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Donghui Huo on 2017/9/6.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/", produces = "application/json")
public class TopController {

    @Autowired
    private ThemeTableService themeTableService;
    @Autowired
    private WebsiteTableService websiteTableService;
    @Autowired
    private Oauth2Properties oauth2Properties;
    @Autowired
    private CommonMethods commonMethods;

    @RequestMapping(value = "/topmenu", method = {RequestMethod.GET})
    public List<TreeOption> topmenu(Principal principal, Authentication authentication) {
        boolean admin = false;
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("ROLE_admin") || grantedAuthority.getAuthority().equals("ROLE_super_admin")) {
                admin = true;
                break;
            }
        }
        List<SelectOption> themes = null;
        List<SelectOption> websites = null;
        if (admin) {
            // 返回所有的theme和website
            themes = themeTableService.getOptions();
            websites = websiteTableService.getOptions();
        } else {
            //  返回只可处理的theme和website
            themes = themeTableService.getOptions(principal);
            websites = websiteTableService.getOptions(principal);
        }
        List<TreeOption> listReturn = new ArrayList<>();
        if (themes != null && themes.size() > 0) {
            TreeOption treeOption = new TreeOption(-1l, "主题");
            List<TreeOption> list = new ArrayList<>();
            for (SelectOption selectOption : themes) {
                list.add(new TreeOption(Long.valueOf(selectOption.getValue().toString()), selectOption.getLabel()));
            }
            treeOption.setChildren(list);
            listReturn.add(treeOption);
        }
        if (websites != null && websites.size() > 0) {
            TreeOption treeOption = new TreeOption(-1l, "网站");
            List<TreeOption> list = new ArrayList<>();
            for (SelectOption selectOption : websites) {
                list.add(new TreeOption(Long.valueOf(selectOption.getValue().toString()), selectOption.getLabel()));
            }
            treeOption.setChildren(list);
            listReturn.add(treeOption);
        }
        return listReturn;
    }

    @PreAuthorize("hasRole('ROLE_super_admin') or hasRole('ROLE_admin')")
    @RequestMapping(value = "/topnavigate", method = {RequestMethod.GET})
    public String[] topnavigate(Principal principal, Authentication authentication) {
        return new String[]{"主题管理", "网站管理"};
    }

    // 根据类型，来获取左侧的树，根据返回的modules 来进行处理，应该在此调用后端的user，然后将theme或者website部分去除，然后返回
    // cms部分应该包含2部分 theme， website ，剩下的对website和theme本身的处理，应该在admin管理权限里面
    @RequestMapping(value = "/leftmenu", method = {RequestMethod.GET})
    public List<TreeOption> leftmenu(@RequestParam("type") String type, HttpServletRequest httpServletRequest, Principal principal, Authentication authentication) throws Exception {
        httpServletRequest.setAttribute("resourceUrl", oauth2Properties.getLeftMenu());
        CommonResult commonResult = commonMethods.getResource(httpServletRequest);
        List<Map> list = (List<Map>) commonResult.getResponseData().get("data");
        for (Map map : list) {
            TreeOption treeOption = JacksonUtil.mapper.convertValue(map, TreeOption.class);
            if (treeOption.getTitle().equals("主题") && "theme".equals(type)) {
                return treeOption.getChildren();
            }
            if (treeOption.getTitle().equals("网站") && "website".equals(type)) {
                return treeOption.getChildren();
            }
        }
        return null;
    }
}
