package cn.hopever.platform.utils.web;

import java.lang.annotation.*;

/**
 * Created by Donghui Huo on 2017/9/5.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ModuleAuthorize {
    String value();
}
