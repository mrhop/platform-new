package cn.hopever.platform.cms.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
@Data
public class TemplateVo {

    private long id;

    private String name;

    private Long themeId;
    private String themeName;

    private String websiteName;
    private Long websiteId;

    private String contentPosition;

    private List<List<String>> blocks;

    private String description;

    private Long createdDate;

    private Long createdUserId;
    private String createdUserName;

}
