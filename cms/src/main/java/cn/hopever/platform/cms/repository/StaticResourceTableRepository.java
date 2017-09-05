package cn.hopever.platform.cms.repository;

import cn.hopever.platform.cms.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface StaticResourceTableRepository extends PagingAndSortingRepository<StaticResourceTable, Long> {

    public List<StaticResourceTable> findByThemeTableOrderByResourceOrderAsc(ThemeTable themeTable);

    public List<StaticResourceTable> findByWebsiteTableOrderByResourceOrderAsc(WebsiteTable websiteTable);

    public List<StaticResourceTable> findByArticleTableOrderByResourceOrderAsc(ArticleTable articleTable);

    public List<StaticResourceTable> findByThemeTableAndIdNotOrderByResourceOrderAsc(ThemeTable themeTable, Long id);

    public List<StaticResourceTable> findByWebsiteTableAndIdNotOrderByResourceOrderAsc(WebsiteTable websiteTable, Long id);

    public List<StaticResourceTable> findByArticleTableAndIdNotOrderByResourceOrderAsc(ArticleTable articleTable, Long id);

    public StaticResourceTable findOneByBeforeStaticResource(StaticResourceTable staticResourceTable);

    public StaticResourceTable findTopByThemeTableOrderByResourceOrderDesc(ThemeTable themeTable);

    public StaticResourceTable findTopByWebsiteTableOrderByResourceOrderDesc(WebsiteTable websiteTable);

    public StaticResourceTable findTopByArticleTableOrderByResourceOrderDesc(ArticleTable articleTable);

}
