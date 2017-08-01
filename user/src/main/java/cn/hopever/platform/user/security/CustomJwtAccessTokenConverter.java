package cn.hopever.platform.user.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Donghui Huo on 2017/8/1.
 */
public class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {

    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
                                     OAuth2Authentication authentication) {
        OAuth2AccessToken result = super.enhance(accessToken, authentication);
        if ("password".equals(authentication.getOAuth2Request().getGrantType())) {
            Map<String, Object> info = new LinkedHashMap<String, Object>(
                    result.getAdditionalInformation());
            if (!info.containsKey("authorities") && authentication.getAuthorities() != null && authentication.getAuthorities().size() > 0) {
                List<GrantedAuthority> list = new ArrayList<>();
                for (GrantedAuthority authority : authentication.getAuthorities()) {
                    SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority.getAuthority());
                    list.add(simpleGrantedAuthority);
                }
                info.put("authorities", list);
                ((DefaultOAuth2AccessToken) result).setAdditionalInformation(info);
            }
        }
        return result;
    }
}
