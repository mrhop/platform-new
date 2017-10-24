package cn.hopever.platform.crm.web.controller;

import cn.hopever.platform.crm.service.OrderProductTableService;
import cn.hopever.platform.crm.vo.OrderProductVo;
import cn.hopever.platform.utils.web.GenericController;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 订单的的商品必须提供orderid
 */
@RestController
//@CrossOrigin
@RequestMapping(value = "/orderproduct", produces = "application/json")
public class OrderProductController implements GenericController<OrderProductVo> {
    Logger logger = LoggerFactory.getLogger(OrderProductController.class);

    @Autowired
    private OrderProductTableService orderProductTableService;

    @Override
    public Map getList(@RequestBody TableParameters body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestParam Long orderId, @RequestBody TableParameters body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        List<OrderProductVo> list = orderProductTableService.getListByOrderId(body, principal, orderId);
        Map<String, Object> map = new HashMap<>();
        List<HashMap<String, Object>> listReturn = null;
        if (list != null && list.iterator().hasNext()) {
            listReturn = new ArrayList<>();
            for (OrderProductVo cv : list) {
                HashMap<String, Object> mapTemp = new HashMap<>();
                mapTemp.put("key", cv.getProductId());
                List<Object> listTmp = new ArrayList<>();
                listTmp.add(cv.getProductCode());
                listTmp.add(cv.getProductName());
                listTmp.add(cv.getNum());
                listTmp.add(cv.getProductSalePrice());
                listTmp.add(cv.getProductCostPrice());
                mapTemp.put("value", listTmp);
                listReturn.add(mapTemp);
            }
            map.put("rows", listReturn);
            map.put("totalCount", list.size());

        } else {
            map.put("rows", null);
            map.put("totalCount", 0);
        }
        return map;
    }

    @Override
    public OrderProductVo info(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    public VueResults.Result update(@RequestParam Long key, @RequestBody OrderProductVo orderProductVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @RequestMapping(value = "/updatenum", method = {RequestMethod.POST})
    public VueResults.Result updateNum(@RequestParam Long key, @RequestParam float num, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return orderProductTableService.updateNumById(key, num);
    }

    @Override
    public VueResults.Result update(@RequestParam(name = "key") Long key, @RequestParam(name = "files", required = false) MultipartFile[] files, OrderProductVo orderProductVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    public VueResults.Result save(@RequestBody OrderProductVo orderProductVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return orderProductTableService.save(orderProductVo, null, principal);
    }

    @RequestMapping(value = "/save", method = {RequestMethod.GET})
    public VueResults.Result save(@RequestParam(name = "orderId") Long orderId, @RequestParam(name = "productId") Long productId, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        OrderProductVo orderProductVo = new OrderProductVo();
        orderProductVo.setOrderId(orderId);
        orderProductVo.setProductId(productId);
        return orderProductTableService.save(orderProductVo, null, principal);
    }

    @Override
    public VueResults.Result save(@RequestParam(name = "files", required = false) MultipartFile[] files, OrderProductVo orderProductVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    public void delete(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void deleteWithRestrict(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        orderProductTableService.deleteWithRestrict(key, principal);
    }

    @Override
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }
}
