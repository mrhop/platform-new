package cn.hopever.platform.cms.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
@Data
public class ThemeVo {

    private long id;

    private String name;

    private List<String> screenshots;

    private String description;

}
