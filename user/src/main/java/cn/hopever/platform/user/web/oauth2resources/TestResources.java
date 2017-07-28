package cn.hopever.platform.user.web.oauth2resources;

import cn.hopever.platform.utils.web.CommonResult;
import cn.hopever.platform.utils.web.CommonResultStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Created by Donghui Huo on 2017/7/26.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/resources/test", produces = "application/json")
public class TestResources {
    @RequestMapping(value = "/testresource", method = {RequestMethod.GET})
    public CommonResult testResource(Principal principal) {
        CommonResult cr = new CommonResult();
        cr.setMessage("you get this test resource");
        cr.setStatus(CommonResultStatus.SUCCESS.toString());
        return cr;
    }

    @RequestMapping(value = "/testclientresource", method = {RequestMethod.GET})
    public CommonResult testClientResource(Principal principal) {
        CommonResult cr = new CommonResult();
        cr.setMessage("you get this test client resource");
        cr.setStatus(CommonResultStatus.SUCCESS.toString());
        return cr;
    }
}
