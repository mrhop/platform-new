package cn.hopever.platform.crm.repository.impl;

import cn.hopever.platform.crm.domain.OrderTable;
import cn.hopever.platform.crm.repository.CustomOrderTableRepository;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Donghui Huo on 2016/11/30.
 */
@Repository("customOrderTableRepository")
public class CustomOrderTableRepositoryImpl extends SimpleJpaRepository<OrderTable, Long> implements CustomOrderTableRepository {

    private final EntityManager entityManager;

    public CustomOrderTableRepositoryImpl(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(OrderTable.class, entityManager), entityManager);
        this.entityManager = entityManager;
    }


    @Override
    public Page<OrderTable> findByFilters(Map<String, Object> mapFilter, Pageable pageable) {
        return super.findAll(filterConditions1(mapFilter), pageable);
    }

    @Override
    public List<Object[]> findCountOrderByCountry(Date beginDate, Date endDate) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select country.id, sum(o.sale_price) as total_quantity, count(o.id) from platform_crm_order o inner join platform_crm_country country on o.country_id = country.id ");
        if (beginDate != null) {
            stringBuilder.append(" and o.finished_date >= :beginDate ");
        }
        if (endDate != null) {
            stringBuilder.append(" and o.finished_date <= :endDate ");
        }
        stringBuilder.append(" group by country.id order by total_quantity desc ");

        Query query = this.entityManager.createNativeQuery(stringBuilder.toString());
        if (beginDate != null) {
            query.setParameter("beginDate", beginDate);
        }
        if (endDate != null) {
            query.setParameter("endDate", endDate);
        }
        return query.getResultList();
    }

    @Override
    public List<Object[]> findOrderAmountFromUser(Date beginDate, Date endDate) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select user.account, sum(o.sale_price) as total_quantity from platform_crm_order o inner join platform_crm_related_user user on o.created_user_id = user.id ");
        if (beginDate != null) {
            stringBuilder.append(" and o.finished_date >= :beginDate ");
        }
        if (endDate != null) {
            stringBuilder.append(" and o.finished_date <= :endDate ");
        }
        stringBuilder.append(" group by user.id order by total_quantity desc ");

        Query query = this.entityManager.createNativeQuery(stringBuilder.toString());
        if (beginDate != null) {
            query.setParameter("beginDate", beginDate);
        }
        if (endDate != null) {
            query.setParameter("endDate", endDate);
        }
        return query.getResultList();
    }

    @Override
    public List<Object[]> findOrderFromClient(Date beginDate, Date endDate, Long clientId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select o.finished_date, sum(o.sale_price) as total_quantity,count(o.id) from platform_crm_order o where 1=1 ");
        if (clientId != null) {
            stringBuilder.append(" and o.client_id = :clientId ");
        }
        if (beginDate != null) {
            stringBuilder.append(" and o.finished_date >= :beginDate ");
        }
        if (endDate != null) {
            stringBuilder.append(" and o.finished_date <= :endDate ");
        }
        stringBuilder.append(" group by o.finished_date order by o.finished_date desc ");

        Query query = this.entityManager.createNativeQuery(stringBuilder.toString());
        if (beginDate != null) {
            query.setParameter("beginDate", beginDate);
        }
        if (endDate != null) {
            query.setParameter("endDate", endDate);
        }
        if (clientId != null) {
            query.setParameter("clientId", clientId);
        }
        return query.getResultList();
    }

    @Override
    public List<Object[]> findOrderFromCreatedUser(Date beginDate, Date endDate, Long userId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select o.finished_date, sum(o.sale_price) as total_quantity,count(o.id) from platform_crm_order o where 1=1 ");
        if (userId != null) {
            stringBuilder.append(" and o.created_user_id = :userId ");
        }
        if (beginDate != null) {
            stringBuilder.append(" and o.finished_date >= :beginDate ");
        }
        if (endDate != null) {
            stringBuilder.append(" and o.finished_date <= :endDate ");
        }
        stringBuilder.append(" group by o.finished_date order by o.finished_date desc ");

        Query query = this.entityManager.createNativeQuery(stringBuilder.toString());
        if (beginDate != null) {
            query.setParameter("beginDate", beginDate);
        }
        if (endDate != null) {
            query.setParameter("endDate", endDate);
        }
        if (userId != null) {
            query.setParameter("userId", userId);
        }
        return query.getResultList();
    }

    private Specification<OrderTable> filterConditions1(Map<String, Object> mapFilter) {
        return new Specification<OrderTable>() {
            public Predicate toPredicate(Root<OrderTable> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                query.distinct(true);
                Predicate predicateReturn = null;
                if (mapFilter != null && mapFilter.size() > 0) {
                    for (String key : mapFilter.keySet()) {
                        if ("createdUser".equals(key) || "clientTable".equals(key) || "orderStatusTable".equals(key) || "countryTable".equals(key)) {
                            if (predicateReturn != null) {
                                predicateReturn = builder.and(predicateReturn, builder.equal(root.get(key), mapFilter.get(key)));

                            } else {
                                predicateReturn = builder.equal(root.get(key), mapFilter.get(key));
                            }
                        } else {
                            if (predicateReturn != null) {
                                predicateReturn = builder.and(predicateReturn, builder.like(root.get(key), "%" + StringEscapeUtils.escapeSql(mapFilter.get(key).toString()) + "%"));
                            } else {
                                predicateReturn = builder.like(root.get(key), "%" + StringEscapeUtils.escapeSql(mapFilter.get(key).toString()) + "%");
                            }
                        }
                    }
                }
                return predicateReturn;
            }
        };
    }

}
