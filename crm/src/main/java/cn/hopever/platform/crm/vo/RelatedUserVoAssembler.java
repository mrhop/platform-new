package cn.hopever.platform.crm.vo;

import cn.hopever.platform.crm.domain.ClientTable;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class RelatedUserVoAssembler implements GenericVoAssembler<RelatedUserVo, ClientTable> {

    @Override
    public RelatedUserVo toResource(ClientTable clientTable) {
        return null;
    }

    @Override
    public ClientTable toDomain(RelatedUserVo relatedUserVo, ClientTable clientTable) {
        return null;
    }
}
