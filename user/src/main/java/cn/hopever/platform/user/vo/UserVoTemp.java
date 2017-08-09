package cn.hopever.platform.user.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Donghui Huo on 2016/8/31.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserVoTemp implements Serializable {
    private long id;

    private String username;

    private String name;

    private String password;

    private boolean accountNonLocked = false;

    private String email;

    private String phone;

    private boolean enabled;

}
