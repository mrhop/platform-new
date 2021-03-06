package cn.hopever.platform.cms.vo;

import lombok.Data;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
@Data
public class ArticleTagVo {

    private long id;
    private String name;
    private String tagId;
    private String websiteName;
    private Long websiteId;

    private Long createdDate;

    private Long createdUserId;
    private String createdUserName;
}
