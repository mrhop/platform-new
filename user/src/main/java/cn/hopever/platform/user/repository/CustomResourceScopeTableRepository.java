package cn.hopever.platform.user.repository;

import cn.hopever.platform.user.domain.ResourceScopeTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface CustomResourceScopeTableRepository {
    public Page<ResourceScopeTable> findByFilters(Map<String, Object> mapFilter, Pageable pageable);
}
