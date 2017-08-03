package cn.hopever.platform.user.vo;

import cn.hopever.platform.user.domain.ClientTable;
import cn.hopever.platform.user.domain.ModuleRoleTable;
import cn.hopever.platform.user.domain.RoleTable;
import cn.hopever.platform.user.domain.UserTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class UserVoAssembler {

    public UserVo toResource(UserTable userTable) {
        UserVo resource = new UserVo();
        BeanUtils.copyNotNullProperties(userTable, resource, "password");
        resource.setEnabledStr(userTable.isEnabled() ? "是" : "否");
        if (userTable.getClients() != null && userTable.getClients().size() > 0) {
            List<Long> setCr = new ArrayList<>();
            List<String> setCv = new ArrayList<>();
            for (ClientTable ct : userTable.getClients()) {
                setCr.add(ct.getId());
                setCv.add(ct.getClientName());
            }
            resource.setClients(setCr);
            resource.setClientsStr(setCv.toString());
        }
        if (userTable.getAuthorities() != null && userTable.getAuthorities().size() > 0) {
            for (RoleTable rt : userTable.getAuthorities()) {
                if (rt.getLevel() < 3) {
                    resource.setAuthorities(rt.getId());
                    resource.setAuthoritiesStr(rt.getName());
                    break;
                }
            }
        }
        if (userTable.getModulesAuthorities() != null && userTable.getModulesAuthorities().size() > 0) {
            List<Long> setMrr = new ArrayList<>();
            List<String> setMrv = new ArrayList<>();
            for (ModuleRoleTable mrt : userTable.getModulesAuthorities()) {
                setMrr.add(mrt.getId());
                setMrv.add(mrt.getName());
            }
            resource.setModulesAuthorities(setMrr);
            resource.setModulesAuthoritiesStr(setMrv.toString());
        }
        if (userTable.getLimitedDate() != null) {
            resource.setLimitedDate(userTable.getLimitedDate().getTime());
        }
        return resource;
    }

    public UserTable toDomain(UserVo userVo, UserTable userTable) {
        BeanUtils.copyNotNullProperties(userVo, userTable, "password", "photo");
        if (userVo.getLimitedDate() != null) {
            userTable.setLimitedDate(new Date(userVo.getLimitedDate()));
        }
        return userTable;
    }

    public List<UserVo> toResources(Iterable<UserTable> userTables) {
        List<UserVo> returnList = new ArrayList<>();
        for (UserTable ut : userTables) {
            returnList.add(this.toResource(ut));
        }
        return returnList;
    }
}
