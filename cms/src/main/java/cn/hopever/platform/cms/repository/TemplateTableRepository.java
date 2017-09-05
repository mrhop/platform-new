package cn.hopever.platform.cms.repository;

import cn.hopever.platform.cms.domain.TemplateTable;
import cn.hopever.platform.cms.domain.ThemeTable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface TemplateTableRepository extends PagingAndSortingRepository<TemplateTable, Long> {

    public List<TemplateTable> findByThemeTable(ThemeTable themeTable);

}
