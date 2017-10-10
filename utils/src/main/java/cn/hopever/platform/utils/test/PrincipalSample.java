package cn.hopever.platform.utils.test;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/1.
 */
@Data
public class PrincipalSample implements Authentication {
    private String name;

    private List<GrantedAuthority> grantedAuthorities;


    public PrincipalSample() {
    }

    public PrincipalSample(String name) {
        this.name = name;
    }

    public PrincipalSample(String name, List<GrantedAuthority> grantedAuthorities) {
        this.name = name;
        this.grantedAuthorities = grantedAuthorities;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.name;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }
}
