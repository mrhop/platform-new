package cn.hopever.platform.cms.web.controller;

import cn.hopever.platform.cms.config.CommonMethods;
import cn.hopever.platform.cms.service.MediaTagTableService;
import cn.hopever.platform.cms.vo.MediaTagVo;
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
@RequestMapping(value = "/mediatag", produces = "application/json")
public class MediaTagController implements GenericController<MediaTagVo> {
    Logger logger = LoggerFactory.getLogger(MediaTagController.class);
    @Autowired
    private MediaTagTableService mediaTagTableService;

    @Override
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestBody TableParameters body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Map filter = CommonMethods.generateInitFilter(body.getFilters(), httpServletRequest);
            if (filter != null && filter.containsKey("websiteId")) {
            Page<MediaTagVo> list = mediaTagTableService.getList(body, principal);
            Map<String, Object> map = new HashMap<>();
            List<HashMap<String, Object>> listReturn = null;
            if (list != null && list.iterator().hasNext()) {
                listReturn = new ArrayList<>();
                for (MediaTagVo cv : list) {
                    HashMap<String, Object> mapTemp = new HashMap<>();
                    mapTemp.put("key", cv.getId());
                    List<Object> listTmp = new ArrayList<>();
                    listTmp.add(cv.getName());
                    listTmp.add(cv.getTagId());
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
        return null;
    }

    @Override
    @RequestMapping(value = "/info", method = {RequestMethod.GET})
    public MediaTagVo info(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return mediaTagTableService.info(key, principal);
    }

    @Override
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam Long key, @RequestBody MediaTagVo mediaTagVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        mediaTagVo.setId(key);
        return mediaTagTableService.update(mediaTagVo, null, principal);
    }

    @Override
    public VueResults.Result update(@RequestParam(name = "key") Long key, @RequestParam(name = "screenshots", required = false) MultipartFile[] files, MediaTagVo mediaTagVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestBody MediaTagVo mediaTagVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Map<String, Long> keys = CommonMethods.generateKey(httpServletRequest);
        if (keys.get("websiteId") != null) {
            mediaTagVo.setWebsiteId(keys.get("websiteId"));
            return mediaTagTableService.save(mediaTagVo, null, principal);
        }
        return VueResults.generateError("创建失败", "模板必须和网站关联");
    }

    @Override
    public VueResults.Result save(@RequestParam(name = "screenshots", required = false) MultipartFile[] files, MediaTagVo mediaTagVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        mediaTagTableService.delete(key, principal);
    }

    @Override
    @RequestMapping(value = "/form/rulechange", method = {RequestMethod.GET, RequestMethod.POST})
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        //没有选项处理相关
        return null;
    }
}
