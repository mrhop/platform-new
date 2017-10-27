package cn.hopever.platform.crm.repository;

import cn.hopever.platform.crm.domain.ClientTable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface ClientTableRepository extends PagingAndSortingRepository<ClientTable, Long> {
    @Query(value = "select o.name, sum(c.order_amount) as total_quantity from platform_crm_client c " +
            "inner join platform_crm_client_origin o " +
            "on c.client_origin_id = o.id " +
            "group by o.id " +
            "order by total_quantity desc", nativeQuery = true)
    List<Object[]> findGroupByOrigin();

    @Query(value = "select o.name, sum(c.order_amount) as total_quantity from platform_crm_client c " +
            "inner join platform_crm_client_origin o " +
            "on c.client_origin_id = o.id " +
            "group by o.id " +
            "HAVING sum(c.order_amount) > ?1 " +
            "order by total_quantity desc", nativeQuery = true)
    List<Object[]> findByOrderAmountGroupByOrigin(float orderAmount);


}
