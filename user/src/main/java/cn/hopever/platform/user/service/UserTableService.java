package cn.hopever.platform.user.service;

import cn.hopever.platform.user.domain.UserTable;
import cn.hopever.platform.user.vo.UserVo;
import cn.hopever.platform.utils.web.SelectOption;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
//need to use this as the userdetailservice,then return the true things there
// then implict
// then password
// then client then consider about the different system to use,like cms crm,etc
public interface UserTableService extends UserDetailsService {
    public UserTable save(UserTable user);

    public UserTable getUserByUsername(String username);

    public UserTable getUserByEmail(String email);

    public UserTable getUserByPhone(String phone);

    public Iterable<UserTable> getList();

    public Page<UserTable> getListWithOutSelf(String username, Pageable pageable, Map<String, Object> filterMap);

    public Page<UserTable> getSubList(String username, Pageable pageable, Map<String, Object> filterMap);

    public List<String> getListByClientId(String clientId);

    public void delete(Long id);

    public UserTable get(Long id);

    // 复合 service
    public Page<UserTable> getList(TableParameters body, Principal principal);

    public void delete(Long id, Principal principal);

    public UserVo info(Long id, Principal principal);

    public VueResults.Result updatePersonalUser(UserVo userVo, MultipartFile[] files, Principal principal);

    public VueResults.Result updateUser(UserVo userVo, MultipartFile[] files, Principal principal);

    public VueResults.Result saveUser(UserVo userVo, MultipartFile[] files, Principal principal);

    public VueResults.Result registerUser(UserVo userVo, MultipartFile[] files);

    public List<SelectOption> getRoleOptions(Long id, Principal principal);

    public List<SelectOption> getClientOptions(Long id, Principal principal);

    public List<SelectOption> getModulesAuthoritiesOptions(Long id, Principal principal, List<SelectOption> clientOptions, List<Integer> clientIds);

    public VueResults.Result updateEnabled(Long id, boolean enabled);
}
