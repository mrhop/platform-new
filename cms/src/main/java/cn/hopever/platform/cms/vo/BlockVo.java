package cn.hopever.platform.cms.vo;

import lombok.Data;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
@Data
public class BlockVo {

    private long id;

    private String name;

    private String position;

    private String content;

    private String script;

    private Long templateId;
    private String templateName;

    private String websiteName;
    private Long websiteId;

    private String articleTitle;
    private Long articleId;

}
