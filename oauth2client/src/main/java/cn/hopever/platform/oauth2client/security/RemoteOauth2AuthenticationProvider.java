package cn.hopever.platform.oauth2client.security;

import cn.hopever.platform.oauth2client.web.common.CommonMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            // 此处进行accesstoken的获取，同时关联相关的授权信息
            List<SimpleGrantedAuthority> list = null;
            if (oa.getAdditionalInformation().get("authorities") != null) {
                list = new ArrayList<>();
                List<Map<String, String>> listAuthorities = (List<Map<String, String>>) oa.getAdditionalInformation().get("authorities");
                for (Map<String, String> authority : listAuthorities) {
                    list.add(new SimpleGrantedAuthority(authority.get("authority")));
                }
            }
            UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                    username, null,
                    list);
            Map details= new HashMap<>();
            if(oa.getAdditionalInformation().get("modules")!=null){
                details.put("modules",details);
            }
            result.setDetails(details);
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
