package cn.hopever.platform.crm.repository.impl;

import cn.hopever.platform.crm.domain.OrderDiscountTable;
import cn.hopever.platform.crm.repository.CustomOrderDiscountTableRepository;
import org.apache.commons.lang.StringEscapeUtils;
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
 * Created by Donghui Huo on 2016/11/30.
 */
@Repository("customOrderDiscountTableRepository")
public class CustomOrderDiscountTableRepositoryImpl extends SimpleJpaRepository<OrderDiscountTable, Long> implements CustomOrderDiscountTableRepository {

    private final EntityManager entityManager;

    public CustomOrderDiscountTableRepositoryImpl(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(OrderDiscountTable.class, entityManager), entityManager);
        this.entityManager = entityManager;
    }


    @Override
    public Page<OrderDiscountTable> findByFilters(Map<String, Object> mapFilter, Pageable pageable) {
        return super.findAll(filterConditions1(mapFilter), pageable);
    }

    private Specification<OrderDiscountTable> filterConditions1(Map<String, Object> mapFilter) {
        return new Specification<OrderDiscountTable>() {
            public Predicate toPredicate(Root<OrderDiscountTable> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                query.distinct(true);
                Predicate predicateReturn = null;

                if (mapFilter != null && mapFilter.size() > 0) {
                    for (String key : mapFilter.keySet()) {
                        if ("name".equals(key)) {
                            if (predicateReturn != null) {
                                predicateReturn = builder.and(predicateReturn, builder.like(root.get(key), "%" + StringEscapeUtils.escapeSql(mapFilter.get(key).toString()) + "%"));
                            } else {
                                predicateReturn = builder.like(root.get(key), "%" + StringEscapeUtils.escapeSql(mapFilter.get(key).toString()) + "%");
                            }
                        } else {
                            if (predicateReturn != null) {
                                predicateReturn = builder.and(predicateReturn, builder.equal(root.get(key), mapFilter.get(key)));
                            } else {
                                predicateReturn = builder.equal(root.get(key), mapFilter.get(key));
                            }
                        }
                    }
                }
                return predicateReturn;
            }
        };
    }

}
