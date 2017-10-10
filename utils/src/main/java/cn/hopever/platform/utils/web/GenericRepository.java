package cn.hopever.platform.utils.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public interface GenericRepository<T> {

    public Page<T> findByFilters(Map<String, Object> mapFilter, Pageable pageable);


}
