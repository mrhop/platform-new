package cn.hopever.platform.oauth2client.web.interceptor;

import cn.hopever.platform.utils.web.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Donghui Huo on 2016/10/25.
 */
@Component("commonInterceptor")
public class CommonInterceptor extends LocaleChangeInterceptor {
    Logger logger = LoggerFactory.getLogger(CommonInterceptor.class);

    public CommonInterceptor() {
        super.setParamName("lang");
        super.setIgnoreInvalidLocale(false);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
        super.preHandle(request, response, handler);
        // 需要将principle来进行cookie设值
        Cookie c = CookieUtil.getCookieByName("accesstoken", request.getCookies());
        HttpSession session = request.getSession(true);
        Object securityContext = session.getAttribute("SPRING_SECURITY_CONTEXT");
        if (c == null && securityContext != null) {
            Authentication authentication = ((SecurityContext) securityContext).getAuthentication();
            if (authentication != null && authentication.getDetails() instanceof Map && ((Map) authentication.getDetails()).get("accesstoken") != null) {
                c = new Cookie("accesstoken", ((Map) authentication.getDetails()).get("accesstoken").toString());
                c.setMaxAge(-1);
                response.addCookie(c);
            } else if (authentication != null && authentication.getDetails() instanceof OAuth2AccessToken) {
                OAuth2AccessToken t = (OAuth2AccessToken) authentication.getDetails();
                c = new Cookie("accesstoken", t.getValue());
                c.setMaxAge(-1);
                response.addCookie(c);
            }
        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView
            modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception
            ex) throws Exception {

    }

    protected Locale parseLocaleValue(String locale) {
        return StringUtils.parseLocaleString(locale);
    }
}
