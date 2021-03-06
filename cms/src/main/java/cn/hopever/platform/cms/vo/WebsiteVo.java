package cn.hopever.platform.cms.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
@Data
public class WebsiteVo {

    private long id;

    private String websiteId;

    private String name;

    private String title;

    private String subtitle;

    private String url;

    private String email;

    private String phone;

    private List<String> relatedUserAccounts;
    private List<Long> relatedUserIds;


    private Long createdDate;

    private Long createdUserId;
    private String createdUserName;

    private boolean enabled;

    private  List<String> screenshots;

    private String description;

    private Long themeId;
    private String themeName;

}
