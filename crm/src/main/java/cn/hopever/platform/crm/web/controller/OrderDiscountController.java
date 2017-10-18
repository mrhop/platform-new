package cn.hopever.platform.crm.web.controller;

import cn.hopever.platform.crm.service.ClientLevelTableService;
import cn.hopever.platform.crm.service.OrderDiscountTableService;
import cn.hopever.platform.crm.vo.OrderDiscountVo;
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
@RequestMapping(value = "/orderdiscount", produces = "application/json")
public class OrderDiscountController implements GenericController<OrderDiscountVo> {
    Logger logger = LoggerFactory.getLogger(OrderDiscountController.class);
    @Autowired
    private OrderDiscountTableService orderDiscountTableService;

    @Autowired
    private ClientLevelTableService clientLevelTableService;

    // 注意折上折的计算，因为有vip的存在
    @Override
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestBody TableParameters body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Page<OrderDiscountVo> list = orderDiscountTableService.getList(body, principal);
        Map<String, Object> map = new HashMap<>();
        List<HashMap<String, Object>> listReturn = null;
        if (list != null && list.iterator().hasNext()) {
            listReturn = new ArrayList<>();
            for (OrderDiscountVo cv : list) {
                HashMap<String, Object> mapTemp = new HashMap<>();
                mapTemp.put("key", cv.getId());
                List<Object> listTmp = new ArrayList<>();
                listTmp.add(cv.getName());
                listTmp.add(cv.getType());
                listTmp.add(cv.getQuota());
                listTmp.add(cv.getDiscount());
                listTmp.add(cv.getReduce());
                listTmp.add(cv.getClientLevelId());
                listTmp.add(cv.getBeginDate());
                listTmp.add(cv.getEndDate());
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
    public OrderDiscountVo info(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return orderDiscountTableService.info(key, principal);
    }

    @Override
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam Long key, @RequestBody OrderDiscountVo orderDiscountVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        orderDiscountVo.setId(key);
        return orderDiscountTableService.update(orderDiscountVo, null, principal);
    }

    @Override
    public VueResults.Result update(@RequestParam(name = "key") Long key, @RequestParam(name = "files", required = false) MultipartFile[] files, OrderDiscountVo orderDiscountVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestBody OrderDiscountVo orderDiscountVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return orderDiscountTableService.save(orderDiscountVo, null, principal);
    }

    @Override
    public VueResults.Result save(@RequestParam(name = "files", required = false) MultipartFile[] files, OrderDiscountVo orderDiscountVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        orderDiscountTableService.delete(key, principal);
    }

    @Override
    @RequestMapping(value = "/form/rulechange", method = {RequestMethod.GET, RequestMethod.POST})
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Map mapReturn = new HashMap<>();
        mapReturn.put("clientLevels", clientLevelTableService.getClientLevelOptions(principal));
        return mapReturn;
    }
}
