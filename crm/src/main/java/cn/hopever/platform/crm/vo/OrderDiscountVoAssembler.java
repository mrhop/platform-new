package cn.hopever.platform.crm.vo;

import cn.hopever.platform.crm.domain.OrderDiscountTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class OrderDiscountVoAssembler implements GenericVoAssembler<OrderDiscountVo, OrderDiscountTable> {

    @Override
    public OrderDiscountVo toResource(OrderDiscountTable orderDiscountTable) {
        OrderDiscountVo orderDiscountVo = new OrderDiscountVo();
        BeanUtils.copyNotNullProperties(orderDiscountTable, orderDiscountVo);
        if (orderDiscountTable.getBeginDate() != null) {
            orderDiscountVo.setBeginDate(orderDiscountTable.getBeginDate().getTime());
        }
        if (orderDiscountTable.getClientLevelTable() != null) {
            orderDiscountVo.setClientLevelId(orderDiscountTable.getClientLevelTable().getId());
            orderDiscountVo.setClientLevelName(orderDiscountTable.getClientLevelTable().getName());
        }
        if (orderDiscountTable.getCreatedDate() != null) {
            orderDiscountVo.setCreatedDate(orderDiscountTable.getCreatedDate().getTime());
        }
        if (orderDiscountTable.getEndDate() != null) {
            orderDiscountVo.setEndDate(orderDiscountTable.getEndDate().getTime());
        }
        return orderDiscountVo;
    }

    @Override
    public OrderDiscountTable toDomain(OrderDiscountVo orderDiscountVo, OrderDiscountTable orderDiscountTable) {
        BeanUtils.copyNotNullProperties(orderDiscountVo, orderDiscountTable, "id", "createdDate");
        if (orderDiscountVo.getBeginDate() != null) {
            orderDiscountTable.setBeginDate(new Date(orderDiscountVo.getBeginDate()));
        }
        if (orderDiscountVo.getEndDate() != null) {
            orderDiscountTable.setEndDate(new Date(orderDiscountVo.getEndDate()));
        }
        return orderDiscountTable;
    }
}
