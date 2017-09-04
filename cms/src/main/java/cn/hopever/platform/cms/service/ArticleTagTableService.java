package cn.hopever.platform.cms.service;

import cn.hopever.platform.cms.vo.ArticleTagVo;
import cn.hopever.platform.utils.web.GenericService;
import cn.hopever.platform.utils.web.SelectOption;

import java.security.Principal;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public interface ArticleTagTableService extends GenericService<ArticleTagVo> {

    public List<SelectOption> getArticleTagOptions(Long websiteId, Principal principal);

}
