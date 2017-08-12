package cn.hopever.platform.user.service.impl;

import cn.hopever.platform.user.domain.ClientTable;
import cn.hopever.platform.user.domain.ModuleRoleTable;
import cn.hopever.platform.user.domain.RoleTable;
import cn.hopever.platform.user.domain.UserTable;
import cn.hopever.platform.user.repository.*;
import cn.hopever.platform.user.service.UserTableService;
import cn.hopever.platform.user.vo.UserVo;
import cn.hopever.platform.user.vo.UserVoAssembler;
import cn.hopever.platform.utils.moji.MojiUtils;
import cn.hopever.platform.utils.properties.CommonProperties;
import cn.hopever.platform.utils.web.SelectOption;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
@Service("userTableService")
@Transactional
public class UserTableServiceImpl implements UserTableService {

    Logger logger = LoggerFactory.getLogger(UserTableServiceImpl.class);

    @Autowired
    private UserTableRepository userTableRepository;
    @Autowired
    private CustomUserTableRepository customUserTableRepository;

    @Autowired
    private RoleTableRepository roleTableRepository;

    @Autowired
    private ClientTableRepository clientTableRepository;

    @Autowired
    private ModuleRoleTableRepository moduleRoleTableRepository;

    @Autowired
    private UserVoAssembler userVoAssembler;

    @Autowired
    private MojiUtils mojiUtils;

    @Autowired
    private CommonProperties commonProperties;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @CachePut
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserTable user = userTableRepository.findOneByUsername(username);
        if (user == null) {
            user = userTableRepository.findOneByPhone(username);
        }
        if (user == null) {
            user = userTableRepository.findOneByEmail(username);
        }
        if (user == null) {
            throw new UsernameNotFoundException("username " + username
                    + " not found");
        }
        return user;
    }

    @Override
    public UserTable save(UserTable user) {
        return userTableRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserTable getUserByUsername(String username) {
        UserTable ut = userTableRepository.findOneByUsername(username);
        // 避免落入session陷阱，但是这样又
        ut.getAuthorities();
        return ut;
    }

    @Override
    public UserTable getUserByEmail(String email) {
        return userTableRepository.findOneByEmail(email);
    }

    @Override
    public UserTable getUserByPhone(String phone) {
        return userTableRepository.findOneByPhone(phone);
    }

    @Override
    public Iterable<UserTable> getList() {
        return userTableRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserTable> getListWithOutSelf(String username, Pageable pageable, Map<String, Object> filterMap) {
        if (filterMap == null) {
            return userTableRepository.findByUsernameNot(username, pageable);
        } else {
            return customUserTableRepository.findByUsernameNotAndFilters(username, filterMap, pageable);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserTable> getSubList(String username, Pageable pageable, Map<String, Object> filterMap) {
        UserTable ut = userTableRepository.findOneByUsername(username);
        List<RoleTable> list1 = new ArrayList<>();
        List<RoleTable> list2 = new ArrayList<>();
        list1.add(roleTableRepository.findOneByAuthority("ROLE_admin"));
        list2.add(roleTableRepository.findOneByAuthority("ROLE_common_user"));
        List listUpdate = new ArrayList<>();
        List<ClientTable> list = ut.getClients();
        if (list != null) {
            for (ClientTable ct : list) {
                if (!"user_admin_client".equals(ct.getClientId())) {
                    listUpdate.add(ct);
                }
            }
        }
        if (listUpdate != null && listUpdate.size() > 0) {
            return customUserTableRepository.findByCreateUserAndAuthoritiesInAndClientsInAndFilters(ut, list1, list2, listUpdate, filterMap, pageable);
        }
        return null;
    }

    @Override
    public List<UserTable> getListByClientId(String clientId) {
        List<RoleTable> list1 = new ArrayList<>();
        list1.add(roleTableRepository.findOneByAuthority("ROLE_common_user"));
        List<ClientTable> list2 = new ArrayList<>();
        list2.add(clientTableRepository.findOneByClientId(clientId));
        return userTableRepository.findByAuthoritiesInAndClientsIn(list1, list2);
    }

    @Override
    public void delete(Long id) {
        this.userTableRepository.updateCreateUser(null, this.userTableRepository.findOne(id));
        this.userTableRepository.delete(id);
    }

    @Override
    public UserTable get(Long id) {
        return this.userTableRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserTable> getList(TableParameters body, Principal principal) {
        UserTable userTable = this.getUserByUsername(principal.getName());
        String authority = this.getTopRole(userTable).getAuthority();
        Page<UserTable> list;
        PageRequest pageRequest;
        if (body.getSorts() == null || body.getSorts().isEmpty()) {
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.DESC, "createdDate");
        } else {
            String key = body.getSorts().keySet().iterator().next();
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.fromString(body.getSorts().get(key)), key);
        }
        if ("ROLE_super_admin".equals(authority)) {
            list = this.getListWithOutSelf(principal.getName(), pageRequest, body.getFilters());
        } else {
            list = this.getSubList(principal.getName(), pageRequest, body.getFilters());
        }
        return list;
    }

    @Override
    public void delete(Long id, Principal principal) {
        if (validateUserOperation(userTableRepository.findOneByUsername(principal.getName()), userTableRepository.findOne(id))) {
            userTableRepository.delete(id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserVo info(Long id, Principal principal) {
        UserVo ur = null;
        if (id == null) {
            UserTable ut = userTableRepository.findOneByUsername(principal.getName());
            ur = userVoAssembler.toResource(ut);
        } else {
            UserTable ut = userTableRepository.findOne(id);
            // cache
            if (validateUserOperation(userTableRepository.findOneByUsername(principal.getName()), ut)) {
                ur = userVoAssembler.toResource(ut);
            }
        }
        return ur;
    }

    @Override
    public VueResults.Result updatePersonalUser(UserVo userVo, MultipartFile[] files, Principal principal) {
        UserTable user = userTableRepository.findOneByUsername(principal.getName());
        if (userVo.getEmail() != null) {
            UserTable ut = userTableRepository.findOneByEmail(userVo.getEmail());
            if (ut != null && userVo.getId() != ut.getId()) {
                return VueResults.generateError("更新失败", "Email已存在");
            }
        }

        if (userVo.getPhone() != null) {
            UserTable ut = userTableRepository.findOneByPhone(userVo.getPhone());
            if (ut != null && userVo.getId() != ut.getId()) {
                return VueResults.generateError("更新失败", "电话号码已存在");
            }
        }
        userVoAssembler.toDomain(userVo, user);
        if (files != null && files.length > 0) {
            try {
                Map<String, List<String>> mapPhotos = mojiUtils.uploadImg("user/photo/", files);
                mapPhotos.get("fileKeys");
                List<String> list = mapPhotos.get("fileKeys");
                if (list != null && list.size() > 0) {
                    user.setPhoto(list.get(0));
                }
            } catch (Exception e) {
                logger.error("save user photo failed", e);
            }
        }
        if (userVo.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userVo.getPassword()));
        }
        userTableRepository.save(user);
        return VueResults.generateSuccess("更新成功", "用户更新完成");
    }

    @Override
    public VueResults.Result updateUser(UserVo userVo, MultipartFile[] files, Principal principal) {
        UserTable userController = userTableRepository.findOneByUsername(principal.getName());
        RoleTable rtController = getTopRole(userController);
        UserTable user = userTableRepository.findOne(userVo.getId());
        if (!validateUserOperation(userController, user)) {
            return VueResults.generateError("更新失败", "无权限修改该用户");
        }
        if (userVo.getEmail() != null) {
            UserTable ut = userTableRepository.findOneByEmail(userVo.getEmail());
            if (ut != null && ut.getId() != userVo.getId()) {
                return VueResults.generateError("更新失败", "用户Email已存在");
            }
        }
        if (userVo.getEmail() != null) {
            UserTable ut = userTableRepository.findOneByPhone(userVo.getPhone());
            if (ut != null && ut.getId() != userVo.getId()) {
                return VueResults.generateError("更新失败", "用户电话号码已存在");
            }
        }
        userVoAssembler.toDomain(userVo, user);
        if (user.getCreateUser() == null) {
            user.setCreateUser(userController);
        }
        if (userVo.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userVo.getPassword()));
        }
        if (files != null && files.length > 0) {
            try {
                Map<String, List<String>> mapPhotos = mojiUtils.uploadImg("user/photo/", files);
                mapPhotos.get("fileKeys");
                List<String> list = mapPhotos.get("fileKeys");
                if (list != null && list.size() > 0) {
                    user.setPhoto(list.get(0));
                }
            } catch (Exception e) {
                logger.error("save user photo failed", e);
            }
        }
        RoleTable rtTop = roleTableRepository.findOne(userVo.getAuthorities());
        if (userVo.getAuthorities() != null) {
            List<RoleTable> list = user.getAuthorities();
            for (RoleTable rt : list) {
                if (rt.getLevel() < 3) {
                    list.remove(rt);
                }
            }
            list.add(rtTop);
            user.setAuthorities(list);
        }
        if (rtTop.getLevel() > 0) {
            List listPartClients = new ArrayList<>();
            listPartClients.add(clientTableRepository.findOneByClientId("user_admin_client"));
            if (rtController != null && rtController.getLevel() != 0 && user.getClients() != null) {
                for (ClientTable ct : user.getClients()) {
                    if (userController.getClients() != null && !userController.getClients().contains(ct)) {
                        listPartClients.add(ct);
                    }
                }
            }
            if (userVo.getClients() != null) {
                Iterable<ClientTable> iterable = clientTableRepository.findAll(userVo.getClients());
                for (ClientTable clientTable : iterable) {
                    listPartClients.add(clientTable);
                }
            }
            user.setClients(listPartClients);
        }
        List<ModuleRoleTable> listPartModuleRoles = new ArrayList<>();
        if (rtController.getLevel() == 1) {
            listPartModuleRoles = user.getModulesAuthorities();
        }
        if (rtTop.getLevel() > 1 && rtController != null && userController.getClients() != null && listPartModuleRoles != null && listPartModuleRoles.size() > 0) {
            for (ClientTable ct : userController.getClients()) {
                List<ModuleRoleTable> moduleRoles = ct.getModuleRoles();
                if (moduleRoles != null) {
                    for (ModuleRoleTable mr : moduleRoles) {
                        listPartModuleRoles.remove(mr);
                    }
                }
            }
        }
        if (userVo.getModulesAuthorities() != null) {
            Iterable<ModuleRoleTable> iterable = moduleRoleTableRepository.findAll(userVo.getModulesAuthorities());
            for (ModuleRoleTable mr : iterable) {
                listPartModuleRoles.add(mr);
            }
        }
        user.setModulesAuthorities(listPartModuleRoles);
        userTableRepository.save(user);
        return VueResults.generateError("更新成功", "更新成功");
    }

    @Override
    public VueResults.Result saveUser(UserVo userVo, MultipartFile[] files, Principal principal) {
        UserTable user = new UserTable();
        if (userTableRepository.findOneByUsername(userVo.getUsername()) != null) {
            return VueResults.generateError("创建失败", "账号已存在");
        }
        if (userVo.getEmail() != null) {
            UserTable ut = userTableRepository.findOneByEmail(userVo.getEmail());
            if (ut != null) {
                return VueResults.generateError("创建失败", "Email已存在");
            }
        }
        if (userVo.getPhone() != null) {
            UserTable ut = userTableRepository.findOneByPhone(userVo.getPhone());
            if (ut != null) {
                return VueResults.generateError("创建失败", "电话号码已存在");
            }
        }
        userVoAssembler.toDomain(userVo, user);
        if (userVo.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userVo.getPassword()));
        }
        if (files != null && files.length > 0) {
            try {
                Map<String, List<String>> mapPhotos = mojiUtils.uploadImg("user/photo/", files);
                mapPhotos.get("fileKeys");
                List<String> list = mapPhotos.get("fileKeys");
                if (list != null && list.size() > 0) {
                    user.setPhoto(list.get(0));
                }
            } catch (Exception e) {
                logger.error("save user photo failed", e);
                user.setPhoto(commonProperties.getDefaultUserPhoto());
            }
        }
        List<RoleTable> listAuthorities = new ArrayList<>();
        if (userVo.getAuthoritiesKey() == null || !userVo.getAuthoritiesKey().equals("ROLE_super_admin")) {
            List<ClientTable> clientsUpdate = new ArrayList<>();
            clientsUpdate.add(clientTableRepository.findOneByClientId("user_admin_client"));
            if (userVo.getClients() != null) {
                Iterable<ClientTable> iterable = clientTableRepository.findAll(userVo.getClients());
                for (ClientTable clientTable : iterable) {
                    clientsUpdate.add(clientTable);
                }
            }
            user.setClients(clientsUpdate);
            for (ClientTable clientTable : clientsUpdate) {
                listAuthorities.add(roleTableRepository.findOneByAuthority("ROLE_" + clientTable.getClientId()));
            }
        }
        if (userVo.getAuthoritiesKey() != null) {
            listAuthorities.add(roleTableRepository.findOneByAuthority(userVo.getAuthoritiesKey()));
        } else {
            listAuthorities.add(roleTableRepository.findOneByAuthority("ROLE_common_user"));
        }
        user.setAuthorities(listAuthorities);
        if (userVo.getModulesAuthorities() != null) {
            List<ModuleRoleTable> moduleRoleTableList = new ArrayList<>();
            Iterable<ModuleRoleTable> iterable = moduleRoleTableRepository.findAll(userVo.getModulesAuthorities());
            for (ModuleRoleTable mr : iterable) {
                moduleRoleTableList.add(mr);
            }
            user.setModulesAuthorities(moduleRoleTableList);
        } else {
            user.setModulesAuthorities(null);
        }
        user.setCreatedDate(new Date());
        user.setCreateUser(userTableRepository.findOneByUsername(principal.getName()));
        userTableRepository.save(user);
        return VueResults.generateSuccess("创建成功", "创建成功");
    }

    @Override
    public VueResults.Result registerUser(UserVo userVo, MultipartFile[] files) {
        UserTable user = new UserTable();
        if (userTableRepository.findOneByUsername(userVo.getUsername()) != null) {
            return VueResults.generateError("注册失败", "账号已存在");
        }
        if (userVo.getEmail() != null) {
            if (userTableRepository.findOneByEmail(userVo.getEmail()) != null) {
                return VueResults.generateError("注册失败", "Email已存在");
            }
        }
        if (userVo.getPhone() != null) {
            if (userTableRepository.findOneByPhone(userVo.getPhone()) != null) {
                return VueResults.generateError("注册失败", "电话号码已存在");
            }
        }
        userVoAssembler.toDomain(userVo, user);
        if (userVo.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userVo.getPassword()));
        }
        if (files != null && files.length > 0) {
            try {
                Map<String, List<String>> mapPhotos = mojiUtils.uploadImg("user/photo/", files);
                mapPhotos.get("fileKeys");
                List<String> list = mapPhotos.get("fileKeys");
                if (list != null && list.size() > 0) {
                    user.setPhoto(list.get(0));
                }
            } catch (Exception e) {
                logger.error("save user photo failed", e);
                user.setPhoto(commonProperties.getDefaultUserPhoto());
            }
        }
        List list = new ArrayList<>();
        list.add(roleTableRepository.findOneByAuthority("ROLE_common_user"));
        list.add(roleTableRepository.findOneByAuthority("ROLE_user_admin_client"));
        user.setAuthorities(list);
        List clientsUpdate = new ArrayList<>();
        clientsUpdate.add(clientTableRepository.findOneByClientId("user_admin_client"));
        user.setClients(clientsUpdate);
        user.setCreatedDate(new Date());
        userTableRepository.save(user);
        return VueResults.generateSuccess("注册成功", "您已成功注册本系统");
    }

    @Override
    public List<SelectOption> getRoleOptions(Long id, Principal principal) {
        UserTable utAdmin = userTableRepository.findOneByUsername(principal.getName());
        UserTable ut = null;
        if (id != null) {
            ut = userTableRepository.findOne(id);
        }
        RoleTable roleTable = getTopRole(utAdmin);
        List<SelectOption> listReturn = new ArrayList<>();
        RoleTable roleTableTmp = roleTableRepository.findOneByAuthority("ROLE_super_admin");
        SelectOption selectOption1 = new SelectOption(roleTableTmp.getName(), "ROLE_super_admin");
        if (ut != null && ut.getAuthorities().contains(roleTableTmp)) {
            selectOption1.setSelected(true);
        }
        roleTableTmp = roleTableRepository.findOneByAuthority("ROLE_admin");
        SelectOption selectOption2 = new SelectOption(roleTableTmp.getName(), "ROLE_admin");
        if (ut != null && ut.getAuthorities().contains(roleTableTmp)) {
            selectOption2.setSelected(true);
        }
        roleTableTmp = roleTableRepository.findOneByAuthority("ROLE_common_user");
        SelectOption selectOption3 = new SelectOption(roleTableTmp.getName(), "ROLE_common_user");
        if (ut != null && ut.getAuthorities().contains(roleTableTmp)) {
            selectOption3.setSelected(true);
        }
        listReturn.add(selectOption1);
        listReturn.add(selectOption2);
        listReturn.add(selectOption3);
        if (roleTable.getLevel() == 1) {
            listReturn.remove(selectOption1);
        } else if (roleTable.getLevel() == 2) {
            listReturn = null;
        }
        return listReturn;
    }

    @Override
    // 注意将user_admin_client去除在外，因为应都有此子应用权限，只是权限大小不同
    public List<SelectOption> getClientOptions(Long id, Principal principal) {
        UserTable utAdmin = userTableRepository.findOneByUsername(principal.getName());
        UserTable ut = id == null ? null : userTableRepository.findOne(id);
        RoleTable roleTable = getTopRole(utAdmin);
        if (ut == null || validateUserOperation(utAdmin, ut)) {
            if (roleTable.getLevel() == 1 && utAdmin.getClients() != null && utAdmin.getClients().size() > 0) {
                List<SelectOption> listReturn = new ArrayList<>();
                for (ClientTable clientTable : utAdmin.getClients()) {
                    if (!clientTable.getClientId().equals("user_admin_client")) {
                        SelectOption selectOption = new SelectOption(clientTable.getClientName(), clientTable.getId());
                        if (ut != null && ut.getClients().contains(clientTable)) {
                            selectOption.setSelected(true);
                        }
                        listReturn.add(selectOption);
                    }
                }
                return listReturn;
            } else if (roleTable.getLevel() == 0) {
                List<SelectOption> listReturn = new ArrayList<>();
                Iterable<ClientTable> list = clientTableRepository.findAll();
                for (ClientTable clientTable : list) {
                    if (!clientTable.getClientId().equals("user_admin_client")) {
                        SelectOption selectOption = new SelectOption(clientTable.getClientName(), clientTable.getId());
                        if (ut != null && ut.getClients().contains(clientTable)) {
                            selectOption.setSelected(true);
                        }
                        listReturn.add(selectOption);
                    }
                }
                return listReturn;
            }
        }
        return null;
    }

    @Override
    public List<SelectOption> getModulesAuthoritiesOptions(Long id, Principal principal, List<SelectOption> clientOptions, List<Integer> clientIds) {
        UserTable utAdmin = userTableRepository.findOneByUsername(principal.getName());
        UserTable ut = null;
        if ((clientOptions != null || clientIds != null)) {
            if (id != null) {
                ut = userTableRepository.findOne(id);
            }
            if (ut == null || (clientIds != null || clientOptions != null && this.getTopRole(ut).getAuthority().equals("ROLE_common_user")) && validateUserOperation(utAdmin, ut)) {
                List<SelectOption> listReturn = new ArrayList<>();
                if (clientOptions != null) {
                    for (SelectOption selectOption : clientOptions) {
                        if (selectOption.isSelected()) {
                            ClientTable clientTable = clientTableRepository.findOne((long) selectOption.getValue());
                            if (clientTable.getModuleRoles() != null && clientTable.getModuleRoles().size() > 0) {
                                for (ModuleRoleTable moduleRoleTable : clientTable.getModuleRoles()) {
                                    SelectOption selectOptionTemp = new SelectOption(moduleRoleTable.getName(), moduleRoleTable.getId());
                                    if (ut != null && ut.getModulesAuthorities().contains(moduleRoleTable)) {
                                        selectOptionTemp.setSelected(true);
                                    }
                                    listReturn.add(selectOptionTemp);
                                }
                            }
                        }
                    }
                } else if (clientIds != null) {
                    for (Integer selectOption : clientIds) {
                        ClientTable clientTable = clientTableRepository.findOne((long) selectOption);
                        if (clientTable.getModuleRoles() != null && clientTable.getModuleRoles().size() > 0) {
                            for (ModuleRoleTable moduleRoleTable : clientTable.getModuleRoles()) {
                                SelectOption selectOptionTemp = new SelectOption(moduleRoleTable.getName(), moduleRoleTable.getId());
                                if (ut != null && ut.getModulesAuthorities().contains(moduleRoleTable)) {
                                    selectOptionTemp.setSelected(true);
                                }
                                listReturn.add(selectOptionTemp);
                            }
                        }
                    }
                }
                return listReturn;
            }
        }
        return null;
    }

    private boolean validateUserOperation(UserTable ut1, UserTable ut2) {
        RoleTable rt1 = getTopRole(ut1);
        RoleTable rt2 = getTopRole(ut2);
        if (rt1.getAuthority().equals("ROLE_super_admin")) {
            return true;
        } else {
            if (rt1.getAuthority().equals("ROLE_admin") && rt2.getAuthority().equals("ROLE_admin")) {
                if (ut2.getCreateUser() != null && ut2.getCreateUser().getId() == ut1.getId()) {
                    return true;
                }
            } else if (rt1.getAuthority().equals("ROLE_admin") && rt2.getAuthority().equals("ROLE_common_user")) {
                if (ut1.getClients() != null) {
                    for (ClientTable ct : ut1.getClients()) {
                        if (ct.getClientId().equals("user_admin_client")) {
                            continue;
                        }
                        if (ut2.getClients() != null && ut2.getClients().contains(ct)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
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
