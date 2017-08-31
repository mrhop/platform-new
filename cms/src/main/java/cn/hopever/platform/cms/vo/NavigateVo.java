package cn.hopever.platform.cms.vo;

import lombok.Data;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
@Data
public class NavigateVo {

    private long id;

    private String name;

    private Integer navigateOrder = 0;

    // 用于和order一起来决定顺序
    private Long beforeId;

    private Long articleId;
    private String articleTitle;

    private String iconClass;

    private Long parentId;
    private String parentName;

    private boolean activated = true;

    private String websiteName;
    private Long websiteId;
}
