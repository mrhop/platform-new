package cn.hopever.platform.user.repository.impl;

import cn.hopever.platform.user.domain.ResourceScopeTable;
import cn.hopever.platform.user.repository.CustomResourceScopeTableRepository;
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
 * Created by Donghui Huo on 2017/8/18.
 */
@Repository("customResourceScopeTableRepositor")
public class CustomResourceScopeTableRepositoryImpl extends SimpleJpaRepository<ResourceScopeTable, Long> implements CustomResourceScopeTableRepository {

    private final EntityManager entityManager;

    public CustomResourceScopeTableRepositoryImpl(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(ResourceScopeTable.class, entityManager), entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Page<ResourceScopeTable> findByFilters(Map<String, Object> mapFilter, Pageable pageable) {
        return super.findAll(filterConditions1(mapFilter), pageable);
    }

    private Specification<ResourceScopeTable> filterConditions1(Map<String, Object> mapFilter) {
        return new Specification<ResourceScopeTable>() {
            public Predicate toPredicate(Root<ResourceScopeTable> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                Predicate predicateReturn = builder.notEqual(root.get("scopeId"), "internal_client");
                predicateReturn = builder.and(predicateReturn,builder.notEqual(root.get("scopeId"), "outer_client"));
                query.distinct(true);
                if (mapFilter != null && mapFilter.size() > 0) {
                    for (String key : mapFilter.keySet()) {
                        if (mapFilter.get(key) != null) {
                            predicateReturn = builder.and(predicateReturn, builder.like(root.get(key), "%" + mapFilter.get(key) + "%"));
                        }
                    }
                }
                return predicateReturn;
            }
        };
    }
}
