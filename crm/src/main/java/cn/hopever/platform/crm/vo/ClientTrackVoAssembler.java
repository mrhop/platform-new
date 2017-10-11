package cn.hopever.platform.crm.vo;

import cn.hopever.platform.crm.domain.ClientTrackTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class ClientTrackVoAssembler implements GenericVoAssembler<ClientTrackVo, ClientTrackTable> {

    @Override
    public ClientTrackVo toResource(ClientTrackTable clientTrackTable) {
        ClientTrackVo clientTrackVo = new ClientTrackVo();
        BeanUtils.copyNotNullProperties(clientTrackTable, clientTrackVo);
        clientTrackVo.setClientId(clientTrackTable.getClientTable().getId());
        clientTrackVo.setClientName(clientTrackTable.getClientTable().getName());
        clientTrackVo.setTrackUserId(clientTrackTable.getTrackUser().getId());
        clientTrackVo.setTrackUserName(clientTrackTable.getTrackUser().getAccount());
        clientTrackVo.setTrackDate(clientTrackTable.getTrackDate().getTime());
        return clientTrackVo;
    }

    @Override
    public ClientTrackTable toDomain(ClientTrackVo clientTrackVo, ClientTrackTable clientTrackTable) {
        BeanUtils.copyNotNullProperties(clientTrackVo, clientTrackTable, "id");
        if (clientTrackVo.getTrackDate() != null) {
            clientTrackTable.setTrackDate(new Date(clientTrackVo.getTrackDate()));
        }
        return clientTrackTable;
    }
}
