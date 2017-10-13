package cn.hopever.platform.crm.security;

import cn.hopever.platform.crm.service.RelatedUserTableService;
import cn.hopever.platform.crm.vo.RelatedUserVo;
import cn.hopever.platform.utils.test.PrincipalSample;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/4/24.
 */
@Aspect
@Order
@Component("crmControllerAdvice")
public class ControllerAdvice {

    public static Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

    @Autowired
    private RelatedUserTableService relatedUserTableService;

    @Before("execution(public * cn.hopever.platform.crm.*.controller.*.*(..))")
    public void packageTableAndForm(JoinPoint jp) {
        // 目前做个临时性的测试
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_admin"));
        Authentication authentication = new PrincipalSample("testAdmin", list);
        authentication.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @AfterReturning(pointcut = "cn.hopever.platform.oauth2client.security.RemoteOauth2AuthenticationProvider.authenticate(..)", returning = "authentication")
    public void afterAuthenticated(Authentication authentication) {
        // 目前做个临时性的测试
        if (authentication.getAuthorities() != null) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if ("ROLE_admin".equals(authority.getAuthority()) || "ROLE_super_admin".equals(authority.getAuthority())) {
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
