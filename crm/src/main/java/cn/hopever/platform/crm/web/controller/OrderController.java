package cn.hopever.platform.crm.web.controller;

import cn.hopever.platform.crm.config.CommonMethods;
import cn.hopever.platform.crm.service.*;
import cn.hopever.platform.crm.vo.OrderVo;
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
@RequestMapping(value = "/order", produces = "application/json")
public class OrderController implements GenericController<OrderVo> {
    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderTableService orderTableService;

    @Autowired
    private CountryTableService countryTableService;

    @Autowired
    private RelatedUserTableService relatedUserTableService;

    @Autowired
    private ClientTableService clientTableService;

    @Autowired
    private DeliveryMethodTableService deliveryMethodTableService;

    @Autowired
    private PayTypeTableService payTypeTableService;
    @Autowired
    private OrderStatusTableService orderStatusTableService;

    @Override
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestBody TableParameters body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Page<OrderVo> list = orderTableService.getList(body, principal);
        Map<String, Object> map = new HashMap<>();
        List<HashMap<String, Object>> listReturn = null;
        if (list != null && list.iterator().hasNext()) {
            listReturn = new ArrayList<>();
            for (OrderVo cv : list) {
                HashMap<String, Object> mapTemp = new HashMap<>();
                mapTemp.put("key", cv.getId());
                List<Object> listTmp = new ArrayList<>();
                listTmp.add(cv.getCode());
                listTmp.add(cv.getOrderStatusId());
                listTmp.add(cv.getClientId());
                listTmp.add(cv.getCostPrice());
                listTmp.add(cv.getPreQuotation());
                listTmp.add(cv.getSalePrice());
                listTmp.add(cv.getTracingNumber());
                listTmp.add(cv.getCountryId());
                listTmp.add(cv.getCreatedDate());
                listTmp.add(cv.getCreatedUserId());
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
    public OrderVo info(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return orderTableService.info(key, principal);
    }

    @Override
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam Long key, @RequestBody OrderVo orderVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        orderVo.setId(key);
        return orderTableService.update(orderVo, null, principal);
    }

    @Override
    public VueResults.Result update(@RequestParam(name = "key") Long key, @RequestParam(name = "files", required = false) MultipartFile[] files, OrderVo orderVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestBody OrderVo orderVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return orderTableService.save(orderVo, null, principal);
    }

    @Override
    public VueResults.Result save(@RequestParam(name = "files", required = false) MultipartFile[] files, OrderVo orderVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        orderTableService.delete(key, principal);
    }

    @Override
    @RequestMapping(value = "/form/rulechange", method = {RequestMethod.GET, RequestMethod.POST})
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Map mapReturn = new HashMap<>();
        if (body != null) {
            // 给出options
            mapReturn.put("clients", clientTableService.getClientOptions(principal));

            mapReturn.put("countries", countryTableService.getCountryOptions(principal));
            mapReturn.put("orderStatuses", orderStatusTableService.getOrderStatusOptions(principal));
            if ("list".equals(body.get("type"))) {
                if (CommonMethods.isAdmin(principal)) {
                    // 进行用户列表的返回
                    mapReturn.put("createdUsers", relatedUserTableService.getRelatedUserOptions(principal));
                }
            } else if ("form".equals(body.get("type"))) {
                mapReturn.put("deliveryMethods", deliveryMethodTableService.getDeliveryMethodOptions(principal));
                mapReturn.put("payTypes", payTypeTableService.getPayTypeOptions(principal));
            }
        }
        return mapReturn;
    }
}
