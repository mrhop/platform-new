package cn.hopever.platform.oauth2client.web.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Donghui Huo on 2016/11/7.
 */
@Component
public class LocaleMessageSource {

    @Resource
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    public String getMessage(String code, HttpServletRequest request) {
        return messageSource.getMessage(code, null, localeResolver.resolveLocale(request));
    }

    public String getMessage(String code, Object[] args, HttpServletRequest request) {
        return messageSource.getMessage(code, args, localeResolver.resolveLocale(request));
    }
}
