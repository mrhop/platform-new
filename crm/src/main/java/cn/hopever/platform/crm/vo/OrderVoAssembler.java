package cn.hopever.platform.crm.vo;

import cn.hopever.platform.crm.domain.OrderTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class OrderVoAssembler implements GenericVoAssembler<OrderVo, OrderTable> {

    @Override
    public OrderVo toResource(OrderTable orderTable) {
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyNotNullProperties(orderTable,orderVo);
        orderVo.setClientId(orderTable.getClientTable().getId());
        orderVo.setClientName(orderTable.getClientTable().getName());
        if(orderTable.getContractSignDate()!=null){
            orderVo.setContractSignDate(orderTable.getContractSignDate().getTime());
        }
        orderVo.setCountryId(orderTable.getCountryTable().getId());
        orderVo.setCountryName(orderTable.getCountryTable().getName());
        if(orderTable.getCreatedDate()!=null){
            orderVo.setCreatedDate(orderTable.getCreatedDate().getTime());
        }
        // order继续后续的处理
        return null;
    }

    @Override
    public OrderTable toDomain(OrderVo orderVo, OrderTable orderTable) {
        return null;
    }
}
