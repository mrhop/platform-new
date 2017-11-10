package cn.hopever.platform.cms.service;

import cn.hopever.platform.cms.vo.TemplateVo;
import cn.hopever.platform.utils.web.GenericService;
import cn.hopever.platform.utils.web.SelectOption;

import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public interface TemplateTableService extends GenericService<TemplateVo> {
    public List<SelectOption> getOptionsByWebsiteId(Long websiteId);

    public void copy(Long id);

    public String preview(Long id,String originPath);

}
