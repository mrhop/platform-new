package cn.hopever.platform.cms.service;

import cn.hopever.platform.cms.vo.ArticleVo;
import cn.hopever.platform.utils.web.GenericService;
import cn.hopever.platform.utils.web.VueResults;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public interface ArticleTableService extends GenericService<ArticleVo> {

    public VueResults.Result updatePublished(Long id, boolean published);

}
