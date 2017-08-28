package cn.hopever.platform.user.web.oauth2resources;

import cn.hopever.platform.user.service.ModuleTableService;
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

    @RequestMapping(value = "/leftmenu", method = {RequestMethod.GET})
    public List getLeftMenu(Principal principal, OAuth2Authentication auth) {
        return moduleTableService.getLeftMenu(principal, auth.getOAuth2Request().getClientId());
    }

}
