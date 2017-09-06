package cn.hopever.platform.cms.web.controller;

import cn.hopever.platform.cms.service.MediaTableService;
import cn.hopever.platform.cms.service.MediaTagTableService;
import cn.hopever.platform.cms.vo.MediaVo;
import cn.hopever.platform.utils.web.GenericController;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping(value = "/media", produces = "application/json")
public class MediaController implements GenericController<MediaVo> {

    Logger logger = LoggerFactory.getLogger(MediaTagController.class);
    @Autowired
    private MediaTableService mediaTableService;
    @Autowired
    private MediaTagTableService mediaTagTableService;

    @Override
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestBody TableParameters body, Principal principal) {
        Page<MediaVo> list = mediaTableService.getList(body, principal);
        Map<String, Object> map = new HashMap<>();
        List<HashMap<String, Object>> listReturn = null;
        if (list != null && list.iterator().hasNext()) {
            listReturn = new ArrayList<>();
            for (MediaVo cv : list) {
                HashMap<String, Object> mapTemp = new HashMap<>();
                mapTemp.put("key", cv.getId());
                List<Object> listTmp = new ArrayList<>();
                listTmp.add(cv.getName());
                listTmp.add(cv.getType());
                listTmp.add(cv.getMediaTagId());
                listTmp.add(cv.getFilename());
                listTmp.add(cv.getUrl());
                listTmp.add(cv.isPublished());
                listTmp.add(cv.getCreateUser());
                listTmp.add(cv.getCreatedDate());
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
    public MediaVo info(@RequestParam Long key, Principal principal) {
        return mediaTableService.info(key, principal);
    }

    @Override
    public VueResults.Result update(@RequestParam Long key, @RequestBody MediaVo mediaVo, Principal principal) {
        return null;
    }

    @Override
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam(name = "key") Long key, @RequestParam(name = "files", required = false) MultipartFile[] files, MediaVo mediaVo, Principal principal) {
        mediaVo.setId(key);
        return mediaTableService.update(mediaVo, files, principal);
    }

    @Override
    public VueResults.Result save(@RequestBody MediaVo mediaVo, Principal principal) {
        return null;
    }

    @Override
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestParam(name = "files", required = false) MultipartFile[] files, MediaVo mediaVo, Principal principal) {
        return mediaTableService.save(mediaVo, files, principal);
    }

    @Override
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key, Principal principal) {
        mediaTableService.delete(key, principal);
    }

    @Override
    @RequestMapping(value = "/form/rulechange", method = {RequestMethod.GET, RequestMethod.POST})
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body, Principal principal) {
        if (body != null && body.get("websiteId") != null) {
            Map mapReturn = new HashMap<>();
            mapReturn.put("mediaTags", mediaTagTableService.getOptionsByWebsiteId(Long.valueOf(body.get("websiteId").toString())));
            return mapReturn;
        }
        return null;
    }
}
