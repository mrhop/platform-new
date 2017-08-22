package cn.hopever.platform.user.service.impl;

import cn.hopever.platform.user.domain.ClientTable;
import cn.hopever.platform.user.domain.ModuleRoleTable;
import cn.hopever.platform.user.domain.ModuleTable;
import cn.hopever.platform.user.repository.*;
import cn.hopever.platform.user.service.ModuleTableService;
import cn.hopever.platform.user.vo.ModuleVo;
import cn.hopever.platform.user.vo.ModuleVoAssembler;
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
@Service("moduleTableService")
@Transactional
public class ModuleTableServiceImpl implements ModuleTableService {

    Logger logger = LoggerFactory.getLogger(ModuleTableServiceImpl.class);

    @Autowired
    private ModuleVoAssembler moduleVoAssembler;
    @Autowired
    private ModuleTableRepository moduleTableRepository;
    @Autowired
    private CustomModuleTableRepository customModuleTableRepository;

    @Autowired
    private ModuleRoleTableRepository moduleRoleTableRepository;

    @Autowired
    private ClientTableRepository clientTableRepository;

    @Autowired
    private UserTableRepository userTableRepository;


    @Override
    public Iterable<ModuleTable> getAll() {
        return this.moduleTableRepository.findAll();
    }


    @Override
    public Page<ModuleVo> getList(TableParameters body) {
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
        Long authorityId = null;
        if (body.getFilters() != null && body.getFilters().containsKey("authorityId")) {
            authorityId = Long.valueOf(body.getFilters().get("authorityId").toString());
        }
        if (body.getFilters() != null && body.getFilters().containsKey("parentId")) {
            body.getFilters().put("parent", moduleRoleTableRepository.findOne(Long.valueOf(body.getFilters().get("parentId").toString())));
            body.getFilters().remove("parentId");
        }
        Page<ModuleTable> page = customModuleTableRepository.findByFilters(body.getFilters(), pageRequest);
        List<ModuleVo> list = new ArrayList<>();
        for (ModuleTable moduleTable : page) {
            ModuleVo moduleVo = moduleVoAssembler.toResource(moduleTable);
            list.add(moduleVoAssembler.toResource(moduleTable));
        }
        return new PageImpl<ModuleVo>(list, pageRequest, page.getTotalElements());
    }

    @Override
    public ModuleVo getById(Long id) {
        ModuleTable moduleTable = this.moduleTableRepository.findOne(id);
        ModuleVo moduleVo = moduleVoAssembler.toResource(moduleTable);
        return moduleVo;
    }

    @Override
    public VueResults.Result update(ModuleVo moduleVo) {
        ModuleTable moduleTable = moduleTableRepository.findOne(moduleVo.getId());
        moduleTable = moduleVoAssembler.toDomain(moduleVo, moduleTable);
        ClientTable clientTable = null;
        ModuleTable moduleTableParent = null;
        ModuleTable moduleTableAfter = null;

        if (moduleVo.getClientId() != null) {
            clientTable = clientTableRepository.findOne(moduleVo.getClientId());
            moduleTable.setClient(clientTable);
        }
        if (moduleVo.getParentId() != null) {
            moduleTableParent = moduleTableRepository.findOne(moduleVo.getParentId());
            moduleTable.setParent(moduleTableParent);
        }
        if (moduleVo.getAuthorities() != null) {
            List<ModuleRoleTable> list = new ArrayList<>();
            for (Long id : moduleVo.getAuthorities()) {
                list.add(moduleRoleTableRepository.findOne(id));
            }
            moduleTable.setAuthorities(list);
        }
        if (moduleVo.getBeforeId() != null) {
            ModuleTable moduleTable1 = moduleTableRepository.findOne(moduleVo.getBeforeId());
            moduleTable.setBeforeModule(moduleTable1);
            moduleTableAfter = moduleTableRepository.findOneByBeforeModule(moduleTable1);
            if (moduleTableAfter != null) {
                moduleTableAfter.setBeforeModule(moduleTable);
            }
            moduleTable.setModuleOrder(moduleTable1.getModuleOrder());
            recursiveModuleOrder(moduleTable1);
        } else {
            ModuleTable moduleTable1 = moduleTableRepository.findTopByParentAndClientOrderByModuleOrderDesc(moduleTableParent, clientTable);
            if (moduleTable1 != null) {
                moduleTable.setBeforeModule(moduleTable1);
                moduleTable.setModuleOrder(moduleTable1.getModuleOrder() + 1);
            } else {
                moduleTable.setModuleOrder(0);
            }
        }
        moduleTableRepository.save(moduleTable);
        if (moduleTableAfter != null) {
            moduleTableRepository.save(moduleTableAfter);
        }
        return VueResults.generateSuccess("更新成功", "更新模块成功");
    }

    @Override
    public VueResults.Result save(ModuleVo moduleVo) {
        ModuleTable moduleTable = new ModuleTable();
        moduleTable = moduleVoAssembler.toDomain(moduleVo, moduleTable);
        ClientTable clientTable = null;
        ModuleTable moduleTableParent = null;
        ModuleTable moduleTableAfter = null;

        if (moduleVo.getClientId() != null) {
            clientTable = clientTableRepository.findOne(moduleVo.getClientId());
            moduleTable.setClient(clientTable);
        }
        if (moduleVo.getParentId() != null) {
            moduleTableParent = moduleTableRepository.findOne(moduleVo.getParentId());
            moduleTable.setParent(moduleTableParent);
        }
        if (moduleVo.getAuthorities() != null) {
            List<ModuleRoleTable> list = new ArrayList<>();
            for (Long id : moduleVo.getAuthorities()) {
                list.add(moduleRoleTableRepository.findOne(id));
            }
            moduleTable.setAuthorities(list);
        }
        if (moduleVo.getBeforeId() != null) {
            ModuleTable moduleTable1 = moduleTableRepository.findOne(moduleVo.getBeforeId());
            moduleTable.setBeforeModule(moduleTable1);
            moduleTableAfter = moduleTableRepository.findOneByBeforeModule(moduleTable1);
            if (moduleTableAfter != null) {
                moduleTableAfter.setBeforeModule(moduleTable);
            }
            moduleTable.setModuleOrder(moduleTable1.getModuleOrder());
            recursiveModuleOrder(moduleTable1);
        } else {
            ModuleTable moduleTable1 = moduleTableRepository.findTopByParentAndClientOrderByModuleOrderDesc(moduleTableParent, clientTable);
            if (moduleTable1 != null) {
                moduleTable.setBeforeModule(moduleTable1);
                moduleTable.setModuleOrder(moduleTable1.getModuleOrder() + 1);
            } else {
                moduleTable.setModuleOrder(0);
            }
        }
        moduleTableRepository.save(moduleTable);
        if (moduleTableAfter != null) {
            moduleTableRepository.save(moduleTableAfter);
        }
        return VueResults.generateSuccess("保存成功", "保存模块成功");
    }


    @Override
    public void deleteById(Long id) {
        //删除时，去除关联并删除子
        ModuleTable mt = moduleTableRepository.findOne(id);
        ModuleTable moduleTableBefore = mt.getBeforeModule();
        ModuleTable moduleTableAfter = moduleTableRepository.findOneByBeforeModule(mt);
        if (moduleTableBefore != null) {
            recursiveModuleOrderBack(moduleTableBefore);
        }
        if (moduleTableAfter != null) {
            moduleTableAfter.setBeforeModule(moduleTableBefore);
            this.moduleTableRepository.save(moduleTableAfter);
        } else {
            this.moduleTableRepository.save(moduleTableBefore);
        }
        this.moduleTableRepository.delete(mt);
    }

    @Override
    public List<ModuleTable> getParentList() {
        return this.moduleTableRepository.findDistinctByParentIsNullAndModuleOrderNotNullAndAvailableAndClientNot(true, this.clientTableRepository.findOneByClientId("user_admin_client"), new Sort("moduleOrder"));
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
    public List<TreeOption> getParentsOptions(Long clientId) {
        ClientTable clientTable = clientTableRepository.findOne(clientId);
        List<ModuleTable> list = moduleTableRepository.findByClientAndParentIsNullOrderByModuleOrderAsc(clientTable);
        List<TreeOption> listReturn = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (ModuleTable moduleTable : list) {
                listReturn.add(recursiveParentsOptions(moduleTable));
            }
        }
        return listReturn;
    }

    // 继续options的实现，同时tree也应该给出一个ruleChange的回调处理
    @Override
    public List<SelectOption> getBeforeOptions(Long parentId, Long clientId) {
        List<SelectOption> listReturn = new ArrayList<>();
        ModuleTable moduleTableParent = null;
        List<ModuleTable> list = new ArrayList<>();
        if (parentId != null && parentId != -1) {
            moduleTableParent = moduleTableRepository.findOne(parentId);
            list = moduleTableRepository.findByParentOrderByModuleOrderAsc(moduleTableParent);
        } else {
            list = moduleTableRepository.findByClientAndParentIsNullOrderByModuleOrderAsc(clientTableRepository.findOne(clientId));
        }
        if (list != null && list.size() > 0) {
            for (ModuleTable moduleTable : list) {
                listReturn.add(new SelectOption(moduleTable.getModuleName(), moduleTable.getId()));
            }
        }
        return listReturn;
    }

    @Override
    public List<SelectOption> getModuleRoleOptions(Long clientId) {
        ClientTable clientTable = clientTableRepository.findOne(clientId);
        List<ModuleRoleTable> list = moduleRoleTableRepository.findByClient(clientTable);
        List<SelectOption> listReturn = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (ModuleRoleTable moduleRoleTable : list) {
                listReturn.add(new SelectOption(moduleRoleTable.getName(), moduleRoleTable.getId()));
            }
        }
        return listReturn;
    }

    private void recursiveModuleOrder(ModuleTable moduleTable) {
        moduleTable.setModuleOrder(moduleTable.getModuleOrder() - 1);
        if (moduleTable.getBeforeModule() != null) {
            recursiveModuleOrder(moduleTable.getBeforeModule());
        }
    }

    private void recursiveModuleOrderBack(ModuleTable moduleTable) {
        moduleTable.setModuleOrder(moduleTable.getModuleOrder() + 1);
        if (moduleTable.getBeforeModule() != null) {
            recursiveModuleOrder(moduleTable.getBeforeModule());
        }
    }

    private TreeOption recursiveParentsOptions(ModuleTable moduleTable) {
        TreeOption treeOption = new TreeOption(moduleTable.getId(), moduleTable.getModuleName());
        treeOption.setEmitClick(true);
        treeOption.setIconClass(moduleTable.getIconClass());
        //treeOption.setUrl(moduleTable.getModuleUrl());
        if (moduleTable.getChildren() != null && moduleTable.getChildren().size() > 0) {
            List<TreeOption> list = new ArrayList<>();
            for (ModuleTable moduleTable1 : moduleTable.getChildren()) {
                list.add(recursiveParentsOptions(moduleTable1));
            }
            treeOption.setChildren(list);
        }
        return treeOption;
    }

}
