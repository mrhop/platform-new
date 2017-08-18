package cn.hopever.platform.user.service.impl;

import cn.hopever.platform.user.domain.ClientTable;
import cn.hopever.platform.user.domain.ModuleRoleTable;
import cn.hopever.platform.user.domain.ModuleTable;
import cn.hopever.platform.user.domain.UserTable;
import cn.hopever.platform.user.repository.ClientTableRepository;
import cn.hopever.platform.user.repository.CustomModuleRoleTableRepository;
import cn.hopever.platform.user.repository.ModuleRoleTableRepository;
import cn.hopever.platform.user.repository.UserTableRepository;
import cn.hopever.platform.user.service.ModuleRoleTableService;
import cn.hopever.platform.user.vo.ModuleRoleVo;
import cn.hopever.platform.user.vo.ModuleRoleVoAssembler;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Donghui Huo on 2016/10/17.
 */
@Service("moduleRoleTableService")
@Transactional
public class ModuleRoleTableServiceImpl implements ModuleRoleTableService {
    Logger logger = LoggerFactory.getLogger(ModuleRoleTableServiceImpl.class);

    @Autowired
    private ModuleRoleTableRepository moduleRoleTableRepository;
    @Autowired
    private CustomModuleRoleTableRepository customModuleRoleTableRepository;

    @Autowired
    private ModuleRoleVoAssembler moduleRoleVoAssembler;
    @Autowired
    private ClientTableRepository clientTableRepository;

    @Autowired
    private UserTableRepository userTableRepository;

    @Override
    public List<ModuleRoleTable> getByClients(List<Object> clientIds) {

        List<ModuleRoleTable> list = new ArrayList<>();
        for (Object clientId : clientIds) {
            ClientTable clientTable = clientTableRepository.findOne(Long.parseLong(clientId.toString()));
            if (clientTable != null) {
                list.addAll(clientTable.getModuleRoles());
            }
        }
        return list;
    }

    @Override
    public List<ModuleRoleTable> getByIds(List<Long> ids) {
        List<ModuleRoleTable> list = new ArrayList<>();
        for (Long id : ids) {
            ModuleRoleTable moduleRoleTable = moduleRoleTableRepository.findOne(id);
            list.add(moduleRoleTable);
        }
        return list;
    }

    @Override
    public List<ModuleRoleTable> getByUserId(Long userId) {
        UserTable user = userTableRepository.findOne(userId);
        return user.getModulesAuthorities();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ModuleRoleVo> getList(TableParameters body) {
        PageRequest pageRequest;
        if (body.getSorts() == null || body.getSorts().isEmpty()) {
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.ASC, "id");
        } else {
            String key = body.getSorts().keySet().iterator().next();
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.fromString(body.getSorts().get(key)), key);
        }
        Page<ModuleRoleTable> page = customModuleRoleTableRepository.findByFilters(body.getFilters(), pageRequest);
        List<ModuleRoleVo> list = new ArrayList<>();
        for (ModuleRoleTable moduleRoleTable : page) {
            list.add(moduleRoleVoAssembler.toResource(moduleRoleTable));
        }
        return new PageImpl<ModuleRoleVo>(list, pageRequest, page.getTotalElements());
    }


    @Override
    public ModuleRoleVo getById(Long id) {
        return moduleRoleVoAssembler.toResource(moduleRoleTableRepository.findOne(id));
    }

    @Override
    public VueResults.Result update(ModuleRoleVo moduleRoleVo) {
        ModuleRoleTable moduleRoleTable = moduleRoleTableRepository.findOne(moduleRoleVo.getId());
        moduleRoleTable = moduleRoleVoAssembler.toDomain(moduleRoleVo, moduleRoleTable);
        moduleRoleTableRepository.save(moduleRoleTable);
        return VueResults.generateSuccess("更新成功", "更新模块角色成功");
    }

    @Override
    public VueResults.Result save(ModuleRoleVo moduleRoleVo) {
        ModuleRoleTable moduleRoleTable = new ModuleRoleTable();
        moduleRoleTable = moduleRoleVoAssembler.toDomain(moduleRoleVo, moduleRoleTable);
        if (moduleRoleVo.getClientId() != null) {
            moduleRoleTable.setClient(clientTableRepository.findOne(moduleRoleVo.getId()));
        }
        moduleRoleTableRepository.save(moduleRoleTable);
        return VueResults.generateSuccess("保存成功", "保存模块角色成功");
    }

    @Override
    public ModuleRoleTable getByAuthority(String authority) {
        return moduleRoleTableRepository.findOneByAuthority(authority);
    }

    @Override
    public ModuleRoleTable save(ModuleRoleTable moduleRoleTable) {
        return moduleRoleTableRepository.save(moduleRoleTable);
    }

    @Override
    public void deleteById(Long id) {
        ModuleRoleTable mrt = moduleRoleTableRepository.findOne(id);
        if (mrt.getUsers() != null) {
            for (UserTable ut : mrt.getUsers()) {
                ut.getModulesAuthorities().remove(mrt);
            }
        }
        if (mrt.getModules() != null) {
            for (ModuleTable mt : mrt.getModules()) {
                mt.getAuthorities().remove(mrt);
            }
        }
        moduleRoleTableRepository.delete(mrt);
    }
}
