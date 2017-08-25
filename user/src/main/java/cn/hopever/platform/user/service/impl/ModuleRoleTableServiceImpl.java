package cn.hopever.platform.user.service.impl;

import cn.hopever.platform.user.domain.ClientTable;
import cn.hopever.platform.user.domain.ModuleRoleTable;
import cn.hopever.platform.user.domain.ModuleTable;
import cn.hopever.platform.user.domain.UserTable;
import cn.hopever.platform.user.repository.*;
import cn.hopever.platform.user.service.ModuleRoleTableService;
import cn.hopever.platform.user.vo.ModuleRoleVo;
import cn.hopever.platform.user.vo.ModuleRoleVoAssembler;
import cn.hopever.platform.utils.web.SelectOption;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.TreeOption;
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
    private ModuleTableRepository moduleTableRepository;

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
        if (body.getFilters() != null && body.getFilters().containsKey("clientId")) {
            body.getFilters().put("client", clientTableRepository.findOne(Long.valueOf(body.getFilters().get("clientId").toString())));
            body.getFilters().remove("clientId");
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
        if (moduleRoleVo.getClientId() != null) {
            moduleRoleTable.setClient(clientTableRepository.findOne(moduleRoleVo.getClientId()));
        }

        if (moduleRoleVo.getModuleIds() != null) {
            List<ModuleTable> listModuleTable = new ArrayList<>();
            for (Long moduleId : moduleRoleVo.getModuleIds()) {
                ModuleTable moduleTable = moduleTableRepository.findOne(moduleId);
                listModuleTable.add(moduleTable);
                if (moduleTable.getAuthorities() != null) {
                    if (!moduleTable.getAuthorities().contains(moduleRoleTable)) {
                        moduleTable.getAuthorities().add(moduleRoleTable);
                    }
                } else {
                    List<ModuleRoleTable> list = new ArrayList<>();
                    list.add(moduleRoleTable);
                    moduleTable.setAuthorities(list);
                }
            }
            if (moduleRoleTable.getModules() == null) {
                moduleRoleTable.setModules(listModuleTable);
            } else {
                for (ModuleTable moduleTable : moduleRoleTable.getModules()) {
                    if (!listModuleTable.contains(moduleTable)) {
                        moduleTable.getAuthorities().remove(moduleRoleTable);
                    }
                }
                moduleRoleTable.getModules().clear();
                moduleRoleTable.getModules().addAll(listModuleTable);
            }
        }
        moduleRoleTableRepository.save(moduleRoleTable);
        return VueResults.generateSuccess("更新成功", "更新模块角色成功");
    }

    @Override
    public VueResults.Result save(ModuleRoleVo moduleRoleVo) {
        ModuleRoleTable moduleRoleTable = new ModuleRoleTable();
        moduleRoleTable = moduleRoleVoAssembler.toDomain(moduleRoleVo, moduleRoleTable);
        moduleRoleTable.setAuthority(moduleRoleVo.getAuthority());
        if (moduleRoleTableRepository.findOneByAuthority(moduleRoleVo.getAuthority()) != null) {
            return VueResults.generateError("保存失败", "模块角色ID已存在");
        }
        if (moduleRoleVo.getClientId() != null) {
            moduleRoleTable.setClient(clientTableRepository.findOne(moduleRoleVo.getClientId()));
        }
        if (moduleRoleVo.getModuleIds() != null) {
            List<ModuleTable> listModuleTable = new ArrayList<>();
            for (Long moduleId : moduleRoleVo.getModuleIds()) {
                ModuleTable moduleTable = moduleTableRepository.findOne(moduleId);
                listModuleTable.add(moduleTable);
                if (moduleTable.getAuthorities() != null) {
                    moduleTable.getAuthorities().add(moduleRoleTable);
                } else {
                    List<ModuleRoleTable> list = new ArrayList<>();
                    list.add(moduleRoleTable);
                    moduleTable.setAuthorities(list);
                }
            }
            moduleRoleTable.setModules(listModuleTable);
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

    @Override
    public List<SelectOption> getClientsOptions() {
        List<SelectOption> listReturn = new ArrayList<>();
        Iterable<ClientTable> list = clientTableRepository.findByClientIdNot("user_admin_client");
        for (ClientTable clientTable : list) {
            SelectOption selectOption = new SelectOption(clientTable.getClientName(), clientTable.getId());
            listReturn.add(selectOption);
        }
        return listReturn;
    }

    @Override
    public List<TreeOption> getParentsOptions(Long clientId, Long moduleRoleId) {
        ClientTable clientTable = null;
        if (clientId != null) {
            clientTable = clientTableRepository.findOne(clientId);
        } else {
            clientTable = moduleRoleTableRepository.findOne(moduleRoleId).getClient();
        }
        if (clientTable != null) {
            List<ModuleTable> list = moduleTableRepository.findByClientAndParentIsNullOrderByModuleOrderAsc(clientTable);
            List<TreeOption> listReturn = new ArrayList<>();
            if (list != null && list.size() > 0) {
                for (ModuleTable moduleTable : list) {
                    TreeOption treeOption = recursiveParentsOptions(moduleTable);
                    if (treeOption != null) {
                        listReturn.add(treeOption);
                    }
                }
            }
            return listReturn;
        }
        return null;

    }

    private TreeOption recursiveParentsOptions(ModuleTable moduleTable) {
        TreeOption treeOption = new TreeOption(moduleTable.getId(), moduleTable.getModuleName());
        treeOption.setEmitClick(true);
        treeOption.setIconClass(moduleTable.getIconClass());
        if (moduleTable.getChildren() != null && moduleTable.getChildren().size() > 0) {
            List<TreeOption> list = new ArrayList<>();
            for (ModuleTable moduleTable1 : moduleTable.getChildren()) {
                TreeOption treeOptionTemp = recursiveParentsOptions(moduleTable1);
                if (treeOptionTemp != null) {
                    list.add(treeOptionTemp);
                }
            }
            treeOption.setChildren(list);
        }
        return treeOption;
    }
}
