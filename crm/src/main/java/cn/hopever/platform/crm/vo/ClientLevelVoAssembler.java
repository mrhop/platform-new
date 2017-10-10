package cn.hopever.platform.crm.vo;

import cn.hopever.platform.crm.domain.ClientLevelTable;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class ClientLevelVoAssembler implements GenericVoAssembler<ClientLevelVo, ClientLevelTable> {

    @Override
    public ClientLevelVo toResource(ClientLevelTable clientLevelTable) {
        return null;
    }

    @Override
    public ClientLevelTable toDomain(ClientLevelVo clientLevelVo, ClientLevelTable clientLevelTable) {
        return null;
    }
}
