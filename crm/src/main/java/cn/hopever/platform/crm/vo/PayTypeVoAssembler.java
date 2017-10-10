package cn.hopever.platform.crm.vo;

import cn.hopever.platform.crm.domain.ClientTable;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class PayTypeVoAssembler implements GenericVoAssembler<PayTypeVo, ClientTable> {

    @Override
    public PayTypeVo toResource(ClientTable clientTable) {
        return null;
    }

    @Override
    public ClientTable toDomain(PayTypeVo payTypeVo, ClientTable clientTable) {
        return null;
    }
}
