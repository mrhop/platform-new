package cn.hopever.platform.cms.web.controller;

import cn.hopever.platform.cms.service.BlockTableService;
import cn.hopever.platform.cms.vo.BlockVo;
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
@RequestMapping(value = "/block", produces = "application/json")
public class BlockController implements GenericController<BlockVo> {
    Logger logger = LoggerFactory.getLogger(BlockController.class);
    @Autowired
    private BlockTableService blockTableService;

    @Override
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    // 这个目前来看没有施展的余地
    public Map getList(@RequestBody TableParameters body, Principal principal) {
        Page<BlockVo> list = blockTableService.getList(body, principal);
        Map<String, Object> map = new HashMap<>();
        List<HashMap<String, Object>> listReturn = null;
        if (list != null && list.iterator().hasNext()) {
            listReturn = new ArrayList<>();
            for (BlockVo cv : list) {
                HashMap<String, Object> mapTemp = new HashMap<>();
                mapTemp.put("key", cv.getId());
                List<Object> listTmp = new ArrayList<>();
                listTmp.add(cv.getName());
                listTmp.add(cv.getPosition());
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
    public BlockVo info(@RequestParam Long key, Principal principal) {
        return blockTableService.info(key, principal);
    }

    // 应该是没有自行更新的时候，都是附着在其他比如template或者page上的更新
    // 在其他上面的更新都是前端先保存，然后统一更新，但是文件，比如图片等都是异步上传的
    // 此处仍然做处理
    @Override
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam Long key, @RequestBody BlockVo blockVo, Principal principal) {
        blockVo.setId(key);
        return blockTableService.update(blockVo, null, principal);
    }

    @Override
    public VueResults.Result update(@RequestParam(name = "key") Long key, @RequestParam(name = "screenshots", required = false) MultipartFile[] files, BlockVo blockVo, Principal principal) {
        return null;
    }

    @Override
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestBody BlockVo blockVo, Principal principal) {
        return blockTableService.save(blockVo, null, principal);
    }

    @Override
    public VueResults.Result save(@RequestParam(name = "screenshots", required = false) MultipartFile[] files, BlockVo blockVo, Principal principal) {
        return  null;
    }

    @Override
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key, Principal principal) {
        blockTableService.delete(key, principal);
    }

    @Override
    @RequestMapping(value = "/form/rulechange", method = {RequestMethod.GET, RequestMethod.POST})
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body, Principal principal) {
        // 没有过滤或者选项的更迭，因为是附着在页面或者template上做的更改，已然完成固定选择
        return null;
    }
}