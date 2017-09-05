package cn.hopever.platform.cms.service;

import cn.hopever.platform.cms.vo.WebsiteVo;
import cn.hopever.platform.utils.web.GenericService;
import cn.hopever.platform.utils.web.SelectOption;

import java.security.Principal;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public interface WebsiteTableService extends GenericService<WebsiteVo> {
    public List<SelectOption> getOptions(Principal principal);
}
