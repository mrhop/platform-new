package cn.hopever.platform.user.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Donghui Huo on 2016/8/31.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleVo {

    @NotNull
    private long id;

    @NotNull
    private String authority;

    private String name;

    private short level;

    private List<UserVo> users;

}
