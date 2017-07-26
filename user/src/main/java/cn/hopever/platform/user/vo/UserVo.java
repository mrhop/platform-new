package cn.hopever.platform.user.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.omg.CORBA.Object;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Donghui Huo on 2016/8/31.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserVo {

    @NotNull
    private long id;

    @NotNull
    private String username;

    private String name;

    private String password;

    private boolean accountNonLocked = false;

    private String email;

    private String phone;

    private boolean enabled;

    private Date limitedDate;

    private String photo;

    private List<RoleVo> authorities;

    private List<ClientVo> clients;

    private Map<String, Object> additionalMessage;

    private List<ModuleRoleVo> modulesAuthorities;
}
