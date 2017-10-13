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
        BeanUtils.copyNotNullProperties(orderTable, orderVo);
        orderVo.setClientId(orderTable.getClientTable().getId());
        orderVo.setClientName(orderTable.getClientTable().getName());
        if (orderTable.getContractSignDate() != null) {
            orderVo.setContractSignDate(orderTable.getContractSignDate().getTime());
        }
        orderVo.setCountryId(orderTable.getCountryTable().getId());
        orderVo.setCountryName(orderTable.getCountryTable().getName());
        if (orderTable.getCreatedDate() != null) {
            orderVo.setCreatedDate(orderTable.getCreatedDate().getTime());
        }
        orderVo.setCreatedUserId(orderTable.getCreatedUser().getId());
        orderVo.setCreatedUserName(orderTable.getCreatedUser().getAccount());
        if (orderTable.getDeliveryDate() != null) {
            orderVo.setDeliveryDate(orderTable.getDeliveryDate().getTime());
        }
        if (orderTable.getOrderStatusTable() != null) {
            orderVo.setOrderStatusId(orderTable.getOrderStatusTable().getId());
            orderVo.setOrderStatusName(orderTable.getOrderStatusTable().getName());
        }
        if (orderTable.getDeliveryMethodTable() != null) {
            orderVo.setDeliveryMethodId(orderTable.getDeliveryMethodTable().getId());
            orderVo.setDeliveryMethodName(orderTable.getDeliveryMethodTable().getName());
        }
        if (orderTable.getFinishedDate() != null) {
            orderVo.setFinishedDate(orderTable.getFinishedDate().getTime());
        }
        if (orderTable.getPayTypeTable() != null) {
            orderVo.setPayTypeId(orderTable.getPayTypeTable().getId());
            orderVo.setPayTypeName(orderTable.getPayTypeTable().getName());
        }
        return orderVo;
    }

    @Override
    public OrderTable toDomain(OrderVo orderVo, OrderTable orderTable) {
        BeanUtils.copyNotNullProperties(orderVo, orderTable, "id", "code","salePrice","discountType","discount","tracingNumber","freight","preQuotation","costPrice");
        return orderTable;
    }
}
