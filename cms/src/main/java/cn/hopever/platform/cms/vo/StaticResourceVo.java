package cn.hopever.platform.cms.vo;

import lombok.Data;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
@Data
public class StaticResourceVo {

    private long id;

    private String name;

    private String filename;

    private String type;

    private String fileType;

    private long size;

    private String url;

    private Long themeId;
    private String themeName;

    private String websiteName;
    private Long websiteId;

    private Long articleId;
    private String articleTitle;

    private Integer order;
}
