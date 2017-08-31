package cn.hopever.platform.cms.vo;

import cn.hopever.platform.cms.domain.ArticleTable;
import cn.hopever.platform.cms.domain.ArticleTagTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class ArticleVoAssembler implements GenericVoAssembler<ArticleVo, ArticleTable> {

    @Override
    public ArticleVo toResource(ArticleTable articleTable) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyNotNullProperties(articleTable, articleVo);
        articleVo.setCreatedDate(articleTable.getCreatedDate().getTime());
        if (articleTable.getPublishDate() != null) {
            articleVo.setPublishDate(articleTable.getPublishDate().getTime());
        }
        if (articleTable.getArticleTagTables() != null) {
            List<Long> articleTagTables = new ArrayList<>();
            List<String> articleTagTablesStr = new ArrayList<>();
            for (ArticleTagTable articleTagTable : articleTable.getArticleTagTables()) {
                articleTagTables.add(articleTagTable.getId());
                articleTagTablesStr.add(articleTagTable.getName());
            }
            articleVo.setArticleTags(articleTagTables);
            articleVo.setArticleTagsStr(articleTagTablesStr.toString());
        }
        articleVo.setWebsiteId(articleTable.getWebsiteTable().getId());
        articleVo.setWebsiteName(articleTable.getWebsiteTable().getName());
        articleVo.setCreatedDate(articleTable.getCreatedDate().getTime());
        return articleVo;
    }

    @Override
    public ArticleTable toDomain(ArticleVo articleVo, ArticleTable articleTable) {
        BeanUtils.copyNotNullProperties(articleVo, articleTable);
        if (articleVo.getPublishDate() != null) {
            articleTable.setPublishDate(new Date(articleVo.getPublishDate()));
        }
        return articleTable;
    }

}
