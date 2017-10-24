package cn.hopever.platform.crm.web.controller;

import cn.hopever.platform.crm.config.CommonMethods;
import cn.hopever.platform.crm.service.RelatedUserTableService;
import cn.hopever.platform.crm.vo.RelatedUserVo;
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
//@CrossOrigin
@RequestMapping(value = "/relateduser", produces = "application/json")
// 登陆的时候给予自动添加，但是可以查询list，不能删除，可以修改
public class RelatedUserController implements GenericController<RelatedUserVo> {
    Logger logger = LoggerFactory.getLogger(RelatedUserController.class);
    @Autowired
    private RelatedUserTableService relatedUserTableService;

    @Override
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestBody TableParameters body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Page<RelatedUserVo> list = relatedUserTableService.getList(body, principal);
        Map<String, Object> map = new HashMap<>();
        List<HashMap<String, Object>> listReturn = null;
        if (list != null && list.iterator().hasNext()) {
            listReturn = new ArrayList<>();
            for (RelatedUserVo cv : list) {
                HashMap<String, Object> mapTemp = new HashMap<>();
                mapTemp.put("key", cv.getId());
                List<Object> listTmp = new ArrayList<>();
                listTmp.add(cv.getAccount());
                listTmp.add(cv.getName());
                listTmp.add(cv.getCustomDiscountStr());
                listTmp.add(cv.getLowerLimit());
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
    public RelatedUserVo info(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return relatedUserTableService.info(key, principal);
    }

    @Override
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam Long key, @RequestBody RelatedUserVo relatedUserVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        relatedUserVo.setId(key);
        return relatedUserTableService.update(relatedUserVo, null, principal);
    }

    @Override
    public VueResults.Result update(@RequestParam(name = "key") Long key, @RequestParam(name = "files", required = false) MultipartFile[] files, RelatedUserVo relatedUserVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    public VueResults.Result save(@RequestBody RelatedUserVo relatedUserVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    public VueResults.Result save(@RequestParam(name = "files", required = false) MultipartFile[] files, RelatedUserVo relatedUserVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    public void delete(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

    }

    @RequestMapping(value = "/validate", method = {RequestMethod.POST})
    public int validate(Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (CommonMethods.isAdmin(principal)) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }
}
