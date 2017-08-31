package cn.hopever.platform.cms.vo;

import lombok.Data;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
@Data
public class TemplateVo {

    private long id;

    private String name;

    private Long themeId;
    private String themeName;

    private String description;

}
