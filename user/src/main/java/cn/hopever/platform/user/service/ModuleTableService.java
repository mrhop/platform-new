package cn.hopever.platform.user.service;

import cn.hopever.platform.user.domain.ModuleTable;
import cn.hopever.platform.user.vo.ModuleVo;
import cn.hopever.platform.utils.web.SelectOption;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.TreeOption;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface ModuleTableService {

    public Iterable<ModuleTable> getAll();

    public Page<ModuleVo> getList(TableParameters body);

    public ModuleVo getById(Long id);

    public VueResults.Result update(ModuleVo moduleVo);

    public VueResults.Result save(ModuleVo moduleVo);

    public void deleteById(Long id);

    public List<ModuleTable> getParentList();

    public List<SelectOption> getClientsOptions();

    public List<TreeOption> getParentsOptions(Long clientId, Long id);

    public List<SelectOption> getBeforeOptions(Long parentId, Long clientId, Long id);

    public List<SelectOption> getModuleRoleOptions(Long clientId);

    public List<TreeOption> getLeftMenu(Principal principal, String clientId);

    public List getFlatModule(Principal principal, String clientId);

}
