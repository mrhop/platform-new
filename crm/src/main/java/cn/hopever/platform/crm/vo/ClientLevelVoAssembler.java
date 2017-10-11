package cn.hopever.platform.crm.vo;

import cn.hopever.platform.crm.domain.ClientLevelTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class ClientLevelVoAssembler implements GenericVoAssembler<ClientLevelVo, ClientLevelTable> {

    @Override
    public ClientLevelVo toResource(ClientLevelTable clientLevelTable) {
        ClientLevelVo clientLevelVo = new ClientLevelVo();
        BeanUtils.copyNotNullProperties(clientLevelTable,clientLevelVo);
        return clientLevelVo;
    }

    @Override
    public ClientLevelTable toDomain(ClientLevelVo clientLevelVo, ClientLevelTable clientLevelTable) {
        BeanUtils.copyNotNullProperties(clientLevelVo,clientLevelTable,"id");
        return clientLevelTable;
    }
}
