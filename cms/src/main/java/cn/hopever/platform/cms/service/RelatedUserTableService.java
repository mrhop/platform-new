package cn.hopever.platform.cms.service;

import cn.hopever.platform.cms.vo.RelatedUserVo;
import cn.hopever.platform.utils.web.GenericService;
import cn.hopever.platform.utils.web.SelectOption;

import java.security.Principal;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public interface RelatedUserTableService extends GenericService<RelatedUserVo> {
    public List<SelectOption> getRelatedUserOptions(Principal principal);

    public RelatedUserVo getOneByAccount(String account);
}
