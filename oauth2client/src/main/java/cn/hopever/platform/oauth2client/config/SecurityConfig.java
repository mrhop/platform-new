package cn.hopever.platform.oauth2client.config;

import cn.hopever.platform.oauth2client.security.RemoteOauth2AuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by Donghui Huo on 2016/9/5.
 */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public static Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    @Qualifier("removeAccessTokenHandler")
    private LogoutHandler removeAccessTokenHandler;

    @Bean
    public RemoteOauth2AuthenticationProvider authenticationProvider() {
        RemoteOauth2AuthenticationProvider authenticationProvider = new RemoteOauth2AuthenticationProvider();
        return authenticationProvider;
    }


    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/webjars/**", "/static/**", "/error/*.html", "/index.html", "/gettokenbycode", "/gettokenbyclient");
        web.ignoring().antMatchers("/webjars/**", "/static/**", "/error/*.html");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/login.html#/?authorization_error=true")
                .and()
                .csrf()
                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
                .disable()
                .logout()
                .addLogoutHandler(removeAccessTokenHandler)
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.html")
                .and()
                .formLogin()
                .loginProcessingUrl("/login").defaultSuccessUrl("/index.html")
                .failureUrl("/login.html#/?authentication_error=true")
                .loginPage("/login.html");
    }
}
