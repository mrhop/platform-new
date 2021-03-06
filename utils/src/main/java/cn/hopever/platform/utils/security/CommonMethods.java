package cn.hopever.platform.utils.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Donghui Huo on 2017/9/22.
 */
public class CommonMethods {

    // 因为moduleRule的话在Before的时候就做处理了，所以此处是判断是否管理员
    // 根据用户是否是管理员，来判断该用户是否
    public static boolean isAdmin(Principal principal) {
        Collection<? extends GrantedAuthority> authorities = ((Authentication) principal).getAuthorities();
        if (authorities != null) {
            for (GrantedAuthority authority : authorities) {
                if ("ROLE_super_admin".equals(authority.getAuthority()) || "ROLE_admin".equals(authority.getAuthority())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String generateCode(String type) {
        String returnStr = "" + new Date().getTime() + "-" + ThreadLocalRandom.current().nextInt(100000, 1000000);
        ;
        if ("client".equals(type)) {
            return 'c' + returnStr;
        } else if ("order".equals(type)) {
            return 'o' + returnStr;
        } else if ("product".equals(type)) {
            return 'p' + returnStr;
        }
        return null;
    }
}
