package cn.hopever.platform.user.domain;

import cn.hopever.platform.utils.json.JacksonUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.persistence.*;
import java.io.IOException;
import java.util.*;

/**
 * Created by Donghui Huo on 2016/9/8.
 */
@Entity
@Table(name = "platform_user_client")
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"authorities", "modules", "moduleRoles", "users"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.user.client")
public class ClientTable implements ClientDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "client_id", nullable = false, length = 50, unique = true)
    private String clientId;

    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Column(name = "client_secret", nullable = false, length = 120)
    private String clientSecret;

    @Column(name = "resource_ids", nullable = true)
    private String resourceIds;

    @Column(name = "secret_required", nullable = false)
    private boolean secretRequired = true;

    @Column(name = "scoped")
    private boolean scoped = true;

    @OneToMany(mappedBy = "client", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<ClientResouceScopeTable> clientResouceScopeTables;

    @Column(name = "authorized_grant_types", nullable = false)
    private String authorizedGrantTypes;

    @Column(name = "registered_redirect_uri", nullable = true)
    private String registeredRedirectUri;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "platform_user_client_client_role", joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<ClientRoleTable> authorities;

    @Column(name = "access_token_validity_seconds", nullable = true)
    private Integer accessTokenValiditySeconds;

    @Column(name = "refresh_token_validity_seconds", nullable = true)
    private Integer refreshTokenValiditySeconds;

    @Column(name = "additional_information", nullable = true, length = 2000)
    private String additionalInformation;

    @OneToMany(mappedBy = "client", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<ModuleRoleTable> moduleRoles;

    @OneToMany(mappedBy = "client", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<ModuleTable> modules;

    @ManyToMany(mappedBy = "clients")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<UserTable> users;


    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public void setResourceIds(Set<String> resourceIds) {
        if (resourceIds != null) {
            try {
                this.resourceIds = JacksonUtil.mapper.writeValueAsString(resourceIds);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.resourceIds = null;
        }
    }

    @Override
    public Set<String> getResourceIds() {
        if (this.resourceIds != null) {
            try {
                return JacksonUtil.mapper.readValue(this.resourceIds, Set.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Set<String> getScope() {
        if (this.getClientResouceScopeTables() != null) {
            Set<String> scopes = new LinkedHashSet<>();
            for (ClientResouceScopeTable clientResouceScopeTable : this.getClientResouceScopeTables()) {
                scopes.add(clientResouceScopeTable.getScope().getScopeId());
            }
            return scopes;
        }
        return null;
    }

    public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
        if (authorizedGrantTypes != null && authorizedGrantTypes.size() > 0) {
            try {
                this.authorizedGrantTypes = JacksonUtil.mapper.writeValueAsString(authorizedGrantTypes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.authorizedGrantTypes = null;
        }
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        if (this.authorizedGrantTypes != null) {
            try {
                return JacksonUtil.mapper.readValue(this.authorizedGrantTypes, Set.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void setRegisteredRedirectUri(String registeredRedirectUri) {
        this.registeredRedirectUri = registeredRedirectUri;
    }

    public void setRegisteredRedirectUri(Set<String> registeredRedirectUri) {
        if (registeredRedirectUri != null) {
            try {
                this.registeredRedirectUri = JacksonUtil.mapper.writeValueAsString(registeredRedirectUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        if (this.registeredRedirectUri != null) {
            try {
                return JacksonUtil.mapper.readValue(this.registeredRedirectUri, Set.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public void setAdditionalInformation(Map<String, Object> additionalInformation) {
        if (additionalInformation != null) {
            try {
                this.additionalInformation = JacksonUtil.mapper.writeValueAsString(additionalInformation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        if (this.additionalInformation != null) {
            try {
                return JacksonUtil.mapper.readValue(this.additionalInformation, Map.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        //需要根据scope来判定
        if (this.getClientResouceScopeTables() != null) {
            for (ClientResouceScopeTable clientResouceScopeTable : this.getClientResouceScopeTables()) {
                if (scope.equals(clientResouceScopeTable.getScope().getScopeId())) {
                    return clientResouceScopeTable.isAutoApprove();
                }
            }
        }
        return false;
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> set = new HashSet<GrantedAuthority>();
        if (this.authorities != null) {
            for (GrantedAuthority ga : this.authorities) {
                set.add(ga);
            }
            return set;
        }
        return null;
    }

    public List<ClientRoleTable> getAuthoritiesBasic() {
        return this.authorities;
    }

}
