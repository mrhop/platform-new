package cn.hopever.platform.utils.web;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Map;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public interface GenericController<T> {

    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestBody TableParameters body, Principal principal);

    @RequestMapping(value = "/info", method = {RequestMethod.GET})
    public T info(@RequestParam Long key, Principal principal);

    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam Long key, @RequestBody T t, Principal principal);

    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam(name = "key") Long key, @RequestParam(name = "files", required = false) MultipartFile[] files, T t, Principal principal);

    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestBody T t, Principal principal);

    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestParam(name = "files", required = false) MultipartFile[] files, T t, Principal principal);

    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key, Principal principal);

    @RequestMapping(value = "/form/rulechange", method = {RequestMethod.GET, RequestMethod.POST})
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body, Principal principal);

}
