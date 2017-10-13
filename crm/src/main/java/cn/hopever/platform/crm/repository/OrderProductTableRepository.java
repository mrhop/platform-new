package cn.hopever.platform.crm.repository;

import cn.hopever.platform.crm.domain.OrderProductTable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface OrderProductTableRepository extends PagingAndSortingRepository<OrderProductTable, Long> {

    @Modifying
    @Query("update OrderProductTable a set a.num = ?1 where a.id = ?2")
    int updateNum(float num,Long id);

}
