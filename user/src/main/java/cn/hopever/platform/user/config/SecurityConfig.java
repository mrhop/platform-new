package cn.hopever.platform.user.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

/**
 * Created by Donghui Huo on 2016/9/5.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BaseConfig baseConfig;

    public static Logger logger = LoggerFactory.getLogger(SecurityConfig.class);


    @Autowired
    @Qualifier("userTableService")
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder pe = null;
        try {
            pe = new BCryptPasswordEncoder(4, new SecureRandom(baseConfig.getPasswordRandom().getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            pe = new BCryptPasswordEncoder(4, new SecureRandom(baseConfig.getPasswordRandom().getBytes()));
        }
        return pe;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        org.springframework.security.oauth2.client.token.AccessTokenRequest a;
        org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter b;
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
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
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.html")
                .and()
                .formLogin()
                .loginProcessingUrl("/login").defaultSuccessUrl("/index.html")
                .failureUrl("/login.html#/?authentication_error=true")
                .loginPage("/login.html");
    }
}
