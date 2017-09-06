package cn.hopever.platform.cms.web.controller;

import cn.hopever.platform.cms.service.StaticResourceTableService;
import cn.hopever.platform.cms.vo.StaticResourceVo;
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
@RequestMapping(value = "/staticresource", produces = "application/json")
public class StaticResourceController implements GenericController<StaticResourceVo> {
    Logger logger = LoggerFactory.getLogger(StaticResourceController.class);
    @Autowired
    private StaticResourceTableService staticResourceTableService;

    @Override
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestBody TableParameters body, Principal principal) {
        Page<StaticResourceVo> list = staticResourceTableService.getList(body, principal);
        Map<String, Object> map = new HashMap<>();
        List<HashMap<String, Object>> listReturn = null;
        if (list != null && list.iterator().hasNext()) {
            listReturn = new ArrayList<>();
            for (StaticResourceVo cv : list) {
                HashMap<String, Object> mapTemp = new HashMap<>();
                mapTemp.put("key", cv.getId());
                List<Object> listTmp = new ArrayList<>();
                listTmp.add(cv.getName());
                listTmp.add(cv.getFilename());
                listTmp.add(cv.getFileType());
                listTmp.add(cv.getSize());
                listTmp.add(cv.getResourceOrder());
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
    public StaticResourceVo info(@RequestParam Long key, Principal principal) {
        return staticResourceTableService.info(key,principal);
    }

    @Override
    public VueResults.Result update(@RequestParam Long key, @RequestBody StaticResourceVo staticResourceVo, Principal principal) {
        return null;
    }

    @Override
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam(name = "key") Long key, @RequestParam(name = "files", required = false) MultipartFile[] files, StaticResourceVo staticResourceVo, Principal principal) {
        staticResourceVo.setId(key);
        return staticResourceTableService.update(staticResourceVo,files,principal);
    }

    @Override
    public VueResults.Result save(@RequestBody StaticResourceVo staticResourceVo, Principal principal) {
        return null;
    }

    @Override
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestParam(name = "files", required = false) MultipartFile[] files, StaticResourceVo staticResourceVo, Principal principal) {
        return staticResourceTableService.save(staticResourceVo,files,principal);
    }

    @Override
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key, Principal principal) {
        staticResourceTableService.delete(key, principal);
    }

    @Override
    @RequestMapping(value = "/form/rulechange", method = {RequestMethod.GET, RequestMethod.POST})
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body, Principal principal) {
        //没有相关的改变
        return null;
    }
}
