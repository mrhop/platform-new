package cn.hopever.platform.oauth2client.security;

import cn.hopever.platform.oauth2client.web.common.CommonMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * Created by Donghui Huo on 2017/7/25.
 */
public class RemoteOauth2AuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CommonMethods commonMethods;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //此处 username 和password
        try {
            String username = authentication.getPrincipal().toString();
            Object credentials = authentication.getCredentials();
            String password = credentials == null ? null : credentials.toString();
            OAuth2AccessToken oa = commonMethods.getPasswordRestTemplate(username, password).getAccessToken();
            // 此处进行accesstoken的获取
            UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                    username, null,
                    null);
            //设置一个accesstoken
            result.setDetails(oa);
            return result;
        } catch (Exception e) {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class
                .isAssignableFrom(authentication));
    }
}
