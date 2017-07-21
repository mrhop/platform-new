package cn.hopever.platform.user.web.hateoas;

import cn.hopever.platform.user.domain.ClientTable;
import cn.hopever.platform.user.domain.ModuleRoleTable;
import cn.hopever.platform.user.domain.ModuleTable;
import cn.hopever.platform.user.resources.ClientResource;
import cn.hopever.platform.user.resources.ModuleResource;
import cn.hopever.platform.user.resources.ModuleRoleResource;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class ModuleRoleResourceAssembler extends ResourceAssemblerSupport<ModuleRoleTable, ModuleRoleResource> {

    private ModelMapper modelMapper;

    @Autowired
    EntityLinks entityLinks;

    public ModuleRoleResourceAssembler() {
        super(ModuleRoleResourceController.class, ModuleRoleResource.class);
        modelMapper = new ModelMapper();
        PropertyMap<ModuleRoleTable, ModuleRoleResource> map = new PropertyMap<ModuleRoleTable, ModuleRoleResource>() {
            protected void configure() {
                skip().setModules(null);
                skip().setUsers(null);
                skip().setClient(null);
            }
        };
        modelMapper.addMappings(map);
    }

    @Override
    public ModuleRoleResource toResource(ModuleRoleTable moduleRoleTable) {
        ModuleRoleResource resource = createResource(moduleRoleTable);
        if (moduleRoleTable.getModules() != null) {
            ArrayList<ModuleResource> setMr = new ArrayList<>();
            for (ModuleTable mt : moduleRoleTable.getModules()) {
                ModuleResource moduleResource = new ModuleResource();
                moduleResource.setInternalId(mt.getId());
                moduleResource.setModuleName(mt.getModuleName());
                setMr.add(moduleResource);
            }
            resource.setModules(setMr);
        }
        return resource;
    }

    public List<ModuleRoleResource> toResourcesCustomized(Iterable<ModuleRoleTable> moduleRoleTables) {
        List<ModuleRoleResource> returnList = new ArrayList<>();
        for (ModuleRoleTable mrt : moduleRoleTables) {
            returnList.add(this.createResource(mrt));
        }
        return returnList;
    }

    private ModuleRoleResource createResource(ModuleRoleTable moduleRoleTable) {
        ModuleRoleResource moduleRoleResource = null;
        if (moduleRoleTable != null) {
            moduleRoleResource = modelMapper.map(moduleRoleTable,ModuleRoleResource.class);
            moduleRoleResource.setInternalId(moduleRoleTable.getId());
            moduleRoleResource.add(entityLinks.linkFor(ModuleRoleResource.class).slash(moduleRoleTable.getId()).withSelfRel());
        }
        if (moduleRoleTable.getClient() != null) {
            ClientTable ct = moduleRoleTable.getClient();
            ClientResource clientResource = new ClientResource();
            clientResource.setInternalId(ct.getId());
            clientResource.setClientId(ct.getClientId());
            clientResource.setClientName(ct.getClientName());
            moduleRoleResource.setClient(clientResource);
        }
        return moduleRoleResource;
    }
}
