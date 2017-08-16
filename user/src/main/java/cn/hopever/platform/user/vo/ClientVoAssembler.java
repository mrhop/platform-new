package cn.hopever.platform.user.vo;

import cn.hopever.platform.user.domain.ClientRoleTable;
import cn.hopever.platform.user.domain.ClientTable;
import cn.hopever.platform.user.domain.ModuleRoleTable;
import cn.hopever.platform.user.domain.ModuleTable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class ClientVoAssembler {


    public ClientVoAssembler() {
    }

    public ClientVo toResource(ClientTable clientTable) {
        ClientVo resource = createResource(clientTable);
        //关联其他资源
        if (clientTable.getAuthoritiesBasic() != null) {
            ArrayList<ClientRoleVo> sCrr = new ArrayList<>();
            for (ClientRoleTable crt : clientTable.getAuthoritiesBasic()) {
                ClientRoleVo crr = new ClientRoleVo();
                crr.setId(crt.getId());
                crr.setAuthority(crt.getAuthority());
                crr.setName(crt.getName());
                sCrr.add(crr);
            }
        }
        if (clientTable.getModules() != null) {
            ArrayList<ModuleVo> sMr = new ArrayList<>();
            for (ModuleTable mt : clientTable.getModules()) {
                ModuleVo mr = new ModuleVo();
                mr.setId(mt.getId());
                mr.setModuleName(mt.getModuleName());
                sMr.add(mr);
            }
        }
        if (clientTable.getModuleRoles() != null) {
            ArrayList<ModuleRoleVo> sMr = new ArrayList<>();
            for (ModuleRoleTable mt : clientTable.getModuleRoles()) {
                ModuleRoleVo mr = new ModuleRoleVo();
                mr.setId(mt.getId());
                mr.setName(mt.getName());
                sMr.add(mr);
            }

        }
        return resource;
    }

    public List<ClientVo> toResourcesCustomized(Iterable<ClientTable> clientTables) {
        List<ClientVo> returnList = new ArrayList<>();
        for (ClientTable ct : clientTables) {
            returnList.add(this.createResource(ct));
        }
        return returnList;
    }

    private ClientVo createResource(ClientTable clientTable) {
        ClientVo clientVo = null;
        if (clientTable != null) {
            clientVo.setClientSecret(null);
        }
        return clientVo;
    }
}
