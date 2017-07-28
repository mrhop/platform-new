package cn.hopever.platform.user.vo;

import cn.hopever.platform.user.domain.ClientTable;
import cn.hopever.platform.user.domain.ModuleRoleTable;
import cn.hopever.platform.user.domain.RoleTable;
import cn.hopever.platform.user.domain.UserTable;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class UserVoAssembler {

    private ModelMapper modelMapper;

    public UserVoAssembler() {
        modelMapper = new ModelMapper();
        PropertyMap<UserTable, UserVo> map = new PropertyMap<UserTable, UserVo>() {
            protected void configure() {
                skip().setPassword(null);
                skip().setModulesAuthorities(null);
                skip().setAuthorities(null);
                skip().setClients(null);
            }
        };
        modelMapper.addMappings(map);
    }

    public UserVo toResource(UserTable userTable) {

        UserVo resource = createResource(userTable);
        // … do further mapping
        //关联其他资源
        if (userTable.getClients() != null) {
            ArrayList<ClientVo> setCr = new ArrayList<>();
            for (ClientTable ct : userTable.getClients()) {
                ClientVo clientVo = new ClientVo();
                clientVo.setId(ct.getId());
                clientVo.setClientId(ct.getClientId());
                clientVo.setClientName(ct.getClientName());
                setCr.add(clientVo);
            }
            resource.setClients(setCr);
        }

        if (userTable.getAuthorities() != null) {
            ArrayList<RoleVo> setRr = new ArrayList<>();
            for (RoleTable rt : userTable.getAuthorities()) {
                RoleVo roleVo = new RoleVo();
                roleVo.setId(rt.getId());
                roleVo.setAuthority(rt.getAuthority());
                roleVo.setName(rt.getName());
                setRr.add(roleVo);
            }
            resource.setAuthorities(setRr);
        }

        if (userTable.getModulesAuthorities() != null) {
            ArrayList<ModuleRoleVo> setMrr = new ArrayList<>();
            for (ModuleRoleTable mrt : userTable.getModulesAuthorities()) {
                ModuleRoleVo moduleRoleVo = new ModuleRoleVo();
                moduleRoleVo.setId(mrt.getId());
                moduleRoleVo.setAuthority(mrt.getAuthority());
                moduleRoleVo.setName(mrt.getName());
                setMrr.add(moduleRoleVo);
            }
            resource.setModulesAuthorities(setMrr);
        }
        return resource;
    }


    public List<UserVo> toResourcesCustomized(Iterable<UserTable> userTables) {
        List<UserVo> returnList = new ArrayList<>();
        for (UserTable ut : userTables) {
            returnList.add(this.createResource(ut));
        }
        return returnList;
    }

    private UserVo createResource(UserTable userTable) {
        UserVo userVo = null;
        if (userTable != null) {
            userVo = modelMapper.map(userTable, UserVo.class);
        }
        return userVo;
    }

}
