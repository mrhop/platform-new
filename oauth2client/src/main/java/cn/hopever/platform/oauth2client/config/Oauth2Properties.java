package cn.hopever.platform.oauth2client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by Donghui Huo on 2016/9/13.
 */
@Configuration
@ConfigurationProperties("config.oauth2")
@Data
public class Oauth2Properties {
    // client使用
    private String accessTokenUri;
    private String userAuthorizationUri;
    private String leftMenu;
    private String domainName;
    private String path;
    private String clientID;
    private String clientSecret;
    private List<String> clientScopes;
    private boolean clientUseCurrentUri = true;
}
