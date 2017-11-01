package cn.hopever.platform.crm.web.controller;

import cn.hopever.platform.oauth2client.config.Oauth2Properties;
import cn.hopever.platform.oauth2client.web.common.CommonMethods;
import cn.hopever.platform.utils.web.CommonResult;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Donghui Huo on 2017/9/6.
 */
@RestController
//@CrossOrigin
@RequestMapping(value = "/", produces = "application/json")
public class TopController {
    private Logger logger = Logger.getLogger(TopController.class);

    @Autowired
    private Oauth2Properties oauth2Properties;
    @Autowired
    private CommonMethods commonMethods;


    @RequestMapping(value = "/leftmenu", method = {RequestMethod.GET})
    public List<Map> leftmenu(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Principal principal, Authentication authentication) throws Exception {
        if (httpServletRequest.getSession(true).getAttribute("leftMenu") != null) {
            return (List) httpServletRequest.getSession(true).getAttribute("leftMenu");
        } else {
            return this.getLeftMenu(httpServletRequest);
        }
    }

    @RequestMapping(value = "/userstatus", method = {RequestMethod.GET})
    public Map userStatus(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Principal principal, Authentication authentication) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<Map> list = leftmenu(httpServletRequest, httpServletResponse, principal, authentication);
        if (list != null) {
            map.put("firstPage", getFirstPage(list));
        }
        if (map.get("firstPage") == null) {
            map.put("firstPage", "/404");
        }
        map.put("isAdmin", cn.hopever.platform.utils.security.CommonMethods.isAdmin(principal));
        return map;
    }

    private List<Map> getLeftMenu(HttpServletRequest httpServletRequest) throws Exception {
        synchronized (this) {
            if (httpServletRequest.getSession(true).getAttribute("leftMenu") != null) {
                return (List) httpServletRequest.getSession(true).getAttribute("leftMenu");
            }
            httpServletRequest.setAttribute("resourceUrl", oauth2Properties.getLeftMenu());
            CommonResult commonResult = commonMethods.getResource(httpServletRequest);
            List<Map> list = (List<Map>) commonResult.getResponseData().get("data");
            httpServletRequest.getSession(true).setAttribute("leftMenu", list);
            return list;
        }
    }

    private String getFirstPage(List<Map> list) {
        String firstPage = null;
        for (Map map : list) {
            if (map.get("url") != null) {
                firstPage = map.get("url").toString();
            } else if (map.get("children") != null) {
                firstPage = getFirstPage((List<Map>) map.get("children"));
            }
            if (firstPage != null) {
                return firstPage;
            }
        }
        return firstPage;
    }
}
