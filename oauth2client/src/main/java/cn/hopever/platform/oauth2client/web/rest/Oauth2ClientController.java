package cn.hopever.platform.oauth2client.web.rest;

import cn.hopever.platform.oauth2client.config.Oauth2Properties;
import cn.hopever.platform.oauth2client.web.common.CommonMethods;
import cn.hopever.platform.utils.web.CommonResult;
import cn.hopever.platform.utils.web.CommonResultStatus;
import cn.hopever.platform.utils.web.CookieUtil;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Donghui Huo on 2016/9/6.
 */
//以cookie为准，并且cookie的值加密
//需要1.获取token 根据提供数据 获取token，并设置cookie（加密，用于多内部平台），session，用于client使用，并需要注意session的延期
//需要2.当判断失效时间以及baseoncode获取的token进行refresh
//需要3.需要client的get和post使用获取resource
//需要4.首次进入时，进入/，并根据cookie中提供的数据进行导向，如session有，则根据session中的来，如session无，则看cookie是否过期，如无过期则重新生成session，并按照session来
//统一的认证和单点的登陆考虑
//cookie的一致性和session的各自性
//不再使用session
@RestController
@RequestMapping(produces = "application/json")
@CrossOrigin
public class Oauth2ClientController {

    @Autowired
    private Oauth2Properties oauth2Properties;

    @Autowired
    @Qualifier("authorizationCodeRestTemplate")
    private OAuth2RestOperations authorizationCodeRestTemplate;

    @Autowired
    @Qualifier("clientRestTemplate")
    private OAuth2RestOperations clientRestTemplate;


    @Autowired
    private CommonMethods commonMethods;

    @RequestMapping(value = "/gettokenbycode", method = RequestMethod.GET)
    public void getTokenByCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie c = CookieUtil.getCookieByName("accesstoken", request.getCookies());
        if(c==null){
            OAuth2AccessToken oa = authorizationCodeRestTemplate.getAccessToken();
            Cookie cookie = new Cookie("accesstoken", oa.getValue());
            //cookie.setPath(request.getContextPath());
            cookie.setMaxAge(oa.getExpiresIn());
            if (oauth2Properties.getDomainName() != null) {
                cookie.setDomain(oauth2Properties.getDomainName());
            }
            response.addCookie(cookie);
        }
        response.sendRedirect(request.getContextPath() + "/index.html");
    }

    @RequestMapping(value = "/gettokenbyclient", method = RequestMethod.GET)
    public void getTokenByClient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie c = CookieUtil.getCookieByName("accesstoken", request.getCookies());
        if(c==null){
            OAuth2AccessToken oa = clientRestTemplate.getAccessToken();
            Cookie cookie = new Cookie("accesstoken", oa.getValue());
            //cookie.setPath(request.getContextPath());
            cookie.setMaxAge(oa.getExpiresIn());
            if (oauth2Properties.getDomainName() != null) {
                cookie.setDomain(oauth2Properties.getDomainName());
            }
            response.addCookie(cookie);
        }
        response.sendRedirect(request.getContextPath() + "/index.html");
    }

    @RequestMapping(value = "/postresource", method = RequestMethod.POST)
    public CommonResult postResource(@RequestBody JsonNode body, HttpServletRequest request) throws Exception {
        return commonMethods.postResource(body, request);
    }

    @RequestMapping(value = "/getresource", method = RequestMethod.GET)
    public CommonResult getResource(HttpServletRequest request) throws Exception {
        return commonMethods.getResource(request);
    }

    @RequestMapping(value = "/leftmenu", method = RequestMethod.GET)
    public CommonResult getLeftmenu(HttpServletRequest request) throws Exception {
        request.setAttribute("resourceUrl", oauth2Properties.getModuleList() + oauth2Properties.getClientID());
        return commonMethods.getResource(request);
    }

    @RequestMapping(value = "/testresource", method = RequestMethod.GET)
    public CommonResult getTestresource(HttpServletRequest request) throws Exception {
        CommonResult cr = new CommonResult();
        cr.setStatus(CommonResultStatus.SUCCESS.toString());
        cr.setMessage("successfully accessed oauth2 resource, you got it");
        return cr;
    }
}
