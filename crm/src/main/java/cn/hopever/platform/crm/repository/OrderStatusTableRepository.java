package cn.hopever.platform.crm.repository;

import cn.hopever.platform.crm.domain.OrderStatusTable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface OrderStatusTableRepository extends PagingAndSortingRepository<OrderStatusTable, Long> {
    public OrderStatusTable findOneByCode(String code);
}
