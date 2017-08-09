package cn.hopever.platform.user.web.controller;

import cn.hopever.platform.user.domain.UserTable;
import cn.hopever.platform.user.service.ClientTableService;
import cn.hopever.platform.user.service.ModuleRoleTableService;
import cn.hopever.platform.user.service.RoleTableService;
import cn.hopever.platform.user.service.UserTableService;
import cn.hopever.platform.user.vo.UserVo;
import cn.hopever.platform.user.vo.UserVoAssembler;
import cn.hopever.platform.user.vo.UserVoTemp;
import cn.hopever.platform.utils.moji.MojiUtils;
import cn.hopever.platform.utils.properties.CommonProperties;
import cn.hopever.platform.utils.test.PrincipalSample;
import cn.hopever.platform.utils.web.SelectOption;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Donghui Huo on 2016/8/29.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/user", produces = "application/json")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

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

    //@PreAuthorize("hasRole('ROLE_super_admin') or hasRole('ROLE_admin')")
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestBody TableParameters body, Principal principal) {
        principal = new PrincipalSample("admin");
        Page<UserTable> list = userTableService.getList(body, principal);
        Map<String, Object> map = new HashMap<>();
        List<HashMap<String, Object>> listReturn = null;
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
                listTmp.add(ut.isEnabled());
                listTmp.add(ut.getCreateUser() == null ? null : ut.getCreateUser().getUsername());
                listTmp.add(ut.getCreatedDate() != null ? ut.getCreatedDate().getTime() : null);
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
        return map;
    }

    // @PreAuthorize("hasRole('ROLE_super_admin') or hasRole('ROLE_admin')")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key, Principal principal) {
        principal = new PrincipalSample("admin");
        userTableService.delete(key, principal);
    }

    //@PreAuthorize("hasRole('ROLE_super_admin') or hasRole('ROLE_admin')")
    @RequestMapping(value = "/info", method = {RequestMethod.GET})
    public UserVo info(@RequestParam Long key, Principal principal) {
        principal = new PrincipalSample("admin");
        return userTableService.info(key, principal);
    }

    //@PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/", ""}, method = {RequestMethod.GET})
    public UserVo info(Principal principal) {
        principal = new PrincipalSample("admin");
        return userTableService.info(null, principal);
    }

    //@PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/personal/update", method = {RequestMethod.POST})
    public VueResults.Result updatePersonalUser(@RequestPart(required = true) UserVo userVo, @RequestPart("photo") MultipartFile[] files, Principal principal) {
        principal = new PrincipalSample("admin");
        return userTableService.updatePersonalUser(userVo, files, principal);
    }


    // @PreAuthorize("hasRole('ROLE_super_admin') or hasRole('ROLE_admin')")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result updateUser(@RequestPart(required = true) UserVo userVo, @RequestPart("photo") MultipartFile[] files, Principal principal) {
        principal = new PrincipalSample("admin");
        return userTableService.updateUser(userVo, files, principal);
    }

    //@PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result saveUser(@RequestParam(name = "photo", required = false) MultipartFile[] files, UserVoTemp userVo, Principal principal) {
        principal = new PrincipalSample("admin");

        return userTableService.saveUser(new UserVo(), files, principal);
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public VueResults.Result registerUser(@RequestPart(required = true) UserVo userVo, @RequestPart("photo") MultipartFile[] files, Principal principal) {
        return userTableService.registerUser(userVo, files);
    }


    @RequestMapping(value = "/form/rulechange", method = {RequestMethod.GET, RequestMethod.POST})
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body, Principal principal) {
        principal = new PrincipalSample("admin");
        Map mapReturn = new HashMap<>();
        if (body == null) {
            if (key == null) {
                // 新增
                mapReturn.put("authorities", userTableService.getRoleOptions(null, principal));
                mapReturn.put("clients", userTableService.getClientOptions(null, principal));
            } else {
                // 更新 - 考虑principal对其显示的限制
                mapReturn.put("authorities", userTableService.getRoleOptions(null, principal));
                List<SelectOption> clientsOptions = userTableService.getClientOptions(null, principal);
                mapReturn.put("clients", clientsOptions);
                mapReturn.put("modulesAuthorities", userTableService.getModulesAuthoritiesOptions(null, principal, clientsOptions, null));
            }
        } else {
            if (body.get("clients") != null) {
                mapReturn.put("modulesAuthorities", userTableService.getModulesAuthoritiesOptions(key, principal, null, (List) body.get("clients")));
            }
        }
        return mapReturn;
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
}