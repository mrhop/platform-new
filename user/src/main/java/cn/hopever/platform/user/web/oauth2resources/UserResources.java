package cn.hopever.platform.user.web.oauth2resources;

import cn.hopever.platform.user.service.ModuleTableService;
import cn.hopever.platform.user.service.UserTableService;
import cn.hopever.platform.utils.test.PrincipalSample;
import cn.hopever.platform.utils.web.TreeOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/7/26.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/resources/user", produces = "application/json")
public class UserResources {

    @Autowired
    private ModuleTableService moduleTableService;

    @Autowired
    private UserTableService userTableService;

    @RequestMapping(value = "/leftmenu", method = {RequestMethod.GET})
    public List<TreeOption> getLeftMenu(Principal principal, OAuth2Authentication auth) {
        principal = new PrincipalSample("admin");
//        return moduleTableService.getLeftMenu(principal, auth.getOAuth2Request().getClientId());
        return moduleTableService.getLeftMenu(principal, "cms_client");
    }

    @RequestMapping(value = "/relatedusers", method = {RequestMethod.GET})
    public List<String> getRelatedUser(Principal principal, OAuth2Authentication auth) {
//        return userTableService.getListByClientId(auth.getOAuth2Request().getClientId());
        return userTableService.getListByClientId("cms_client");
    }

}
