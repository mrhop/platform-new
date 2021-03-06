package cn.hopever.platform.user.web.controller;

import cn.hopever.platform.user.domain.UserTable;
import cn.hopever.platform.user.service.UserTableService;
import cn.hopever.platform.user.vo.UserVo;
import cn.hopever.platform.utils.web.SelectOption;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping(value = "/user", produces = "application/json")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserTableService userTableService;

    @PreAuthorize("hasRole('ROLE_super_admin') or hasRole('ROLE_admin')")
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestBody TableParameters body, Principal principal) {
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
        map.put("sorts", body.getSorts());
        return map;
    }

    @PreAuthorize("hasRole('ROLE_super_admin') or hasRole('ROLE_admin')")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key, Principal principal) {
        userTableService.delete(key, principal);
    }

    @PreAuthorize("hasRole('ROLE_super_admin') or hasRole('ROLE_admin')")
    @RequestMapping(value = "/info", method = {RequestMethod.GET})
    public UserVo info(@RequestParam Long key, Principal principal) {
        return userTableService.info(key, principal);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/", ""}, method = {RequestMethod.GET})
    public UserVo info(Principal principal) {
        return userTableService.info(null, principal);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/personal/update", method = {RequestMethod.POST})
    public VueResults.Result updatePersonalUser(@RequestParam(name = "photoFiles", required = false) MultipartFile[] files, UserVo userVo, Principal principal) {
        return userTableService.updatePersonalUser(userVo, files, principal);
    }


    @PreAuthorize("hasRole('ROLE_super_admin') or hasRole('ROLE_admin')")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam(name = "key") Long key, @RequestParam(name = "photoFiles", required = false) MultipartFile[] files, UserVo userVo, Principal principal) {
        userVo.setId(key);
        return userTableService.updateUser(userVo, files, principal);
    }

    @PreAuthorize("hasRole('ROLE_super_admin') or hasRole('ROLE_admin')")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestParam(name = "photoFiles", required = false) MultipartFile[] files, UserVo userVo, Principal principal) {
        return userTableService.saveUser(userVo, files, principal);
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public VueResults.Result register(@RequestParam(name = "photoFiles", required = false) MultipartFile[] files, UserVo userVo, Principal principal) {
        return userTableService.registerUser(userVo, files);
    }


    @PreAuthorize("hasRole('ROLE_super_admin') or hasRole('ROLE_admin')")
    @RequestMapping(value = "/form/rulechange", method = {RequestMethod.GET, RequestMethod.POST})
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body, Principal principal) {
        Map mapReturn = new HashMap<>();
        if (body == null) {
            if (key == null) {
                // 新增
                mapReturn.put("authorities", userTableService.getRoleOptions(null, principal));
                mapReturn.put("clients", userTableService.getClientOptions(null, principal));
            } else {
                // 更新 - 考虑principal对其显示的限制
                mapReturn.put("authorities", userTableService.getRoleOptions(key, principal));
                List<SelectOption> clientsOptions = userTableService.getClientOptions(key, principal);
                mapReturn.put("clients", clientsOptions);
                mapReturn.put("modulesAuthorities", userTableService.getModulesAuthoritiesOptions(key, principal, clientsOptions, null));
            }
        } else {
            if (body.get("clients") != null) {
                mapReturn.put("modulesAuthorities", userTableService.getModulesAuthoritiesOptions(key, principal, null, (List) body.get("clients")));
            }
        }
        return mapReturn;
    }

    @PreAuthorize("hasRole('ROLE_super_admin') or hasRole('ROLE_admin')")
    @RequestMapping(value = "/setEnabled", method = {RequestMethod.GET})
    public VueResults.Result updateEnabled(@RequestParam Long key, @RequestParam Boolean enabled) {
        return userTableService.updateEnabled(key, enabled);
    }
}