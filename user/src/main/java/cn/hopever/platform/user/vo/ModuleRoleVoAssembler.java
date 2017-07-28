package cn.hopever.platform.user.vo;

import cn.hopever.platform.user.domain.ClientTable;
import cn.hopever.platform.user.domain.ModuleRoleTable;
import cn.hopever.platform.user.domain.ModuleTable;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class ModuleRoleVoAssembler{

    private ModelMapper modelMapper;


    public ModuleRoleVoAssembler() {
        modelMapper = new ModelMapper();
        PropertyMap<ModuleRoleTable, ModuleRoleVo> map = new PropertyMap<ModuleRoleTable, ModuleRoleVo>() {
            protected void configure() {
                skip().setModules(null);
                skip().setUsers(null);
                skip().setClient(null);
            }
        };
        modelMapper.addMappings(map);
    }

    public ModuleRoleVo toResource(ModuleRoleTable moduleRoleTable) {
        ModuleRoleVo resource = createResource(moduleRoleTable);
        if (moduleRoleTable.getModules() != null) {
            ArrayList<ModuleVo> setMr = new ArrayList<>();
            for (ModuleTable mt : moduleRoleTable.getModules()) {
                ModuleVo ModuleVo = new ModuleVo();
                ModuleVo.setId(mt.getId());
                ModuleVo.setModuleName(mt.getModuleName());
                setMr.add(ModuleVo);
            }
            resource.setModules(setMr);
        }
        return resource;
    }

    public List<ModuleRoleVo> toResourcesCustomized(Iterable<ModuleRoleTable> moduleRoleTables) {
        List<ModuleRoleVo> returnList = new ArrayList<>();
        for (ModuleRoleTable mrt : moduleRoleTables) {
            returnList.add(this.createResource(mrt));
        }
        return returnList;
    }

    private ModuleRoleVo createResource(ModuleRoleTable moduleRoleTable) {
        ModuleRoleVo ModuleRoleVo = null;
        if (moduleRoleTable != null) {
            ModuleRoleVo = modelMapper.map(moduleRoleTable,ModuleRoleVo.class);
        }
        if (moduleRoleTable.getClient() != null) {
            ClientTable ct = moduleRoleTable.getClient();
            ClientVo ClientVo = new ClientVo();
            ClientVo.setId(ct.getId());
            ClientVo.setClientId(ct.getClientId());
            ClientVo.setClientName(ct.getClientName());
            ModuleRoleVo.setClient(ClientVo);
        }
        return ModuleRoleVo;
    }
}
