package cn.hopever.platform.cms.repository;

import cn.hopever.platform.cms.domain.ArticleTagTable;
import cn.hopever.platform.cms.domain.WebsiteTable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface ArticleTagTableRepository extends PagingAndSortingRepository<ArticleTagTable, Long> {
    public ArticleTagTable findOneByTagId(String tagId);

    public List<ArticleTagTable> findByWebsiteTable(WebsiteTable websiteTable);

}
