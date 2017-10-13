package cn.hopever.platform.crm.service.impl;

import cn.hopever.platform.crm.domain.OrderStatusTable;
import cn.hopever.platform.crm.repository.OrderStatusTableRepository;
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
