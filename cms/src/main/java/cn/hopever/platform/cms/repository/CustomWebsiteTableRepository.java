package cn.hopever.platform.cms.repository;

import cn.hopever.platform.cms.domain.WebsiteTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface CustomWebsiteTableRepository {
    public Page<WebsiteTable> findByFilters(Map<String, Object> mapFilter, Pageable pageable);
}
