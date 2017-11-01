package cn.hopever.platform.cms.security;

import cn.hopever.platform.cms.service.RelatedUserTableService;
import cn.hopever.platform.cms.service.ThemeTableService;
import cn.hopever.platform.cms.service.WebsiteTableService;
import cn.hopever.platform.cms.vo.RelatedUserVo;
import cn.hopever.platform.cms.vo.ThemeVo;
import cn.hopever.platform.cms.vo.WebsiteVo;
import cn.hopever.platform.utils.test.PrincipalSample;
import cn.hopever.platform.utils.web.CookieUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/4/24.
 */
@Aspect
@Order
@Component("cmsControllerAdvice")
public class ControllerAdvice {

    public static Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

    @Autowired
    private ThemeTableService themeTableService;
    @Autowired
    private WebsiteTableService websiteTableService;
    @Autowired
    private RelatedUserTableService relatedUserTableService;


    @Around("execution(public * cn.hopever.platform.cms.*.controller.*.*(..))")
    public Object packageTableAndForm(ProceedingJoinPoint pjp) throws Throwable {
        // 目前做个临时性的测试
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_admin"));
        Authentication authentication = new PrincipalSample("testAdmin", list);
        authentication.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Class[] typeClasses = ((MethodSignature) pjp.getSignature()).getMethod().getParameterTypes();
        int position = -1;

        if (typeClasses != null && typeClasses.length > 0) {
            for (int i = 0; i < typeClasses.length; i++) {
                if (typeClasses[i].getName().equals("java.security.Principal")) {
                    position = i;
                    break;
                }
            }
        }
        Object[] args = pjp.getArgs();
        if (position != -1) {
            args[position] = authentication;
        }
        return pjp.proceed(args);
    }

    //    @Before("execution(public * cn.hopever.platform.cms.*.controller.*.*(..))")
    public void testUserRelateWebsiteOrTheme(JoinPoint jp) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Cookie c = CookieUtil.getCookieByName("current-website", request.getCookies());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (c != null) {
            WebsiteVo websiteVo = websiteTableService.info(Long.valueOf(c.getValue()), authentication);
            if (websiteVo != null && websiteVo.getRelatedUserAccounts().contains(authentication.getName())) {
                return;
            } else {
                throw new AuthorizationServiceException("Bad authorization");
            }
        } else {
            c = CookieUtil.getCookieByName("current-theme", request.getCookies());
            if (c != null) {
                ThemeVo themeVo = themeTableService.info(Long.valueOf(c.getValue()), authentication);
                if (themeVo != null && themeVo.getRelatedUserAccounts().contains(authentication.getName())) {
                    return;
                } else {
                    throw new AuthorizationServiceException("Bad authorization");
                }
            }
        }
    }


    @AfterReturning(pointcut = "execution(public * cn.hopever.platform.oauth2client.security.RemoteOauth2AuthenticationProvider.authenticate(..))", returning = "authentication")
    public void afterAuthenticated(Authentication authentication) {
        // 注意当用户可用时，管理员也放置其中，但是超级管理员不放置其中
        if (authentication.getAuthorities() != null) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if ("ROLE_super_admin".equals(authority.getAuthority())) {
                    return;
                }
            }
        }
        RelatedUserVo relatedUserVo = relatedUserTableService.getOneByAccount(authentication.getName());
        if (relatedUserVo == null) {
            relatedUserVo = new RelatedUserVo();
            relatedUserVo.setAccount(authentication.getName());
            relatedUserTableService.save(relatedUserVo, null, null);
        }
    }

}
