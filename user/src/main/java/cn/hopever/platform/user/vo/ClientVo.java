package cn.hopever.platform.user.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

/**
 * Created by Donghui Huo on 2016/8/31.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ClientVo {

    private long id;

    private String clientId;

    private String clientName;

    private String clientSecret;

    private String scopesStr;

    private List<Long> scopeIds;

    private List<Long> autoApprovaledScopeIds;

    private Set<String> authorizedGrantTypes;

    private String authorizedGrantTypesStr;

    private List<Long> authorities;
}
