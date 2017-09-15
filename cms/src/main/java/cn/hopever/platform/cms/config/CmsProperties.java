package cn.hopever.platform.cms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Donghui Huo on 2016/9/13.
 */
@Configuration
@ConfigurationProperties("config.cms")
@Data
public class CmsProperties {
    private String staticResourcePath;
    private String staticResourceRelativePath;
}
