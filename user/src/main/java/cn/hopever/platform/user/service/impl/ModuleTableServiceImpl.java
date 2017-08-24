package cn.hopever.platform.user.service.impl;

import cn.hopever.platform.user.domain.*;
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

import java.security.Principal;
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
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.ASC, "moduleOrder");
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
            body.getFilters().put("parent", moduleTableRepository.findOne(Long.valueOf(body.getFilters().get("parentId").toString())));
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
        ModuleTable moduleTableParent = null;
        if (moduleVo.getParentId() != null) {
            moduleTableParent = moduleTableRepository.findOne(moduleVo.getParentId());
        }
        if (moduleVo.getAuthorities() != null) {
            List<ModuleRoleTable> list = new ArrayList<>();
            for (Long id : moduleVo.getAuthorities()) {
                list.add(moduleRoleTableRepository.findOne(id));
            }
            moduleTable.setAuthorities(list);
        }
        if (moduleVo.getBeforeId() != null && !(moduleTable.getBeforeModule() != null && moduleTable.getBeforeModule().getId() == moduleVo.getBeforeId())) {
            ModuleTable moduleTableOld = moduleTableRepository.findOneByBeforeModule(moduleTable);
            if (moduleTableOld != null) {
                moduleTableOld.setBeforeModule(moduleTable.getBeforeModule());
                moduleTableRepository.save(moduleTableOld);
            }
            ModuleTable moduleTable1 = moduleTableRepository.findOne(moduleVo.getBeforeId());
            moduleTable.setBeforeModule(moduleTable1);
            ModuleTable moduleTableAfter = moduleTableRepository.findOneByBeforeModule(moduleTable1);
            if (moduleTableAfter != null) {
                moduleTableAfter.setBeforeModule(moduleTable);
                moduleTableRepository.save(moduleTableAfter);
            }
            moduleTable.setModuleOrder(moduleTable1.getModuleOrder());
            recursiveModuleOrder(moduleTable1);
        } else {
            ModuleTable moduleTable1 = moduleTableRepository.findTopByParentAndClientOrderByModuleOrderDesc(moduleTableParent, moduleTable.getClient());
            if (moduleTable1 != null) {
                if (moduleTable.getId() != moduleTable1.getId()) {
                    ModuleTable moduleTableOld = moduleTableRepository.findOneByBeforeModule(moduleTable);
                    if (moduleTableOld != null) {
                        moduleTableOld.setBeforeModule(moduleTable.getBeforeModule());
                        moduleTableRepository.save(moduleTableOld);
                    }
                    moduleTable.setBeforeModule(moduleTable1);
                    moduleTable.setModuleOrder(moduleTable1.getModuleOrder() + 1);
                }
            } else {
                moduleTable.setBeforeModule(null);
                moduleTable.setModuleOrder(0);
            }
        }
        moduleTable.setParent(moduleTableParent);
        moduleTableRepository.save(moduleTable);
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
        } else if (moduleTableBefore != null) {
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
    public List<TreeOption> getParentsOptions(Long clientId, Long id) {
        ClientTable clientTable = clientTableRepository.findOne(clientId);
        List<ModuleTable> list = null;
        if (id == null) {
            list = moduleTableRepository.findByClientAndParentIsNullOrderByModuleOrderAsc(clientTable);
        } else {
            list = moduleTableRepository.findByClientAndParentIsNullAndIdNotOrderByModuleOrderAsc(clientTable, id);
        }
        List<TreeOption> listReturn = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (ModuleTable moduleTable : list) {
                TreeOption treeOption = recursiveParentsOptions(moduleTable, id);
                if (treeOption != null) {
                    listReturn.add(treeOption);
                }
            }
        }
        return listReturn;
    }

    // 继续options的实现，同时tree也应该给出一个ruleChange的回调处理
    @Override
    public List<SelectOption> getBeforeOptions(Long parentId, Long clientId, Long id) {
        List<SelectOption> listReturn = new ArrayList<>();
        ModuleTable moduleTableParent = null;
        List<ModuleTable> list = new ArrayList<>();
        if (parentId != null && parentId != -1) {
            moduleTableParent = moduleTableRepository.findOne(parentId);
            if (id == null) {
                list = moduleTableRepository.findByParentOrderByModuleOrderAsc(moduleTableParent);
            } else {
                list = moduleTableRepository.findByParentAndIdNotOrderByModuleOrderAsc(moduleTableParent, id);
            }
        } else {
            if (id == null) {
                list = moduleTableRepository.findByClientAndParentIsNullOrderByModuleOrderAsc(clientTableRepository.findOne(clientId));
            } else {
                list = moduleTableRepository.findByClientAndParentIsNullAndIdNotOrderByModuleOrderAsc(clientTableRepository.findOne(clientId), id);
            }
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

    @Override
    public List<TreeOption> getLeftMenu(Principal principal, String clientId) {
        UserTable userTable = userTableRepository.findOneByUsername(principal.getName());
        ClientTable clientTable = clientTableRepository.findOneByClientId(clientId);
        if(clientId.equals("user_admin_client")){
            //不必按照查询来，而要根据user的权限来
            RoleTable roleTable = getTopRole(userTable);
            if(roleTable.getAuthority().equals("ROLE_super_admin")){
                // return 全部
            } else if(roleTable.getAuthority().equals("ROLE_admin")){
                // 返回其可以管理的用户选项等
            } else{
                // 只返回一个个人信息的处理
            }
        }else{
            // 首先判断用户是否和client 有关联，然后是 获取该client 关联的modulerole
            // 然后筛选出用户的module role，然后根据role获取module
        }
        return null;
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

    private TreeOption recursiveParentsOptions(ModuleTable moduleTable, Long id) {
        if (id == null || id != moduleTable.getId()) {
            TreeOption treeOption = new TreeOption(moduleTable.getId(), moduleTable.getModuleName());
            treeOption.setEmitClick(true);
            treeOption.setIconClass(moduleTable.getIconClass());
            //treeOption.setUrl(moduleTable.getModuleUrl());
            if (moduleTable.getChildren() != null && moduleTable.getChildren().size() > 0) {
                List<TreeOption> list = new ArrayList<>();
                for (ModuleTable moduleTable1 : moduleTable.getChildren()) {
                    TreeOption treeOptionTemp = recursiveParentsOptions(moduleTable1, id);
                    if (treeOptionTemp != null) {
                        list.add(treeOptionTemp);
                    }
                }
                treeOption.setChildren(list);
            }
            return treeOption;
        }
        return null;
    }

    private RoleTable getTopRole(UserTable userTable) {
        List<RoleTable> rtControllers = userTable.getAuthorities();
        for (RoleTable rt : rtControllers) {
            if (rt.getLevel() < 3) {
                return rt;
            }
        }
        return null;
    }

}
