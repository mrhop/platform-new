package cn.hopever.platform.cms.web.controller;

import cn.hopever.platform.cms.vo.ThemeVo;
import cn.hopever.platform.utils.web.GenericController;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Map;

/**
 * Created by Donghui Huo on 2017/9/5.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/theme", produces = "application/json")
public class ThemeController implements GenericController<ThemeVo> {
    @Override
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestBody TableParameters body, Principal principal) {
        return null;
    }

    @Override
    @RequestMapping(value = "/info", method = {RequestMethod.GET})
    public ThemeVo info(@RequestParam Long key, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result update(@RequestParam Long key, @RequestBody ThemeVo themeVo, Principal principal) {
        return null;
    }

    @Override
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam(name = "key") Long key, @RequestParam(name = "screenshots", required = false) MultipartFile[] files, ThemeVo themeVo, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result save(@RequestBody ThemeVo themeVo, Principal principal) {
        return null;
    }

    @Override
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestParam(name = "screenshots", required = false) MultipartFile[] files, ThemeVo themeVo, Principal principal) {
        return null;
    }

    @Override
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key, Principal principal) {

    }

    @Override
    @RequestMapping(value = "/form/rulechange", method = {RequestMethod.GET, RequestMethod.POST})
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body, Principal principal) {
        return null;
    }
}
