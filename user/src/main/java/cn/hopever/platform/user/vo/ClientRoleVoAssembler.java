package cn.hopever.platform.user.vo;

import cn.hopever.platform.user.domain.ClientRoleTable;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class ClientRoleVoAssembler{

    private ModelMapper modelMapper;


    public ClientRoleVoAssembler() {
        modelMapper = new ModelMapper();
        PropertyMap<ClientRoleTable, ClientRoleVo> map = new PropertyMap<ClientRoleTable, ClientRoleVo>() {
            protected void configure() {
                skip().setClients(null);
            }
        };
        modelMapper.addMappings(map);
    }

    public ClientRoleVo toResource(ClientRoleTable clientRoleTable) {
        ClientRoleVo resource = createResource(clientRoleTable);
        return resource;
    }

    private ClientRoleVo createResource(ClientRoleTable clientRoleTable) {
        ClientRoleVo ClientRoleVo = null;
        if (clientRoleTable != null) {
            ClientRoleVo = modelMapper.map(clientRoleTable, ClientRoleVo.class);
        }
        return ClientRoleVo;
    }
}
