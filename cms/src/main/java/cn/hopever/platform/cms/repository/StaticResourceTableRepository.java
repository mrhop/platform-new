package cn.hopever.platform.cms.repository;

import cn.hopever.platform.cms.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface StaticResourceTableRepository extends PagingAndSortingRepository<StaticResourceTable, Long> {

    public List<StaticResourceTable> findByThemeTableAndTypeOrderByResourceOrderAsc(ThemeTable themeTable, String type);

    public List<StaticResourceTable> findByWebsiteTableAndTypeOrderByResourceOrderAsc(WebsiteTable websiteTable, String type);

    public List<StaticResourceTable> findByArticleTableAndTypeOrderByResourceOrderAsc(ArticleTable articleTable, String type);

    public List<StaticResourceTable> findByThemeTableAndTypeAndIdNotOrderByResourceOrderAsc(ThemeTable themeTable, String type, Long id);

    public List<StaticResourceTable> findByWebsiteTableAndTypeAndIdNotOrderByResourceOrderAsc(WebsiteTable websiteTable, String type, Long id);

    public List<StaticResourceTable> findByArticleTableAndTypeAndIdNotOrderByResourceOrderAsc(ArticleTable articleTable, String type, Long id);

    public StaticResourceTable findOneByBeforeStaticResource(StaticResourceTable staticResourceTable);

    public StaticResourceTable findOneByUrl(String url);

    public StaticResourceTable findTopByThemeTableAndTypeOrderByResourceOrderDesc(ThemeTable themeTable, String type);

    public StaticResourceTable findTopByWebsiteTableAndTypeOrderByResourceOrderDesc(WebsiteTable websiteTable, String type);

    public StaticResourceTable findTopByArticleTableAndTypeOrderByResourceOrderDesc(ArticleTable articleTable, String type);

}
