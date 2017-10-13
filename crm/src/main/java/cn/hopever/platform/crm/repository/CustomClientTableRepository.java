package cn.hopever.platform.crm.repository;

import cn.hopever.platform.crm.domain.ClientTable;
import cn.hopever.platform.utils.web.GenericRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface CustomClientTableRepository extends GenericRepository<ClientTable> {
    public Page<ClientTable> findByFilters(Map<String, Object> mapFilter, Pageable pageable);

    public List<ClientTable> findByRelatedUserId(Long relatedUserId);
}
