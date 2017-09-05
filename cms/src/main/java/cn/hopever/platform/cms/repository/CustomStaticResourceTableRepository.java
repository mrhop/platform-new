package cn.hopever.platform.cms.repository;

import cn.hopever.platform.cms.domain.StaticResourceTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface CustomStaticResourceTableRepository {
    public Page<StaticResourceTable> findByFilters(Map<String, Object> mapFilter, Pageable pageable);
}
