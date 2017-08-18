package cn.hopever.platform.user.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Donghui Huo on 2016/8/31.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ModuleRoleVo {

    private long id;

    private String authority;

    private String name;

    private Long clientId;

    private String clientName;
}
