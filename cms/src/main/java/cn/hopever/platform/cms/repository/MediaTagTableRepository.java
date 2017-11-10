package cn.hopever.platform.cms.repository;

import cn.hopever.platform.cms.domain.MediaTagTable;
import cn.hopever.platform.cms.domain.ThemeTable;
import cn.hopever.platform.cms.domain.WebsiteTable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface MediaTagTableRepository extends PagingAndSortingRepository<MediaTagTable, Long> {
    public MediaTagTable findOneByTagId(String tagId);

    public List<MediaTagTable> findByWebsiteTable(WebsiteTable websiteTable);

    public List<MediaTagTable> findByThemeTable(ThemeTable themeTable);
}
