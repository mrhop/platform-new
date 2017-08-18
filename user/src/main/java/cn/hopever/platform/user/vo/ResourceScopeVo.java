package cn.hopever.platform.user.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Donghui Huo on 2017/8/18.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ResourceScopeVo {
    private long id;

    private String name;

    private String scopeId;
}
