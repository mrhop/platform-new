package cn.hopever.platform.utils.properties;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Donghui Huo on 2016/9/13.
 */
@Configuration
@ConfigurationProperties("config.common")
@Data
public class CommonProperties {
    public static Logger logger = LoggerFactory.getLogger(CommonProperties.class);
    private int pageSize;
    private String relatedusers;
    private String imageUpload;
    private String fileUpload;
    private String docUpload;
    private String imageDel;
    private String fileDel;
    private String docDel;
    private String imagePathPrev;
    private String filePathPrev;
    private String docPathPrev;
    private String defaultUserPhoto;
}
