package cn.hopever.platform.user.web.controller;

import cn.hopever.platform.user.domain.ClientTable;
import cn.hopever.platform.user.domain.ModuleRoleTable;
import cn.hopever.platform.user.domain.RoleTable;
import cn.hopever.platform.user.domain.UserTable;
import cn.hopever.platform.user.service.ClientTableService;
import cn.hopever.platform.user.service.ModuleRoleTableService;
import cn.hopever.platform.user.service.RoleTableService;
import cn.hopever.platform.user.service.UserTableService;
import cn.hopever.platform.user.vo.UserVo;
import cn.hopever.platform.user.vo.UserVoAssembler;
import cn.hopever.platform.utils.moji.MojiUtils;
import cn.hopever.platform.utils.properties.CommonProperties;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.*;

/**
 * Created by Donghui Huo on 2016/8/29.
 */
@RestController
@RequestMapping(value = "/user", produces = "application/json")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    private ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private MojiUtils mojiUtils;
    @Autowired
    private CommonProperties commonProperties;
    @Autowired
    private UserTableService userTableService;
    @Autowired
    private RoleTableService roleTableService;
    @Autowired
    private ClientTableService clientTableService;
    @Autowired
    private ModuleRoleTableService moduleRoleTableService;
    @Autowired
    private UserVoAssembler userVoAssembler;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ROLE_super_admin') or hasRole('ROLE_admin')")
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestBody TableParameters body, Principal principal) {
        Map<String, Object> map = new HashMap<>();
        List<HashMap<String, Object>> listReturn = new ArrayList<>();
        String authority = ((OAuth2Authentication) principal).getAuthorities().iterator().next().getAuthority();
        Page<UserTable> list;
        PageRequest pageRequest;
        if (body.getSorts() == null || body.getSorts().isEmpty()) {
            pageRequest = new PageRequest(body.getPager().getCurrentPage(), body.getPager().getPageSize(), Sort.Direction.ASC, "id");
        } else {
            String key = body.getSorts().keySet().iterator().next();
            pageRequest = new PageRequest(body.getPager().getCurrentPage(), body.getPager().getPageSize(), Sort.Direction.fromString(body.getSorts().get(key)), key);
        }
        if ("ROLE_super_admin".equals(authority)) {
            list = userTableService.getListWithOutSelf(principal.getName(), pageRequest, body.getFilters());
        } else {
            list = userTableService.getSubList(principal.getName(), pageRequest, body.getFilters());
        }
        if (list != null && list.iterator().hasNext()) {
            listReturn = new ArrayList<>();
            for (UserTable ut : list) {
                HashMap<String, Object> mapTemp = new HashMap<>();
                mapTemp.put("key", ut.getId());
                List<Object> listTmp = new ArrayList<>();
                listTmp.add(ut.getUsername());
                listTmp.add(ut.getName());
                listTmp.add(ut.getEmail());
                listTmp.add(ut.getPhone());
                listTmp.add(ut.getCreateUser());
                listTmp.add(ut.getCreatedDate() != null ? ut.getCreatedDate().getTime() : null);
                listTmp.add(ut.isEnabled());
                listTmp.add(ut.isAccountNonExpired());
                mapTemp.put("value", listTmp);
                listReturn.add(mapTemp);
            }
            map.put("rows", listReturn);
            map.put("totalCount", list.getTotalElements());

        } else {
            map.put("rows", null);
            map.put("totalCount", 0);
        }
        map.put("pager", body.getPager());
        map.put("filters", body.getFilters());
        map.put("sorts", body.getSorts());
        HashMap mapReturn = new HashMap();
        mapReturn.put("data", map);
        return map;
    }

    @PreAuthorize("hasRole('ROLE_super_admin') or hasRole('ROLE_admin')")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key, Principal principal) {
        if (validateUserOperation(userTableService.getUserByUsername(principal.getName()), this.userTableService.get(key))) {
            this.userTableService.delete(key);
        }
    }

    @PreAuthorize("hasRole('ROLE_super_admin') or hasRole('ROLE_admin')")
    @RequestMapping(value = "/info", method = {RequestMethod.GET})
    public UserVo info(@RequestParam Long key, Principal principal) {
        //返回user是无法解析的，要使用对象解析为map 的形式
        UserTable ut = userTableService.get(key);
        UserVo ur = userVoAssembler.toResource(ut);
        if (validateUserOperation(userTableService.getUserByUsername(principal.getName()), ut)) {
            return ur;
        } else {
            return null;
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/", ""}, method = {RequestMethod.GET})
    public UserVo info(Principal principal) {
        //返回user是无法解析的，要使用对象解析为map 的形式
        UserTable ut = this.userTableService.getUserByUsername(principal.getName());
        UserVo ur = userVoAssembler.toResource(ut);
        return ur;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/personal/update", method = {RequestMethod.POST})
    public VueResults.Result updatePersonalUser(@RequestPart(required = true) UserVo userVo, @RequestPart("photo") MultipartFile[] files, Principal principal) {
        // 此处应该考虑form表单的file的属性，所以应该是form mate方式，update也一样
        UserTable user = this.userTableService.getUserByUsername(principal.getName());
        if (userVo.getEmail() != null) {
            UserTable ut = this.userTableService.getUserByEmail(userVo.getEmail());
            if (ut != null && userVo.getId() != ut.getId()) {
                return VueResults.generateError("更新失败", "用户Email已存在");
            }
        }

        if (userVo.getPhone() != null) {
            UserTable ut = this.userTableService.getUserByPhone(userVo.getPhone());
            if (ut != null && userVo.getId() != ut.getId()) {
                return VueResults.generateError("更新失败", "用户电话号码已存在");
            }
        }
        userVoAssembler.toDomain(userVo, user);
        if (files != null && files.length > 0) {
            try {
                Map<String, List<String>> mapPhotos = mojiUtils.uploadImg("user/photo/", files);
                mapPhotos.get("fileKeys");
                List<String> list = mapPhotos.get("fileKeys");
                if (list != null && list.size() > 0) {
                    user.setPhoto(commonProperties.getImagePathPrev() + list.get(0));
                }
            } catch (Exception e) {
                logger.error("save user photo failed", e);
                return VueResults.generateError("更新失败", "保存用户头像失败");
            }
        }
        if (userVo.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userVo.getPassword()));
        }
        userTableService.save(user);
        return VueResults.generateSuccess("更新成功", "用户更新完成");
    }


    @PreAuthorize("hasRole('ROLE_super_admin') or hasRole('ROLE_admin')")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result updateUser(@RequestPart(required = true) UserVo userVo, @RequestPart("photo") MultipartFile[] files, Principal principal) {
        UserTable userController = userTableService.getUserByUsername(principal.getName());
        RoleTable rtController = getTopRole(userController);
        UserTable user = userTableService.get(userVo.getId());
        if (!validateUserOperation(userController, user)) {
            return VueResults.generateError("更新失败", "无权限修改该用户");
        }
        if (userVo.getEmail() != null) {
            UserTable ut = this.userTableService.getUserByEmail(userVo.getEmail());
            if (ut != null && ut.getId() != userVo.getId()) {
                return VueResults.generateError("更新失败", "用户Email已存在");
            }
        }
        if (userVo.getEmail() != null) {
            UserTable ut = this.userTableService.getUserByPhone(userVo.getEmail());
            if (ut != null && ut.getId() != userVo.getId()) {
                return VueResults.generateError("更新失败", "用户电话号码已存在");
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
                    user.setPhoto(commonProperties.getImagePathPrev() + list.get(0));
                }
            } catch (Exception e) {
                logger.error("save user photo failed", e);
            }
        }
        RoleTable rtTop = roleTableService.get(userVo.getAuthorities());
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
            listPartClients.add(clientTableService.loadClientByClientId("user_admin_client"));
            if (rtController != null && rtController.getLevel() != 0 && user.getClients() != null) {
                for (ClientTable ct : user.getClients()) {
                    if (userController.getClients() != null && !userController.getClients().contains(ct)) {
                        listPartClients.add(ct);
                    }
                }
            }
            if (userVo.getClients() != null) {
                listPartClients.addAll(clientTableService.getByIds(userVo.getClients()));
            }
            user.setClients(listPartClients);
        }
        List<ModuleRoleTable> listPartModileRoles = user.getModulesAuthorities();
        if (rtTop.getLevel() > 1 && rtController != null && rtController.getLevel() != 0 && userController.getClients() != null && listPartModileRoles != null && listPartModileRoles.size() > 0) {
            for (ClientTable ct : userController.getClients()) {
                List<ModuleRoleTable> moduleRoles = ct.getModuleRoles();
                if (moduleRoles != null) {
                    for (ModuleRoleTable mr : moduleRoles) {
                        listPartModileRoles.remove(mr);
                    }
                }
            }
        }
        if (userVo.getModulesAuthorities() != null) {
            listPartModileRoles.addAll(moduleRoleTableService.getByIds(userVo.getModulesAuthorities()));
        }
        user.setModulesAuthorities(listPartModileRoles);
        userTableService.save(user);
        return VueResults.generateError("更新成功", "用户信息更新成功");
    }

    @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result saveUser(@RequestPart(required = true) UserVo userVo, @RequestPart("photo") MultipartFile[] files, Principal principal) {
        UserTable user = new UserTable();
        if (this.userTableService.getUserByUsername(userVo.getUsername()) != null) {
            return VueResults.generateError("创建失败", "用户账号已存在");
        }
        if (userVo.getEmail() != null) {
            UserTable ut = this.userTableService.getUserByEmail(userVo.getEmail());
            if (ut != null) {
                return VueResults.generateError("创建失败", "用户Email已存在");
            }
        }
        if (userVo.getEmail() != null) {
            UserTable ut = this.userTableService.getUserByPhone(userVo.getEmail());
            if (ut != null) {
                return VueResults.generateError("创建失败", "用户电话号码已存在");
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
                    user.setPhoto(commonProperties.getImagePathPrev() + list.get(0));
                }
            } catch (Exception e) {
                logger.error("save user photo failed", e);
                user.setPhoto(commonProperties.getDefaultUserPhoto());
            }
        }
        if (userVo.getAuthorities() != null) {
            List list = new ArrayList<>();
            list.add(roleTableService.get(userVo.getAuthorities()));
            user.setAuthorities(list);
        } else {
            List list = new ArrayList<>();
            list.add(roleTableService.getByAuthority("ROLE_common_user"));
            user.setAuthorities(list);
        }
        List clientsUpdate = new ArrayList<>();
        clientsUpdate.add(clientTableService.loadClientByClientId("user_admin_client"));
        if (userVo.getClients() != null) {
            clientTableService.getByIds(userVo.getClients());
            clientsUpdate.addAll(clientTableService.getByIds(userVo.getClients()));
        }
        user.setClients(clientsUpdate);
        if (userVo.getModulesAuthorities() != null) {
            user.setModulesAuthorities(moduleRoleTableService.getByIds(userVo.getModulesAuthorities()));
        } else {
            user.setModulesAuthorities(null);
        }
        user.setCreatedDate(new Date());
        user.setCreateUser(this.userTableService.getUserByUsername(principal.getName()));
        userTableService.save(user);
        return VueResults.generateSuccess("创建成功", "用户创建成功");
    }

    @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/list/relatedusers/options", method = {RequestMethod.GET})
    public List getListOptionsByClients(@RequestParam String clientId) {
        List listOptions = null;
        Long selected = null;
        List<UserTable> list = userTableService.getListByClientId(clientId);
        if (list != null && list.size() > 0) {
            listOptions = new ArrayList<>();
            for (UserTable ut : list) {
                Map mapOption = new HashMap<>();
                mapOption.put("label", ut.getUsername());
                mapOption.put("value", ut.getUsername());
                listOptions.add(mapOption);
            }
        }
        return listOptions;
    }

    private boolean validateUserOperation(UserTable ut1, UserTable ut2) {
        // 此处应该判断user是否有相同的role，以及是否包含有相同的role
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

    public static void main(String[] args) {
        UserTable u = new UserTable();
        u.setLimitedDate(new Date());
        u.setName("123");
        UserVo uv = new UserVo();
        BeanUtils.copyNotNullProperties(u, uv);
        System.out.print(uv);
    }
}
