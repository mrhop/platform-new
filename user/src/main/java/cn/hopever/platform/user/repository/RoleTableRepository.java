package cn.hopever.platform.user.repository;

import cn.hopever.platform.user.domain.RoleTable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * Created by Donghui Huo on 2016/8/30.
 * 全部采用
 */
public interface RoleTableRepository extends PagingAndSortingRepository<RoleTable, Long> {
    // 需要测试权限增加的情况如何处理
    @QueryHints({
            @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "hoperver.user.role.findOneByAuthority")
    public RoleTable findOneByAuthority(String authority);

    public List<RoleTable> findByAuthorityLike(String authority, Pageable pageable);
}
