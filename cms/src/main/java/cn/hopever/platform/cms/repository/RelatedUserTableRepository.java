package cn.hopever.platform.cms.repository;

import cn.hopever.platform.cms.domain.RelatedUserTable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface RelatedUserTableRepository extends PagingAndSortingRepository<RelatedUserTable, Long> {
    public RelatedUserTable findOneByAccount(String account);
}
