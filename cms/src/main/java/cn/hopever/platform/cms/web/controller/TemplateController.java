package cn.hopever.platform.cms.web.controller;

import cn.hopever.platform.cms.service.TemplateTableService;
import cn.hopever.platform.cms.vo.TemplateVo;
import cn.hopever.platform.utils.web.GenericController;
import cn.hopever.platform.utils.web.ModuleAuthorize;
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
@RequestMapping(value = "/template", produces = "application/json")
public class TemplateController implements GenericController<TemplateVo> {
    Logger logger = LoggerFactory.getLogger(TemplateController.class);
    @Autowired
    private TemplateTableService templateTableService;

    @Override
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    @ModuleAuthorize("templateList")
    public Map getList(@RequestBody TableParameters body, Principal principal) {
        Page<TemplateVo> list = templateTableService.getList(body, principal);
        Map<String, Object> map = new HashMap<>();
        List<HashMap<String, Object>> listReturn = null;
        if (list != null && list.iterator().hasNext()) {
            listReturn = new ArrayList<>();
            for (TemplateVo cv : list) {
                HashMap<String, Object> mapTemp = new HashMap<>();
                mapTemp.put("key", cv.getId());
                List<Object> listTmp = new ArrayList<>();
                listTmp.add(cv.getName());
                listTmp.add(cv.getContentPosition());
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
    public TemplateVo info(@RequestParam Long key, Principal principal) {
        return templateTableService.info(key, principal);
    }

    @Override
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam Long key, @RequestBody TemplateVo templateVo, Principal principal) {
        templateVo.setId(key);
        return templateTableService.update(templateVo,null,principal);
    }

    @Override
    public VueResults.Result update(@RequestParam(name = "key") Long key, @RequestParam(name = "files", required = false) MultipartFile[] files, TemplateVo templateVo, Principal principal) {
        return null;
    }

    @Override
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestBody TemplateVo templateVo, Principal principal) {
        return templateTableService.save(templateVo,null,principal);
    }

    @Override
    public VueResults.Result save(@RequestParam(name = "files", required = false) MultipartFile[] files, TemplateVo templateVo, Principal principal) {
        return null;
    }

    @Override
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key, Principal principal) {
        templateTableService.delete(key, principal);
    }

    @Override
    @RequestMapping(value = "/form/rulechange", method = {RequestMethod.GET, RequestMethod.POST})
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body, Principal principal) {
        //没有相关的变化
        return null;
    }
}