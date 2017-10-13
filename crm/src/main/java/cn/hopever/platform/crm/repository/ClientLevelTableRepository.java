package cn.hopever.platform.crm.repository;

import cn.hopever.platform.crm.domain.ClientLevelTable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface ClientLevelTableRepository extends PagingAndSortingRepository<ClientLevelTable, Long> {

    public ClientLevelTable findOneByOrderAmountAndIdNot(float orderAmount, Long id);

    public ClientLevelTable findOneByOrderAmount(float orderAmount);

    public List<ClientLevelTable> findByOrderAmountIsNullOrderByIdAsc();

}
