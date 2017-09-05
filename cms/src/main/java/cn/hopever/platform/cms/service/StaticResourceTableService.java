package cn.hopever.platform.cms.service;

import cn.hopever.platform.cms.vo.StaticResourceVo;
import cn.hopever.platform.utils.web.GenericService;
import cn.hopever.platform.utils.web.SelectOption;

import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public interface StaticResourceTableService extends GenericService<StaticResourceVo> {
    public List<SelectOption> getBeforeOptions(Long scopeId, Long id, String type);
}
