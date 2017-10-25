package cn.hopever.platform.user.vo;

import cn.hopever.platform.user.domain.ModuleRoleTable;
import cn.hopever.platform.user.domain.ModuleTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class ModuleVoAssembler {

    public ModuleVo toResource(ModuleTable moduleTable) {
        ModuleVo resource = new ModuleVo();
        BeanUtils.copyNotNullProperties(moduleTable, resource);
        if (moduleTable.getClient() != null) {
            resource.setClientId(moduleTable.getClient().getId());
            resource.setClientName(moduleTable.getClient().getClientName());
        }
        if (moduleTable.getParent() != null) {
            resource.setParentId(moduleTable.getParent().getId());
            resource.setParentName(moduleTable.getParent().getModuleName());
        }
        if (moduleTable.getAuthorities() != null && moduleTable.getAuthorities().size() > 0) {
            // List<Long> list = new ArrayList<>();
            List<String> listStr = new ArrayList<>();
            for (ModuleRoleTable moduleRoleTable : moduleTable.getAuthorities()) {
                // list.add(moduleRoleTable.getId());
                listStr.add(moduleRoleTable.getName());
            }
            //resource.setAuthorities(list);
            resource.setAuthoritiesStr(listStr.toString());
        }
        if(moduleTable.getBeforeModule()!=null){
            resource.setBeforeId(moduleTable.getBeforeModule().getId());
            resource.setBeforeName(moduleTable.getBeforeModule().getModuleName());
        }
        if (moduleTable.getParent() != null) {
            resource.setParentId(moduleTable.getParent().getId());
            resource.setParentName(moduleTable.getParent().getModuleName());
        }
        return resource;
    }

    public ModuleTable toDomain(ModuleVo moduleVo, ModuleTable moduleTable) {
        BeanUtils.copyNotNullProperties(moduleVo, moduleTable, "moduleId");
        // 需要进行计算的时候，放在service里面处理
        return moduleTable;
    }
}
