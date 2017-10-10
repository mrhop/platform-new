package cn.hopever.platform.crm.vo;

import cn.hopever.platform.crm.domain.ClientLevelTable;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class ClientTrackVoAssembler implements GenericVoAssembler<ClientTrackVo, ClientLevelTable> {

    @Override
    public ClientTrackVo toResource(ClientLevelTable clientLevelTable) {
        return null;
    }

    @Override
    public ClientLevelTable toDomain(ClientTrackVo clientTrackVo, ClientLevelTable clientLevelTable) {
        return null;
    }
}
