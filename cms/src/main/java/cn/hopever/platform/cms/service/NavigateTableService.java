package cn.hopever.platform.cms.service;

import cn.hopever.platform.cms.vo.NavigateVo;
import cn.hopever.platform.utils.web.GenericService;
import cn.hopever.platform.utils.web.SelectOption;
import cn.hopever.platform.utils.web.TreeOption;

import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/31.
 * 参照user的module表结构
 */
public interface NavigateTableService  extends GenericService<NavigateVo> {

    public List<TreeOption> getParentsOptions(Long websiteId, Long id);

    public List<SelectOption> getBeforeOptions(Long parentId, Long websiteId, Long id);
}
