package cn.hopever.platform.cms.service;

import cn.hopever.platform.cms.vo.ArticleVo;
import cn.hopever.platform.utils.web.GenericService;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.data.domain.Page;

import java.security.Principal;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public interface ArticleTableService extends GenericService<ArticleVo> {

    public VueResults.Result updatePublished(Long id, boolean published);

    public VueResults.Result saveNews(ArticleVo articleVo, Principal principal);

    public VueResults.Result saveEvent(ArticleVo articleVo, Principal principal);

    public VueResults.Result updateNews(ArticleVo articleVo, Principal principal);

    public VueResults.Result updateEvent(ArticleVo articleVo, Principal principal);

    public Page<ArticleVo> getNewsList(TableParameters body, Principal principal);

    public Page<ArticleVo> getEventList(TableParameters body, Principal principal);

    public VueResults.Result updatePublished(Long id, boolean published, Principal principal);

}
