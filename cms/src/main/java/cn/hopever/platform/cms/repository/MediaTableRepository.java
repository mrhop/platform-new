package cn.hopever.platform.cms.repository;

import cn.hopever.platform.cms.domain.MediaTable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface MediaTableRepository extends PagingAndSortingRepository<MediaTable, Long> {

    @Modifying
    @Query("update MediaTable a set a.published = true,a.publishDate = ?1 where a.createUser = ?2")
    int publishArticle(Date publishDate, Long id);

    @Modifying
    @Query("update MediaTable a set a.published = false where a.createUser = ?1")
    int unpublishArticle(Long id);
}
