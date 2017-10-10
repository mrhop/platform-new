package cn.hopever.platform.crm.config;

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
        //稍后整个完成，并要开始新增处理client的时候，考虑单独做一个test的client进行处理
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers().antMatchers("/resources/crm/*")
                .and()
                .authorizeRequests()
                .antMatchers("/resources/crm/*").access("#oauth2.hasScope('internal_client') and hasRole('ROLE_crm_client')");
    }
}
