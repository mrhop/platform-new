package cn.hopever.platform.crm.repository;

import cn.hopever.platform.crm.domain.ClientTrackTable;
import cn.hopever.platform.utils.web.GenericRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface CustomClientTrackTableRepository extends GenericRepository<ClientTrackTable> {

    public Page<ClientTrackTable> findByFilters(Map<String, Object> mapFilter, Pageable pageable);

}
