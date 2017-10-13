package cn.hopever.platform.crm.vo;

import cn.hopever.platform.crm.domain.OrderStatusTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class OrderStatusVoAssembler implements GenericVoAssembler<OrderStatusVo, OrderStatusTable> {

    @Override
    public OrderStatusVo toResource(OrderStatusTable orderStatusTable) {
        OrderStatusVo orderStatusVo = new OrderStatusVo();
        BeanUtils.copyNotNullProperties(orderStatusTable, orderStatusVo);
        return orderStatusVo;
    }

    @Override
    public OrderStatusTable toDomain(OrderStatusVo orderStatusVo, OrderStatusTable orderStatusTable) {
        BeanUtils.copyNotNullProperties(orderStatusVo, orderStatusTable, "id", "code");
        return orderStatusTable;
    }
}
