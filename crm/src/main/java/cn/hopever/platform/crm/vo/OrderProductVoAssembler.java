package cn.hopever.platform.crm.vo;

import cn.hopever.platform.crm.domain.OrderProductTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class OrderProductVoAssembler implements GenericVoAssembler<OrderProductVo, OrderProductTable> {

    @Override
    public OrderProductVo toResource(OrderProductTable orderProductTable) {
        OrderProductVo orderProductVo = new OrderProductVo();
        BeanUtils.copyNotNullProperties(orderProductTable, orderProductVo);
        orderProductVo.setOrderId(orderProductTable.getOrderTable().getId());
        orderProductVo.setOrderName(orderProductTable.getOrderTable().getCode());
        orderProductVo.setProductCode(orderProductTable.getProductTable().getCode());
        orderProductVo.setProductId(orderProductTable.getProductTable().getId());
        orderProductVo.setProductName(orderProductTable.getProductTable().getName());
        orderProductVo.setProductCostPrice(orderProductTable.getProductTable().getCostPrice());
        orderProductVo.setProductSalePrice(orderProductTable.getProductTable().getSalePrice());
        return orderProductVo;
    }

    @Override
    public OrderProductTable toDomain(OrderProductVo orderProductVo, OrderProductTable orderProductTable) {
        BeanUtils.copyNotNullProperties(orderProductVo, orderProductTable, "id");
        return orderProductTable;
    }
}
