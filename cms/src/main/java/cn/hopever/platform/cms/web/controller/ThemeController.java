package cn.hopever.platform.cms.web.controller;

import cn.hopever.platform.cms.service.ThemeTableService;
import cn.hopever.platform.cms.vo.ThemeVo;
import cn.hopever.platform.utils.web.GenericController;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Donghui Huo on 2017/9/5.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/theme", produces = "application/json")
public class ThemeController implements GenericController<ThemeVo> {
    Logger logger = LoggerFactory.getLogger(ThemeController.class);
    @Autowired
    private ThemeTableService themeTableService;

    @Override
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestBody TableParameters body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Page<ThemeVo> list = themeTableService.getList(body, principal);
        Map<String, Object> map = new HashMap<>();
        List<HashMap<String, Object>> listReturn = null;
        if (list != null && list.iterator().hasNext()) {
            listReturn = new ArrayList<>();
            for (ThemeVo cv : list) {
                HashMap<String, Object> mapTemp = new HashMap<>();
                mapTemp.put("key", cv.getId());
                List<Object> listTmp = new ArrayList<>();
                listTmp.add(cv.getName());
                listTmp.add(cv.getThemeId());
                listTmp.add(cv.getRelatedUsers());
                mapTemp.put("value", listTmp);
                listReturn.add(mapTemp);
            }
            map.put("rows", listReturn);
            map.put("totalCount", list.getTotalElements());

        } else {
            map.put("rows", null);
            map.put("totalCount", 0);
        }
        map.put("pager", body.getPager());
        map.put("sorts", body.getSorts());
        return map;
    }

    @Override
    @RequestMapping(value = "/info", method = {RequestMethod.GET})
    public ThemeVo info(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return themeTableService.info(key, principal);
    }

    @Override
    public VueResults.Result update(@RequestParam Long key, @RequestBody ThemeVo themeVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam(name = "key") Long key, @RequestParam(name = "screenshotFiles", required = false) MultipartFile[] files, ThemeVo themeVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        themeVo.setId(key);
        return themeTableService.update(themeVo, files, principal);
    }

    @RequestMapping(value = "/updateinfo", method = {RequestMethod.POST})
    public VueResults.Result updateinfo(@RequestParam(name = "key") Long key, @RequestParam(name = "screenshotFiles", required = false) MultipartFile[] files, ThemeVo themeVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        themeVo.setId(key);
        themeTableService.update(themeVo, files, principal);
        return VueResults.generateSuccess("更新成功", "更新成功");
    }

    @Override
    public VueResults.Result save(@RequestBody ThemeVo themeVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestParam(name = "screenshotFiles", required = false) MultipartFile[] files, ThemeVo themeVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return themeTableService.save(themeVo, files, principal);
    }

    @Override
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        themeTableService.delete(key, principal);
    }

    @Override
    @RequestMapping(value = "/form/rulechange", method = {RequestMethod.GET, RequestMethod.POST})
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        // 需要关联用户的处理
        return null;
    }
}
