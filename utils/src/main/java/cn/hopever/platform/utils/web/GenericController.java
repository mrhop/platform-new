package cn.hopever.platform.utils.web;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public interface GenericController<T> {
    public Map getList(@RequestBody TableParameters body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    public T info(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    public VueResults.Result update(@RequestParam Long key, @RequestBody T t, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    public VueResults.Result update(@RequestParam(name = "key") Long key, @RequestParam(name = "files", required = false) MultipartFile[] files, T t, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    public VueResults.Result save(@RequestBody T t, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    public VueResults.Result save(@RequestParam(name = "files", required = false) MultipartFile[] files, T t, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    public void delete(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

}
