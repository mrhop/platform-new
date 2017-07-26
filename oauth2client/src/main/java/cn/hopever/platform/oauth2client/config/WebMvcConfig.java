package cn.hopever.platform.oauth2client.config;

import cn.hopever.platform.oauth2client.web.interceptor.CommonInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.MappedInterceptor;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Locale;

/**
 * Created by Donghui Huo on 2016/9/21.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private CommonInterceptor commonInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/api/*").allowedOrigins("*");
    }

    @Bean(name = "localeResolver")
    public LocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setCookieName("locale");
        localeResolver.setDefaultLocale(Locale.CHINA);
        return localeResolver;
    }


    @Bean
    public MappedInterceptor timingInterceptor() {
        return new MappedInterceptor(null, new String[]{"/webjars/**", "/static/**", "/error/*.html", "/api/*.html"}, commonInterceptor);
    }

}