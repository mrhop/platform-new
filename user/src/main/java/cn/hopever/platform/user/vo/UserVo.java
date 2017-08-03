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

    private String enabledStr;

    private Long limitedDate;

    private String photo;

    private Long authorities;

    private String authoritiesStr;

    private List<Long> clients;

    private String clientsStr;

    private List<Long> modulesAuthorities;

    private String modulesAuthoritiesStr;
}
