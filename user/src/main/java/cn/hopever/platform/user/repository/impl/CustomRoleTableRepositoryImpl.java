package cn.hopever.platform.user.repository.impl;

import cn.hopever.platform.user.domain.RoleTable;
import cn.hopever.platform.user.repository.CustomRoleTableRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

/**
 * Created by Donghui Huo on 2016/11/2.
 */
@Repository("customRoleTableRepository")
public class CustomRoleTableRepositoryImpl extends SimpleJpaRepository<RoleTable, Long> implements CustomRoleTableRepository {

    private final EntityManager entityManager;

    public CustomRoleTableRepositoryImpl(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(RoleTable.class, entityManager), entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Page<RoleTable> findByFilters(Map<String, Object> mapFilter, Pageable pageable) {
        return super.findAll(filterConditions(mapFilter), pageable);
    }

    private Specification<RoleTable> filterConditions(Map<String, Object> mapFilter) {
        return new Specification<RoleTable>() {
            public Predicate toPredicate(Root<RoleTable> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                Predicate predicateReturn = null;
                query.distinct(true);
                if (mapFilter != null && mapFilter.size() > 0) {
                    for (String key : mapFilter.keySet()) {
                        if (mapFilter.get(key) != null) {
                            if (predicateReturn == null) {
                                if (key.equals("level")) {
                                    predicateReturn = builder.equal(root.get(key), mapFilter.get(key));
                                } else {
                                    predicateReturn = builder.like(root.get(key), "%" + mapFilter.get(key) + "%");
                                }
                            } else {
                                if (key.equals("level")) {
                                    predicateReturn = builder.and(predicateReturn, builder.equal(root.get(key), mapFilter.get(key)));
                                } else {
                                    predicateReturn = builder.and(predicateReturn, builder.like(root.get(key), "%" + mapFilter.get(key) + "%"));
                                }
                            }
                        }
                    }
                }
                return predicateReturn;
            }
        };
    }


}
