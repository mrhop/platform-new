package cn.hopever.platform.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Created by Donghui Huo on 2016/9/7.
 */
@Configuration
@EnableResourceServer
public class ResourceServer extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .cors()
                .and()
                .requestMatchers().antMatchers("/resources/**")
                .and()
                .authorizeRequests()
//                .antMatchers("/resources/user/leftmenu").access("#oauth2.hasScope('internal_client') and isAuthenticated()")
                .antMatchers("/resources/user/leftmenu").permitAll()
                .antMatchers("/resources/user/relatedusers").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/resources/test/testresource").access("#oauth2.hasScope('internal_client') and ( hasRole('ROLE_super_admin') or hasRole('ROLE_admin'))")
                .and()
                .authorizeRequests()
                .antMatchers("/resources/test/testclientresource").access("(#oauth2.hasScope('internal_client') or #oauth2.hasScope('out_client')) and isAuthenticated()");
    }
}
