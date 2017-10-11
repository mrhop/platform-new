package cn.hopever.platform.crm.vo;

import cn.hopever.platform.crm.domain.ClientTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class ClientVoAssembler implements GenericVoAssembler<ClientVo, ClientTable> {

    @Override
    public ClientVo toResource(ClientTable clientTable) {
        ClientVo clientVo = new ClientVo();
        BeanUtils.copyNotNullProperties(clientTable,clientVo);
        clientVo.setClientLevelId(clientTable.getClientLevelTable().getId());
        clientVo.setClientLevelName(clientTable.getClientLevelTable().getName());
        clientVo.setClientOriginId(clientTable.getClientOriginTable().getId());
        clientVo.setClientOriginName(clientTable.getClientOriginTable().getName());
        clientVo.setCountryId(clientTable.getCountryTable().getId());
        clientVo.setCountryName(clientTable.getCountryTable().getName());
        clientVo.setCreatedDate(clientTable.getCreatedDate().getTime());
        clientVo.setCreatedUserId(clientTable.getCreatedUser().getId());
        clientVo.setCreatedUserName(clientTable.getCreatedUser().getAccount());
        return clientVo;
    }

    @Override
    public ClientTable toDomain(ClientVo clientVo, ClientTable clientTable) {
        BeanUtils.copyNotNullProperties(clientVo,clientTable,"id");
        return clientTable;
    }
}
