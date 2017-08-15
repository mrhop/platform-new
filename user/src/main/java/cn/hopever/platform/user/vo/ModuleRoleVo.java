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
//获取角色的一些信息并进行处理，
public class ModuleRoleVo {

    @NotNull
    private long id;

    @NotNull
    private String authority;

    private String name;

    private List<ModuleVo> modules;

    private ClientVo client;

    private List<UserVo> users;

}
