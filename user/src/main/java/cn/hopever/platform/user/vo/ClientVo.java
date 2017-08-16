package cn.hopever.platform.user.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;
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

    private boolean internalClient;

    private String scopesStr;

    private List<Long> scopeIds;

    private List<Long> autoApprovaledScopeIds;

    private Set<String> authorizedGrantTypes;

    private String authorizedGrantTypesStr;
}
