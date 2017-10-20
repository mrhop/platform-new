package cn.hopever.platform.crm.repository;

import cn.hopever.platform.crm.domain.ClientLevelTable;
import cn.hopever.platform.crm.domain.OrderDiscountTable;
import cn.hopever.platform.utils.web.GenericRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface CustomOrderDiscountTableRepository extends GenericRepository<OrderDiscountTable> {

    public Page<OrderDiscountTable> findByFilters(Map<String, Object> mapFilter, Pageable pageable);

    public OrderDiscountTable findByFilters(ClientLevelTable clientLevelTable, float price, String type);

}
