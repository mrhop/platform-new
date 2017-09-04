package cn.hopever.platform.cms.repository;

import cn.hopever.platform.cms.domain.MediaTable;
import cn.hopever.platform.cms.domain.MediaTagTable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface MediaTableRepository extends PagingAndSortingRepository<MediaTable, Long> {

    public List<MediaTable> findByMediaTagTableAndPublished(MediaTagTable mediaTagTable, boolean published);

    @Modifying
    @Query("update MediaTable a set a.published = true,a.publishDate = ?1 where a.id = ?2")
    int publishMedia(Date publishDate, Long id);

    @Modifying
    @Query("update MediaTable a set a.published = false,a.publishDate = null where a.id = ?1")
    int unpublishMedia(Long id);
}
