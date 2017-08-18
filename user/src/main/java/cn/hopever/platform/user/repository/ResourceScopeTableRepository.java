package cn.hopever.platform.user.repository;

import cn.hopever.platform.user.domain.ResourceScopeTable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface ResourceScopeTableRepository extends PagingAndSortingRepository<ResourceScopeTable, Long> {
    public ResourceScopeTable findOneByScopeId(String scopeId);
}
