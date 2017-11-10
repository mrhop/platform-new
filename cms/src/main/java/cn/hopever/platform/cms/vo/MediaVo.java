package cn.hopever.platform.cms.vo;

import lombok.Data;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
@Data
public class MediaVo {

    private long id;

    private String name;

    private String filename;

    private String type;

    private String fileType;

    private long size;

    private String url;

    private Long mediaTagId;
    private String mediaTagName;

    private String websiteName;
    private Long websiteId;

    private String themeName;
    private Long themeId;

    private Long publishDate;

    private Long createdDate;

    private Long createdUserId;
    private String createdUserName;

    private boolean published;

    private Integer clickTimes;

}
