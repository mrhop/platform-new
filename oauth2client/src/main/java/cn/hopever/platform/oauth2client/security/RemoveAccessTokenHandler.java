package cn.hopever.platform.oauth2client.security;

import cn.hopever.platform.utils.web.CookieUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Donghui Huo on 2017/7/28.
 */
@Component("removeAccessTokenHandler")
public class RemoveAccessTokenHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Cookie c = CookieUtil.getCookieByName("accesstoken", request.getCookies());
        if (c != null) {
            c.setMaxAge(0);
            response.addCookie(c);
        }
    }
}
