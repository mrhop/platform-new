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
public class ModuleVoAssembler{

    private ModelMapper modelMapper;

    public ModuleVoAssembler() {
        modelMapper = new ModelMapper();
        PropertyMap<ModuleTable, ModuleVo> map = new PropertyMap<ModuleTable, ModuleVo>() {
            protected void configure() {
                skip().setAuthorities(null);
                skip().setParent(null);
                skip().setChildren(null);
                skip().setClient(null);
            }
        };
        modelMapper.addMappings(map);
    }

    public ModuleVo toResource(ModuleTable moduleTable) {
        ModuleVo resource = createResource(moduleTable);
        //关联其他资源
        //child采用异步获取的策略在列表中显示
        //不采用勾选，删除关系的策略维护模块关系，而是在list中通过删除子module本身来删除关系
        //同时需要添加一个hasChildren的方式，来添加异步获取的触发按钮，所以单个的情况不获取children，parent【再作考虑】
        if (moduleTable.getAuthorities() != null) {
            List<ModuleRoleVo> listMrr = new ArrayList<>();
            for (ModuleRoleTable mrt : moduleTable.getAuthorities()) {
                ModuleRoleVo ModuleRoleVo = new ModuleRoleVo();
                ModuleRoleVo.setId(mrt.getId());
                ModuleRoleVo.setAuthority(mrt.getAuthority());
                listMrr.add(ModuleRoleVo);
            }
            resource.setAuthorities(listMrr);
        }
        return resource;
    }

    public List<ModuleVo> toResourcesCustomized(Iterable<ModuleTable> moduleTables) {
        List<ModuleVo> returnList = new ArrayList<>();
        for (ModuleTable moduleTable : moduleTables) {
            ModuleVo resource = createResource(moduleTable);
            if (moduleTable.getChildren() != null) {
                ArrayList<ModuleVo> setMr = new ArrayList<>();
                for (ModuleTable mt : moduleTable.getChildren()) {
                    ModuleVo ModuleVo = new ModuleVo();
                    ModuleVo.setId(mt.getId());
                    ModuleVo.setModuleName(mt.getModuleName());
                    ModuleVo.setAvailable(mt.isAvailable());
                    ModuleVo.setModuleUrl(mt.getModuleUrl());
                    ModuleVo.setIconClass(mt.getIconClass());
                    ModuleVo.setActivated(mt.isActivated());
                    setMr.add(ModuleVo);
                }
                resource.setChildren(setMr);
            }
            returnList.add(resource);
        }
        return returnList;
    }

    private ModuleVo createResource(ModuleTable moduleTable) {
        ModuleVo ModuleVo = null;
        if (moduleTable != null) {
            ModuleVo = modelMapper.map(moduleTable, ModuleVo.class);
            if (moduleTable.getParent() != null) {
                ModuleTable mt = moduleTable.getParent();
                ModuleVo ModuleVoParent = new ModuleVo();
                ModuleVoParent.setId(mt.getId());
                ModuleVoParent.setModuleName(mt.getModuleName());
                ModuleVoParent.setAvailable(mt.isAvailable());
                ModuleVoParent.setModuleUrl(mt.getModuleUrl());
                ModuleVoParent.setIconClass(mt.getIconClass());
                ModuleVoParent.setActivated(mt.isActivated());
                ModuleVo.setParent(ModuleVoParent);
            }
            if (moduleTable.getClient() != null) {
                ClientTable ct = moduleTable.getClient();
                ClientVo ClientVo = new ClientVo();
                ClientVo.setId(ct.getId());
                ClientVo.setClientId(ct.getClientId());
                ModuleVo.setClient(ClientVo);
            }
        }
        return ModuleVo;
    }
}
