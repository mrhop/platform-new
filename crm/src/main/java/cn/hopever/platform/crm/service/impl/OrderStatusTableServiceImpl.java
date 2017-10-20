package cn.hopever.platform.crm.service.impl;

import cn.hopever.platform.crm.domain.OrderStatusTable;
import cn.hopever.platform.crm.domain.OrderTable;
import cn.hopever.platform.crm.repository.OrderStatusTableRepository;
import cn.hopever.platform.crm.repository.OrderTableRepository;
import cn.hopever.platform.crm.service.OrderStatusTableService;
import cn.hopever.platform.crm.vo.OrderStatusVo;
import cn.hopever.platform.crm.vo.OrderStatusVoAssembler;
import cn.hopever.platform.utils.web.SelectOption;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/10/10.
 */
@Service
@Transactional
public class OrderStatusTableServiceImpl implements OrderStatusTableService {

    @Autowired
    private OrderStatusTableRepository orderStatusTableRepository;
    @Autowired
    private OrderTableRepository orderTableRepository;

    @Autowired
    private OrderStatusVoAssembler orderStatusVoAssembler;


    @Override
    public List<SelectOption> getOrderStatusOptions(Principal principal) {
        Iterable<OrderStatusTable> orderStatusTables = orderStatusTableRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        List<SelectOption> list = new ArrayList<>();
        for (OrderStatusTable orderStatusTable : orderStatusTables) {
            list.add(new SelectOption(orderStatusTable.getName(), orderStatusTable.getId()));
        }
        return list;
    }

    @Override
    public List<SelectOption> getOrderStatusOptions(Principal principal, Long orderId) {
        OrderTable orderTable = orderTableRepository.findOne(orderId);
        String code = orderTable.getOrderStatusTable().getCode();
//        * 已创建created，报价中(quoting)，预签合同(precontract)，合同签订(contracted)，已收款(payed)，备货中(goodspreparing),暂无，，已发货(shipped)，已收货(received)，已完成并归档(finished).
        List<SelectOption> list = new ArrayList<>();
        if (code.equals("created") || code.equals("quoting")) {
            OrderStatusTable orderStatusTable = orderStatusTableRepository.findOneByCode("created");
            list.add(new SelectOption(orderStatusTable.getName(), orderStatusTable.getId()));
            orderStatusTable = orderStatusTableRepository.findOneByCode("quoting");
            list.add(new SelectOption(orderStatusTable.getName(), orderStatusTable.getId()));
            orderStatusTable = orderStatusTableRepository.findOneByCode("precontract");
            list.add(new SelectOption(orderStatusTable.getName(), orderStatusTable.getId()));
        } else if (code.equals("precontract")) {
            OrderStatusTable orderStatusTable = orderStatusTableRepository.findOneByCode("quoting");
            list.add(new SelectOption(orderStatusTable.getName(), orderStatusTable.getId()));
            orderStatusTable = orderStatusTableRepository.findOneByCode("contracted");
            list.add(new SelectOption(orderStatusTable.getName(), orderStatusTable.getId()));
        } else if (code.equals("contracted")) {
            OrderStatusTable orderStatusTable = orderStatusTableRepository.findOneByCode("precontract");
            list.add(new SelectOption(orderStatusTable.getName(), orderStatusTable.getId()));
            orderStatusTable = orderStatusTableRepository.findOneByCode("payed");
            list.add(new SelectOption(orderStatusTable.getName(), orderStatusTable.getId()));
        } else if (code.equals("payed")) {
            OrderStatusTable orderStatusTable = orderStatusTableRepository.findOneByCode("contracted");
            list.add(new SelectOption(orderStatusTable.getName(), orderStatusTable.getId()));
            orderStatusTable = orderStatusTableRepository.findOneByCode("shipped");
            list.add(new SelectOption(orderStatusTable.getName(), orderStatusTable.getId()));
        } else if (code.equals("shipped")) {
            OrderStatusTable orderStatusTable = orderStatusTableRepository.findOneByCode("payed");
            list.add(new SelectOption(orderStatusTable.getName(), orderStatusTable.getId()));
            orderStatusTable = orderStatusTableRepository.findOneByCode("received");
            list.add(new SelectOption(orderStatusTable.getName(), orderStatusTable.getId()));
        } else if (code.equals("received")) {
            OrderStatusTable orderStatusTable = orderStatusTableRepository.findOneByCode("shipped");
            list.add(new SelectOption(orderStatusTable.getName(), orderStatusTable.getId()));
            orderStatusTable = orderStatusTableRepository.findOneByCode("finished");
            list.add(new SelectOption(orderStatusTable.getName(), orderStatusTable.getId()));
        } else if (code.equals("finished")) {
            OrderStatusTable orderStatusTable = orderStatusTableRepository.findOneByCode("received");
            list.add(new SelectOption(orderStatusTable.getName(), orderStatusTable.getId()));
        }
        return list;
    }


    @Override
    public Page<OrderStatusVo> getList(TableParameters body, Principal principal) {
        PageRequest pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.ASC, "id");
        Page<OrderStatusTable> page = orderStatusTableRepository.findAll(pageRequest);
        List<OrderStatusVo> list = new ArrayList<>();
        for (OrderStatusTable orderStatusTable : page) {
            list.add(orderStatusVoAssembler.toResource(orderStatusTable));
        }
        return new PageImpl<>(list, pageRequest, page.getTotalElements());
    }

    @Override
    public void delete(Long id, Principal principal) {
        orderStatusTableRepository.delete(id);
    }

    @Override
    public OrderStatusVo info(Long id, Principal principal) {
        return orderStatusVoAssembler.toResource(orderStatusTableRepository.findOne(id));
    }

    @Override
    public VueResults.Result update(OrderStatusVo orderStatusVo, MultipartFile[] files, Principal principal) {
        orderStatusTableRepository.save(orderStatusVoAssembler.toDomain(orderStatusVo, orderStatusTableRepository.findOne(orderStatusVo.getId())));
        return null;
    }

    @Override
    public VueResults.Result save(OrderStatusVo orderStatusVo, MultipartFile[] files, Principal principal) {
        OrderStatusTable orderStatusTableTemp = orderStatusTableRepository.findOneByCode(orderStatusVo.getCode());
        if (orderStatusTableTemp != null) {
            return VueResults.generateError("新增失败", "已有相同的的记录");
        }
        OrderStatusTable orderStatusTable = new OrderStatusTable();
        orderStatusVoAssembler.toDomain(orderStatusVo, orderStatusTable);
        orderStatusTable.setCode(orderStatusVo.getCode());
        orderStatusTableRepository.save(orderStatusTable);
        return null;
    }
}
