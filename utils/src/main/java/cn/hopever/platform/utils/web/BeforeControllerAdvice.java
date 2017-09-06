package cn.hopever.platform.utils.web;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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
public class BeforeControllerAdvice {

    public static Logger logger = LoggerFactory.getLogger(BeforeControllerAdvice.class);

    @Around("execution(public * cn.hopever.platform.*.controller.*.*(..)) && @annotation(moduleAuthorize)")
    public void packageTableAndForm(JoinPoint jp, ModuleAuthorize moduleAuthorize) {
        try {
            String module = moduleAuthorize.value();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
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
