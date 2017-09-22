package cn.hopever.platform.cms.config;

import cn.hopever.platform.utils.web.CookieUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Donghui Huo on 2017/9/22.
 */
public class CommonMethods {

    public static Map generateInitFilter(Map filter, HttpServletRequest httpServletRequest) {
        if (filter == null) {
            filter = new HashMap<String, Object>();
        }
        Cookie c = CookieUtil.getCookieByName("current-website", httpServletRequest.getCookies());
        if (c != null) {
            filter.put("websiteId", Long.valueOf(c.getValue()));
        } else {
            //目前先执行测试操作
            c = CookieUtil.getCookieByName("current-theme", httpServletRequest.getCookies());
            if (c == null) {
                // flag 临时测试
                // return null;
            }
            // 此处做临时测试
//            filter.put("themeId", Long.valueOf(c.getValue()));
            // body.getFilters().put("themeId", 5L);
            filter.put("websiteId", 1L);
//            filter.put("themeId", 1L);
        }
        return filter;
    }

    public static Map<String, Long> generateKey(HttpServletRequest httpServletRequest) {
        Map<String, Long> map = new HashMap<String, Long>();
        Cookie c = CookieUtil.getCookieByName("current-website", httpServletRequest.getCookies());
        if (c != null) {
            map.put("websiteId", Long.valueOf(c.getValue()));
        } else {
            //目前先执行测试操作
            c = CookieUtil.getCookieByName("current-theme", httpServletRequest.getCookies());
            if (c == null) {
                // flag 临时测试
                // return null;
            }
            // 此处做临时测试
//            map.put("themeId", Long.valueOf(c.getValue()));
            map.put("websiteId", 1L);
//            map.put("themeId", 1L);
        }
        return map;
    }
}
