package cn.hopever.platform.crm.service.impl;

import cn.hopever.platform.crm.config.CommonMethods;
import cn.hopever.platform.crm.domain.OrderStatusTable;
import cn.hopever.platform.crm.domain.OrderTable;
import cn.hopever.platform.crm.repository.*;
import cn.hopever.platform.crm.service.OrderTableService;
import cn.hopever.platform.crm.vo.OrderVo;
import cn.hopever.platform.crm.vo.OrderVoAssembler;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.*;

/**
 * Created by Donghui Huo on 2017/10/10.
 */
@Service
@Transactional
public class OrderTableServiceImpl implements OrderTableService {

    @Autowired
    private OrderTableRepository orderTableRepository;

    @Autowired
    private CustomOrderTableRepository customOrderTableRepository;

    @Autowired
    private RelatedUserTableRepository relatedUserTableRepository;
    @Autowired
    private ClientTableRepository clientTableRepository;
    @Autowired
    private CountryTableRepository countryTableRepository;
    @Autowired
    private OrderStatusTableRepository orderStatusTableRepository;
    @Autowired
    private PayTypeTableRepository payTypeTableRepository;
    @Autowired
    private DeliveryMethodTableRepository deliveryMethodTableRepository;

    @Autowired
    private OrderVoAssembler orderVoAssembler;

    @Override
    public Page<OrderVo> getList(TableParameters body, Principal principal) {
        Map<String, Object> map = body.getFilters();
        if (map == null) {
            map = new HashMap<>();
        }
        // 根据用户的规格查看这个用户可以处理的client
        if (!CommonMethods.isAdmin(principal)) {
            map.put("createdUser", relatedUserTableRepository.findOneByAccount(principal.getName()));
        } else {
            if (map.get("createdUserId") != null) {
                map.put("createdUser", relatedUserTableRepository.findOne(Long.valueOf(map.get("createdUserId").toString())));
                map.remove("createdUserId");
            }
        }

        if (map.get("clientId") != null) {
            map.put("clientTable", clientTableRepository.findOne(Long.valueOf(map.get("clientId").toString())));
            map.remove("clientId");
        }
        if (map.get("orderStatusId") != null) {
            map.put("orderStatusTable", orderStatusTableRepository.findOne(Long.valueOf(map.get("orderStatusId").toString())));
            map.remove("orderStatusId");
        }
        if (map.get("countryId") != null) {
            map.put("countryTable", countryTableRepository.findOne(Long.valueOf(map.get("countryId").toString())));
            map.remove("countryId");
        }
        body.setFilters(map);
        PageRequest pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.ASC, "id");
        Page<OrderTable> page = customOrderTableRepository.findByFilters(body.getFilters(), pageRequest);
        List<OrderVo> list = new ArrayList<>();
        for (OrderTable orderTable : page) {
            list.add(orderVoAssembler.toResource(orderTable));
        }
        return new PageImpl<>(list, pageRequest, page.getTotalElements());
    }

    @Override
    public void delete(Long id, Principal principal) {
        OrderStatusTable orderStatusTable = orderTableRepository.findOne(id).getOrderStatusTable();
        if (orderStatusTable.getCode().equals("created") || orderStatusTable.getCode().equals("offer") || orderStatusTable.getCode().equals("preContract")) {
            orderTableRepository.delete(id);
        }
    }

    @Override
    public OrderVo info(Long id, Principal principal) {
        OrderTable orderTable = orderTableRepository.findOne(id);
        if (!CommonMethods.isAdmin(principal)) {
            // 判断是否有查看的权限
            if (!orderTable.getCreatedUser().getAccount().equals(principal.getName())) {
                return null;
            }
        }
        return orderVoAssembler.toResource(orderTable);
    }

    @Override
    public VueResults.Result update(OrderVo orderVo, MultipartFile[] files, Principal principal) {
        OrderTable orderTable = orderTableRepository.findOne(orderVo.getId());
        if (!CommonMethods.isAdmin(principal)) {
            // 判断是否有查看的权限
            if (!orderTable.getCreatedUser().getAccount().equals(principal.getName())) {
                return VueResults.generateError("更新失败", "没有权限");
            }
        }
        orderVoAssembler.toDomain(orderVo, orderTable);
        OrderStatusTable orderStatusTable = orderStatusTableRepository.findOne(orderVo.getOrderStatusId());
        if (orderStatusTable.getCode().equals("contract")) {
            orderTable.setSalePrice(orderVo.getSalePrice());
            orderTable.setContractSignDate(new Date(orderVo.getContractSignDate()));
        }
        if (orderStatusTable.getCode().equals("finished")) {
            orderTable.setFinishedDate(new Date());
        }

        if (orderStatusTable.getCode().equals("payed")) {
            orderTable.setPayTypeTable(payTypeTableRepository.findOne(orderVo.getPayTypeId()));
        }
        if (orderStatusTable.getCode().equals("delivered")) {
            orderTable.setDeliveryDate(new Date(orderVo.getDeliveryDate()));
            orderTable.setDeliveryMethodTable(deliveryMethodTableRepository.findOne(orderVo.getDeliveryMethodId()));
            orderTable.setTracingNumber(orderVo.getTracingNumber());
            orderTable.setFreight(orderVo.getFreight());
        }

        if (orderStatusTable.getCode().equals("created") || orderStatusTable.getCode().equals("offer") || orderStatusTable.getCode().equals("preContract")) {
            if (orderVo.getClientId() != null) {
                orderTable.setClientTable(clientTableRepository.findOne(orderVo.getClientId()));
            }
            if (orderVo.getCountryId() != null) {
                orderTable.setCountryTable(countryTableRepository.findOne(orderVo.getCountryId()));
            }
            orderTable.setDiscountType(orderVo.getDiscountType());
            orderTable.setDiscount(orderVo.getDiscount());
            orderTable.setPreQuotation(orderVo.getPreQuotation());
            orderTable.setCostPrice(orderVo.getCostPrice());
        }
        orderTable.setOrderStatusTable(orderStatusTable);
        orderTableRepository.save(orderTable);
        return null;
    }

    @Override
    public VueResults.Result save(OrderVo orderVo, MultipartFile[] files, Principal principal) {
        OrderTable orderTable = new OrderTable();
        orderVoAssembler.toDomain(orderVo, orderTable);
        orderTable.setCode(CommonMethods.generateCode("order"));
        orderTable.setCreatedDate(new Date());
        orderTable.setCreatedUser(relatedUserTableRepository.findOneByAccount(principal.getName()));
        if (orderVo.getClientId() != null) {
            orderTable.setClientTable(clientTableRepository.findOne(orderVo.getClientId()));
        }
        if (orderVo.getCountryId() != null) {
            orderTable.setCountryTable(countryTableRepository.findOne(orderVo.getCountryId()));
        }
        orderTable.setDiscountType(orderVo.getDiscountType());
        orderTable.setDiscount(orderVo.getDiscount());
        orderTable.setPreQuotation(orderVo.getPreQuotation());
        orderTable.setCostPrice(orderVo.getCostPrice());
        orderTableRepository.save(orderTable);
        return null;
    }
}
