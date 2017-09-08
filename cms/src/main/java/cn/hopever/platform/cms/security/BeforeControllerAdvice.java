package cn.hopever.platform.cms.security;

import cn.hopever.platform.cms.service.ThemeTableService;
import cn.hopever.platform.cms.service.WebsiteTableService;
import cn.hopever.platform.cms.vo.ThemeVo;
import cn.hopever.platform.cms.vo.WebsiteVo;
import cn.hopever.platform.utils.web.CookieUtil;
import cn.hopever.platform.utils.web.ModuleAuthorize;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Donghui Huo on 2017/4/24.
 */
@Aspect
@Order
@Component("cmsBeforeControllerAdvice")
public class BeforeControllerAdvice {

    public static Logger logger = LoggerFactory.getLogger(BeforeControllerAdvice.class);

    @Autowired
    private ThemeTableService themeTableService;
    @Autowired
    private WebsiteTableService websiteTableService;

    @Before("execution(public * cn.hopever.platform.cms.*.controller.*.*(..)) && @annotation(moduleAuthorize)")
    public void packageTableAndForm(JoinPoint jp, ModuleAuthorize moduleAuthorize) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Cookie c = CookieUtil.getCookieByName("current-website", request.getCookies());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (c != null) {
            WebsiteVo websiteVo = websiteTableService.info(Long.valueOf(c.getValue()), authentication);
            if (websiteVo != null && websiteVo.getRelatedUsers().contains(authentication.getName())) {
                return;
            } else {
                throw new AuthorizationServiceException("Bad authorization");
            }
        } else {
            c = CookieUtil.getCookieByName("current-theme", request.getCookies());
            if (c != null) {
                ThemeVo themeVo = themeTableService.info(Long.valueOf(c.getValue()), authentication);
                if (themeVo != null && themeVo.getRelatedUsers().contains(authentication.getName())) {
                    return;
                } else {
                    throw new AuthorizationServiceException("Bad authorization");
                }
            }
        }
    }

}
