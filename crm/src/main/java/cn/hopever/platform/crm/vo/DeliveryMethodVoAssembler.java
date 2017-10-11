package cn.hopever.platform.crm.vo;

import cn.hopever.platform.crm.domain.DeliveryMethodTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class DeliveryMethodVoAssembler implements GenericVoAssembler<DeliveryMethodVo, DeliveryMethodTable> {


    @Override
    public DeliveryMethodVo toResource(DeliveryMethodTable deliveryMethodTable) {
        DeliveryMethodVo deliveryMethodVo = new DeliveryMethodVo();
        BeanUtils.copyNotNullProperties(deliveryMethodTable, deliveryMethodVo);
        return deliveryMethodVo;
    }

    @Override
    public DeliveryMethodTable toDomain(DeliveryMethodVo deliveryMethodVo, DeliveryMethodTable deliveryMethodTable) {
        BeanUtils.copyNotNullProperties(deliveryMethodVo, deliveryMethodTable, "id");
        return deliveryMethodTable;
    }
}
