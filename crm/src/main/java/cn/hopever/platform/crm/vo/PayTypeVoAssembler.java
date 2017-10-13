package cn.hopever.platform.crm.vo;

import cn.hopever.platform.crm.domain.PayTypeTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class PayTypeVoAssembler implements GenericVoAssembler<PayTypeVo, PayTypeTable> {

    @Override
    public PayTypeVo toResource(PayTypeTable payTypeTable) {
        PayTypeVo payTypeVo = new PayTypeVo();
        BeanUtils.copyNotNullProperties(payTypeTable, payTypeVo);
        return payTypeVo;
    }

    @Override
    public PayTypeTable toDomain(PayTypeVo payTypeVo, PayTypeTable payTypeTable) {
        BeanUtils.copyNotNullProperties(payTypeVo, payTypeTable,"id");
        return payTypeTable;
    }
}
