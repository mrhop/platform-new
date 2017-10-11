package cn.hopever.platform.crm.vo;

import cn.hopever.platform.crm.domain.ClientOriginTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class ClientOriginVoAssembler implements GenericVoAssembler<ClientOriginVo, ClientOriginTable> {

    @Override
    public ClientOriginVo toResource(ClientOriginTable clientOriginTable) {
        ClientOriginVo clientOriginVo = new ClientOriginVo();
        BeanUtils.copyNotNullProperties(clientOriginTable,clientOriginVo);
        return clientOriginVo;
    }

    @Override
    public ClientOriginTable toDomain(ClientOriginVo clientOriginVo, ClientOriginTable clientOriginTable) {
        BeanUtils.copyNotNullProperties(clientOriginVo,clientOriginTable,"id");
        return clientOriginTable;
    }
}
