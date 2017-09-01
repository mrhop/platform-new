package cn.hopever.platform.cms.repository;

import cn.hopever.platform.cms.domain.ArticleTagTable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface ArticleTagTableRepository extends PagingAndSortingRepository<ArticleTagTable, Long> {
    public ArticleTagTable findOneByTagId(String tagId);
}
