package cn.hopever.platform.crm.repository;

import cn.hopever.platform.crm.domain.OrderTable;
import cn.hopever.platform.utils.web.GenericRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface CustomOrderTableRepository extends GenericRepository<OrderTable> {

    public Page<OrderTable> findByFilters(Map<String, Object> mapFilter, Pageable pageable);

    public List<Object[]> findCountOrderByCountry(Date beginDate, Date endDate);

    public List<Object[]> findOrderAmountFromUser(Date beginDate, Date endDate);

    public List<Object[]> findOrderFromClient(Date beginDate, Date endDate, Long clientId);

    public List<Object[]> findOrderFromCreatedUser(Date beginDate, Date endDate, Long userId);

}
