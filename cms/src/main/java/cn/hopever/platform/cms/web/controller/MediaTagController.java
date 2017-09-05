package cn.hopever.platform.cms.web.controller;

import cn.hopever.platform.cms.vo.MediaTagVo;
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
@RequestMapping(value = "/mediatag", produces = "application/json")
public class MediaTagController implements GenericController<MediaTagVo> {
    @Override
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestBody TableParameters body, Principal principal) {
        return null;
    }

    @Override
    @RequestMapping(value = "/info", method = {RequestMethod.GET})
    public MediaTagVo info(@RequestParam Long key, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result update(@RequestParam Long key, @RequestBody MediaTagVo mediaTagVo, Principal principal) {
        return null;
    }

    @Override
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam(name = "key") Long key, @RequestParam(name = "screenshots", required = false) MultipartFile[] files, MediaTagVo mediaTagVo, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result save(@RequestBody MediaTagVo mediaTagVo, Principal principal) {
        return null;
    }

    @Override
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestParam(name = "screenshots", required = false) MultipartFile[] files, MediaTagVo mediaTagVo, Principal principal) {
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
