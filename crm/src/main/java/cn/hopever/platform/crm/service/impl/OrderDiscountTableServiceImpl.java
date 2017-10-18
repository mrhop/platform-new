package cn.hopever.platform.crm.service.impl;

import cn.hopever.platform.crm.domain.OrderDiscountTable;
import cn.hopever.platform.crm.repository.ClientLevelTableRepository;
import cn.hopever.platform.crm.repository.OrderDiscountTableRepository;
import cn.hopever.platform.crm.repository.impl.CustomOrderDiscountTableRepositoryImpl;
import cn.hopever.platform.crm.service.OrderDiscountTableService;
import cn.hopever.platform.crm.vo.OrderDiscountVo;
import cn.hopever.platform.crm.vo.OrderDiscountVoAssembler;
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
public class OrderDiscountTableServiceImpl implements OrderDiscountTableService {

    @Autowired
    private OrderDiscountTableRepository orderDiscountTableRepository;

    @Autowired
    private CustomOrderDiscountTableRepositoryImpl customOrderDiscountTableRepository;

    @Autowired
    private ClientLevelTableRepository clientLevelTableRepository;

    @Autowired
    private OrderDiscountVoAssembler orderDiscountVoAssembler;

    @Override
    public Page<OrderDiscountVo> getList(TableParameters body, Principal principal) {
        Map<String, Object> map = body.getFilters();
        if (map == null) {
            map = new HashMap<>();
        }
        if (map.get("clientLevelId") != null) {
            map.put("clientLevelTable", clientLevelTableRepository.findOne(Long.valueOf(map.get("clientLevelId").toString())));
            map.remove("clientLevelId");
        }
        PageRequest pageRequest;
        if (body.getSorts() == null || body.getSorts().isEmpty()) {
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.DESC, "createdDate");
        } else {
            String key = body.getSorts().keySet().iterator().next();
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.fromString(body.getSorts().get(key)), key);
        }
        Page<OrderDiscountTable> page = customOrderDiscountTableRepository.findByFilters(body.getFilters(), pageRequest);
        List<OrderDiscountVo> list = new ArrayList<>();
        for (OrderDiscountTable orderDiscountTable : page) {
            OrderDiscountVo orderDiscountVo = orderDiscountVoAssembler.toResource(orderDiscountTable);
            list.add(orderDiscountVo);
        }
        return new PageImpl<>(list, pageRequest, page.getTotalElements());
    }

    @Override
    public void delete(Long id, Principal principal) {
        orderDiscountTableRepository.delete(id);
    }

    @Override
    public OrderDiscountVo info(Long id, Principal principal) {
        return orderDiscountVoAssembler.toResource(orderDiscountTableRepository.findOne(id));
    }

    @Override
    public VueResults.Result update(OrderDiscountVo orderDiscountVo, MultipartFile[] files, Principal principal) {
        OrderDiscountTable orderDiscountTable = orderDiscountTableRepository.findOne(orderDiscountVo.getId());
        orderDiscountTable = orderDiscountVoAssembler.toDomain(orderDiscountVo, orderDiscountTable);
        if (orderDiscountVo.getClientLevelId() != null) {
            orderDiscountTable.setClientLevelTable(clientLevelTableRepository.findOne(orderDiscountVo.getClientLevelId()));
        }
        orderDiscountTableRepository.save(orderDiscountTable);
        return null;
    }

    @Override
    public VueResults.Result save(OrderDiscountVo orderDiscountVo, MultipartFile[] files, Principal principal) {
        OrderDiscountTable orderDiscountTable = new OrderDiscountTable();
        orderDiscountTable = orderDiscountVoAssembler.toDomain(orderDiscountVo, orderDiscountTable);
        if (orderDiscountVo.getClientLevelId() != null) {
            orderDiscountTable.setClientLevelTable(clientLevelTableRepository.findOne(orderDiscountVo.getClientLevelId()));
        }
        orderDiscountTable.setCreatedDate(new Date());
        orderDiscountTableRepository.save(orderDiscountTable);
        return null;
    }
}
