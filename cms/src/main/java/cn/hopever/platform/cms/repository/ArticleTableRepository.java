package cn.hopever.platform.cms.repository;

import cn.hopever.platform.cms.domain.ArticleTable;
import cn.hopever.platform.cms.domain.WebsiteTable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface ArticleTableRepository extends PagingAndSortingRepository<ArticleTable, Long> {
    @Modifying
    @Query("update ArticleTable a set a.published = true,a.publishDate = ?1 where a.id = ?2")
    int publishArticle(Date publishDate, Long id);

    @Modifying
    @Query("update ArticleTable a set a.published = false,a.publishDate = null where a.id = ?1")
    int unpublishArticle(Long id);

    public List<ArticleTable> findByWebsiteTableAndType(WebsiteTable websiteTable, Short type);

}
