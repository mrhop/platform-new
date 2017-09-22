package cn.hopever.platform.cms.vo;

import cn.hopever.platform.cms.domain.ArticleTagTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class ArticleTagVoAssembler implements GenericVoAssembler<ArticleTagVo, ArticleTagTable> {

    @Override
    public ArticleTagVo toResource(ArticleTagTable articleTagTable) {
        ArticleTagVo articleTagVo = new ArticleTagVo();
        BeanUtils.copyNotNullProperties(articleTagTable, articleTagVo);
        articleTagVo.setWebsiteId(articleTagTable.getWebsiteTable().getId());
        articleTagVo.setWebsiteName(articleTagTable.getWebsiteTable().getName());
        return articleTagVo;
    }

    @Override
    public ArticleTagTable toDomain(ArticleTagVo articleTagVo, ArticleTagTable articleTagTable) {
        BeanUtils.copyNotNullProperties(articleTagVo, articleTagTable,"tagId");
        return articleTagTable;
    }
}
