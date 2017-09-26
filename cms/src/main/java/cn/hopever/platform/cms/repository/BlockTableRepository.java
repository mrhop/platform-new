package cn.hopever.platform.cms.repository;

import cn.hopever.platform.cms.domain.ArticleTable;
import cn.hopever.platform.cms.domain.BlockTable;
import cn.hopever.platform.cms.domain.TemplateTable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface BlockTableRepository extends PagingAndSortingRepository<BlockTable, Long> {
    public List<BlockTable> findByTemplateTableOrderByPositionAsc(TemplateTable templateTable);

    public List<BlockTable> findByArticleTableOrderByPositionAsc(ArticleTable articleTable);

    public List<BlockTable> findByArticleTableAndTemplateTableOrderByPositionAsc(ArticleTable articleTable, TemplateTable templateTable);
}
