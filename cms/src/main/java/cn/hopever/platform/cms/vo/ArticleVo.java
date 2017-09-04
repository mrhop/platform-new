package cn.hopever.platform.cms.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
@Data
public class ArticleVo {

    private long id;

    private String title;

    private String subtitle;

    private String aliasUrl;

    private String content;

    private String script;

    private boolean published;

    private Integer clickTimes;

    private List<Long> articleTags;
    private String articleTagsStr;

    private Long publishDate;

    private Long createdDate;
    private String createUser;

    private String websiteName;
    private Long websiteId;

    private String templateName;
    private Long templateId;

}
