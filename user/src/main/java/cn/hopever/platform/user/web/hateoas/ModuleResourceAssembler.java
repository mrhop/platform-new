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
public class ModuleResourceAssembler extends ResourceAssemblerSupport<ModuleTable, ModuleResource> {

    private ModelMapper modelMapper;

    @Autowired
    EntityLinks entityLinks;

    public ModuleResourceAssembler() {
        super(ModuleResourceController.class, ModuleResource.class);
        modelMapper = new ModelMapper();
        PropertyMap<ModuleTable, ModuleResource> map = new PropertyMap<ModuleTable, ModuleResource>() {
            protected void configure() {
                skip().setAuthorities(null);
                skip().setParent(null);
                skip().setChildren(null);
                skip().setClient(null);
            }
        };
        modelMapper.addMappings(map);
    }

    @Override
    public ModuleResource toResource(ModuleTable moduleTable) {
        ModuleResource resource = createResource(moduleTable);
        //关联其他资源
        //child采用异步获取的策略在列表中显示
        //不采用勾选，删除关系的策略维护模块关系，而是在list中通过删除子module本身来删除关系
        //同时需要添加一个hasChildren的方式，来添加异步获取的触发按钮，所以单个的情况不获取children，parent【再作考虑】
        if (moduleTable.getAuthorities() != null) {
            List<ModuleRoleResource> listMrr = new ArrayList<>();
            for (ModuleRoleTable mrt : moduleTable.getAuthorities()) {
                ModuleRoleResource moduleRoleResource = new ModuleRoleResource();
                moduleRoleResource.setInternalId(mrt.getId());
                moduleRoleResource.setAuthority(mrt.getAuthority());
                listMrr.add(moduleRoleResource);
            }
            resource.setAuthorities(listMrr);
        }
        return resource;
    }

    public List<ModuleResource> toResourcesCustomized(Iterable<ModuleTable> moduleTables) {
        List<ModuleResource> returnList = new ArrayList<>();
        for (ModuleTable moduleTable : moduleTables) {
            ModuleResource resource = createResource(moduleTable);
            if (moduleTable.getChildren() != null) {
                ArrayList<ModuleResource> setMr = new ArrayList<>();
                for (ModuleTable mt : moduleTable.getChildren()) {
                    ModuleResource moduleResource = new ModuleResource();
                    moduleResource.setInternalId(mt.getId());
                    moduleResource.setModuleName(mt.getModuleName());
                    moduleResource.setAvailable(mt.isAvailable());
                    moduleResource.setModuleUrl(mt.getModuleUrl());
                    moduleResource.setIconClass(mt.getIconClass());
                    moduleResource.setActivated(mt.isActivated());
                    setMr.add(moduleResource);
                }
                resource.setChildren(setMr);
            }
            returnList.add(resource);
        }
        return returnList;
    }

    private ModuleResource createResource(ModuleTable moduleTable) {
        ModuleResource moduleResource = null;
        if (moduleTable != null) {
            moduleResource = modelMapper.map(moduleTable, ModuleResource.class);
            moduleResource.setInternalId(moduleTable.getId());
            if (moduleTable.getParent() != null) {
                ModuleTable mt = moduleTable.getParent();
                ModuleResource moduleResourceParent = new ModuleResource();
                moduleResourceParent.setInternalId(mt.getId());
                moduleResourceParent.setModuleName(mt.getModuleName());
                moduleResourceParent.setAvailable(mt.isAvailable());
                moduleResourceParent.setModuleUrl(mt.getModuleUrl());
                moduleResourceParent.setIconClass(mt.getIconClass());
                moduleResourceParent.setActivated(mt.isActivated());
                moduleResource.setParent(moduleResourceParent);
            }
            if (moduleTable.getClient() != null) {
                ClientTable ct = moduleTable.getClient();
                ClientResource clientResource = new ClientResource();
                clientResource.setInternalId(ct.getId());
                clientResource.setClientId(ct.getClientId());
                moduleResource.setClient(clientResource);
            }
            moduleResource.add(entityLinks.linkFor(ModuleResource.class).slash(moduleTable.getId()).withSelfRel());
        }
        return moduleResource;
    }
}
