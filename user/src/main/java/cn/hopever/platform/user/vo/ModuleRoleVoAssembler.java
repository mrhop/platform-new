package cn.hopever.platform.user.vo;

import cn.hopever.platform.user.domain.ModuleRoleTable;
import cn.hopever.platform.user.domain.ModuleTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class ModuleRoleVoAssembler {

    private ModelMapper modelMapper;


    public ModuleRoleVo toResource(ModuleRoleTable moduleRoleTable) {
        ModuleRoleVo resource = new ModuleRoleVo();
        BeanUtils.copyNotNullProperties(moduleRoleTable, resource);
        //关联其他资源
        if (moduleRoleTable.getClient() != null) {
            resource.setClientId(moduleRoleTable.getClient().getId());
            resource.setClientName(moduleRoleTable.getClient().getClientName());
        }
        //关联其他资源
        if (moduleRoleTable.getModules() != null) {
            List<Long> moduleIds = new ArrayList<>();
            for (ModuleTable moduleTable : moduleRoleTable.getModules()) {
                moduleIds.add(moduleTable.getId());
            }
            resource.setModuleIds(moduleIds);
        }
        return resource;
    }

    public ModuleRoleTable toDomain(ModuleRoleVo moduleRoleVo, ModuleRoleTable moduleRoleTable) {
        BeanUtils.copyNotNullProperties(moduleRoleVo, moduleRoleTable, "authority");
        return moduleRoleTable;
    }
}
