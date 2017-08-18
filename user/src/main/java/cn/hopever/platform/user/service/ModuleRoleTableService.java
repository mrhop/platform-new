package cn.hopever.platform.user.service;

import cn.hopever.platform.user.domain.ModuleRoleTable;
import cn.hopever.platform.user.vo.ModuleRoleVo;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface ModuleRoleTableService {
    public List<ModuleRoleTable> getByClients(List<Object> clientIds);

    public List<ModuleRoleTable> getByIds(List<Long> ids);

    public List<ModuleRoleTable> getByUserId(Long userId);

    public Page<ModuleRoleVo> getList(TableParameters body);

    public ModuleRoleVo getById(Long id);

    public VueResults.Result update(ModuleRoleVo moduleRoleVo);

    public VueResults.Result save(ModuleRoleVo moduleRoleVo);

    public ModuleRoleTable getByAuthority(String authority);

    public ModuleRoleTable save(ModuleRoleTable moduleRoleTable);

    public void deleteById(Long id);

}
