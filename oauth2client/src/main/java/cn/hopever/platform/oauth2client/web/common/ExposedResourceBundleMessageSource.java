package cn.hopever.platform.oauth2client.web.common;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by Donghui Huo on 2016/11/7.
 */
@Component("messageSource")
public class ExposedResourceBundleMessageSource extends ResourceBundleMessageSource {

    private static final Resource[] NO_RESOURCES = {};
    private Charset encoding = Charset.forName("UTF-8");
    private int cacheSeconds = -1;
    private boolean fallbackToSystemLocale = true;
    private boolean alwaysUseMessageFormat = false;
    public static final String WHOLE = "whole";
    private Map<String, Map> cachedData = new HashMap();

    public ExposedResourceBundleMessageSource() {
        String[] defaultBasename = {"messages", "common/messages/common"};
        super.setBasenames(defaultBasename);
        if (this.encoding != null) {
            this.setDefaultEncoding(this.encoding.name());
        }
        this.setFallbackToSystemLocale(this.fallbackToSystemLocale);
        this.setCacheSeconds(this.cacheSeconds);
        this.setAlwaysUseMessageFormat(this.alwaysUseMessageFormat);
        this.setDefaultEncoding("utf-8");
    }

    public Set getKeys(String basename, Locale locale) {
        ResourceBundle bundle = getResourceBundle(basename, locale);
        return bundle.keySet();
    }

    public Map getKeyValues(String basename, Locale locale) {
        String cacheKey = basename + locale.getCountry();
        if (cachedData.containsKey(cacheKey)) {
            return cachedData.get(cacheKey);
        }
        ResourceBundle bundle = getResourceBundle(basename, locale);
        if (bundle != null) {
            HashMap hashMap = new HashMap();
            for (String key : bundle.keySet()) {
                hashMap.put(key, this.getMessage(key, null, locale));
            }
            cachedData.put(cacheKey, hashMap);
            return hashMap;
        } else {
            return null;
        }

    }

    public Map getKeyValues(Locale locale) {
        String cacheKey = WHOLE + locale.getCountry();
        if (cachedData.containsKey(cacheKey)) {
            return cachedData.get(cacheKey);
        }
        HashMap hashMap = new HashMap();
        for (String basename : super.getBasenameSet()) {
            hashMap.putAll(getKeyValues(basename, locale));
        }
        cachedData.put(cacheKey, hashMap);
        return hashMap;
    }

    public void addBasenames(String[] basenames) {
        super.addBasenames(basenames);
    }
}
