package cn.hopever.platform.crm.vo;

import cn.hopever.platform.crm.domain.ClientTable;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class OrderDiscountVoAssembler implements GenericVoAssembler<OrderDiscountVo, ClientTable> {

    @Override
    public OrderDiscountVo toResource(ClientTable clientTable) {
        return null;
    }

    @Override
    public ClientTable toDomain(OrderDiscountVo orderDiscountVo, ClientTable clientTable) {
        return null;
    }
}
