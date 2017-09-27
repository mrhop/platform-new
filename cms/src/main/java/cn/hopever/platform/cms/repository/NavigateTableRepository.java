package cn.hopever.platform.cms.repository;

import cn.hopever.platform.cms.domain.NavigateTable;
import cn.hopever.platform.cms.domain.WebsiteTable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface NavigateTableRepository extends PagingAndSortingRepository<NavigateTable, Long> {

    public NavigateTable findOneByBeforeNavigate(NavigateTable navigateTable);

    public NavigateTable findTopByParentAndWebsiteTableOrderByNavigateOrderDesc(NavigateTable parent, WebsiteTable websiteTable);

    public List<NavigateTable> findByWebsiteTableAndParentIsNullOrderByNavigateOrderAsc(WebsiteTable websiteTable);

    public List<NavigateTable> findByWebsiteTableAndParentIsNullAndIdNotOrderByNavigateOrderAsc(WebsiteTable websiteTable, Long id);

    public List<NavigateTable> findByParentOrderByNavigateOrderAsc(NavigateTable parent);

    public List<NavigateTable> findByParentAndIdNotOrderByNavigateOrderAsc(NavigateTable parent, Long id);

    @Modifying
    @Query("update NavigateTable a set a.activated = true where a.id = ?1")
    int activateArticle(Long id);

    @Modifying
    @Query("update NavigateTable a set a.activated = false where a.id = ?1")
    int unActivateArticle(Long id);

}
