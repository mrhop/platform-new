package cn.hopever.platform.cms.repository;

import cn.hopever.platform.cms.domain.NavigateTable;
import cn.hopever.platform.cms.domain.WebsiteTable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface NavigateTableRepository extends PagingAndSortingRepository<NavigateTable, Long> {

    public NavigateTable findOneByBeforeNavigate(NavigateTable navigateTable);

    public NavigateTable findTopByParentAndWebsiteTableOrderByModuleOrderDesc(NavigateTable parent,WebsiteTable websiteTable);

}
