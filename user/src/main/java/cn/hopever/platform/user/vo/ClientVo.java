package cn.hopever.platform.user.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.omg.CORBA.Object;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Donghui Huo on 2016/8/31.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ClientVo {

    @NotNull
    private long id;

    @NotNull
    private String clientId;

    private String clientName;

    private String clientSecret;

    private boolean secretRequired = true;

    private Set<String> resourceIds;

    private boolean scoped = true;

    private boolean internalClient;

    private Set<String> scope;

    private Set<String> authorizedGrantTypes;

    private String registeredRedirectUri;

    private List<ClientRoleVo> authorities;

    private Integer accessTokenValiditySeconds;

    private Integer refreshTokenValiditySeconds;

    private Map<String, Object> additionalInformation;

    private List<ModuleVo> modules;

    private List<ModuleRoleVo> moduleRoles;

    private List<UserVo> users;

}
