package cn.hopever.platform.utils.web;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by Donghui Huo on 2017/4/24.
 */
@Aspect
@Order
@Component
public class AroundControllerAdvice {

    public static Logger logger = LoggerFactory.getLogger(AroundControllerAdvice.class);

    @Before("execution(public * cn.hopever.platform.*.controller.*.*(..)) && @annotation(moduleAuthorize)")
    public void packageTableAndForm(ProceedingJoinPoint jp, ModuleAuthorize moduleAuthorize) {
        try {
            String module = moduleAuthorize.value();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Object detail = authentication.getDetails();
            List<String> listModules = (List<String>) ((Map) authentication.getDetails()).get("modules");
            if (listModules.contains(module)) {
                return;
            }
            throw new AuthorizationServiceException("Bad authorization");
        } catch (Exception e) {
            throw new AuthorizationServiceException("Bad authorization");
        }

    }

}
