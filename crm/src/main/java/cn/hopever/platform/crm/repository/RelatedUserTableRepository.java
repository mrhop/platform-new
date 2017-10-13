package cn.hopever.platform.crm.repository;

import cn.hopever.platform.crm.domain.RelatedUserTable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface RelatedUserTableRepository extends PagingAndSortingRepository<RelatedUserTable, Long> {
    public RelatedUserTable findOneByAccount(String account);
}
