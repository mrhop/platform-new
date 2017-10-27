package cn.hopever.platform.crm.repository.impl;

import cn.hopever.platform.crm.domain.ClientRelatedUserTable;
import cn.hopever.platform.crm.domain.ClientTable;
import cn.hopever.platform.crm.domain.RelatedUserTable;
import cn.hopever.platform.crm.repository.CustomClientTableRepository;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Donghui Huo on 2016/11/30.
 */
@Repository("customClientTableRepository")
public class CustomClientTableRepositoryImpl extends SimpleJpaRepository<ClientTable, Long> implements CustomClientTableRepository {

    private final EntityManager entityManager;

    public CustomClientTableRepositoryImpl(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(ClientTable.class, entityManager), entityManager);
        this.entityManager = entityManager;
    }


    @Override
    public Page<ClientTable> findByFilters(Map<String, Object> mapFilter, Pageable pageable) {
        return super.findAll(filterConditions1(mapFilter), pageable);
    }

    private Specification<ClientTable> filterConditions1(Map<String, Object> mapFilter) {
        return new Specification<ClientTable>() {
            public Predicate toPredicate(Root<ClientTable> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                query.distinct(true);
                Predicate predicateReturn = null;
                if (mapFilter != null && mapFilter.size() > 0) {
                    for (String key : mapFilter.keySet()) {
                        if ("clientOriginTable".equals(key) || "clientLevelTable".equals(key) || "countryTable".equals(key) || "createdUser".equals(key) || "traded".equals(key)) {
                            if (predicateReturn != null) {
                                predicateReturn = builder.and(predicateReturn, builder.equal(root.get(key), mapFilter.get(key)));

                            } else {
                                predicateReturn = builder.equal(root.get(key), mapFilter.get(key));
                            }
                        } else if ("relatedUserTable".equals(key)) {
                            Join<ClientTable, ClientRelatedUserTable> takeJoin = root.join("clientRelatedUserTables");
                            Expression<RelatedUserTable> relatedUserTableExpression = takeJoin.get("relatedUserTable");
                            if (predicateReturn != null) {
                                predicateReturn = builder.and(predicateReturn, builder.equal(relatedUserTableExpression, mapFilter.get(key)));
                            } else {
                                predicateReturn = builder.equal(relatedUserTableExpression, mapFilter.get(key));
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

    @Override
    public List<ClientTable> findByRelatedUserTable(RelatedUserTable relatedUserTable) {
        return super.findAll(filterConditions2(relatedUserTable), new Sort(Sort.Direction.ASC, "orderAmount", "id"));
    }

    private Specification<ClientTable> filterConditions2(RelatedUserTable relatedUserTable) {
        return new Specification<ClientTable>() {
            public Predicate toPredicate(Root<ClientTable> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                query.distinct(true);
                Join<ClientTable, ClientRelatedUserTable> takeJoin = root.join("clientRelatedUserTables");
                Expression<RelatedUserTable> relatedUserTableExpression = takeJoin.get("relatedUserTable");
                return builder.equal(relatedUserTableExpression, relatedUserTable);
            }
        };
    }

    @Override
    public List<Object[]> findCountClientByCountry(Date beginDate, Date endDate) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select country.id, count(c.id) as total_quantity from platform_crm_client c inner join platform_crm_country country on c.country_id = country.id ");
        if (beginDate != null) {
            stringBuilder.append(" and c.created_date >= :beginDate ");
        }
        if (endDate != null) {
            stringBuilder.append(" and c.created_date <= :endDate ");
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
    public List<Object[]> findOrderAmountFromClient(Date beginDate, Date endDate) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select c.name,c.order_amount from platform_crm_client c where 1=1 ");
        if (beginDate != null) {
            stringBuilder.append(" and c.created_date >= :beginDate ");
        }
        if (endDate != null) {
            stringBuilder.append(" and c.created_date <= :endDate ");
        }
        stringBuilder.append(" order by c.order_amount desc ");

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
    public List<Object[]> findClientFromCreatedUser(Date beginDate, Date endDate, Long clientLevelId, Long userId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select c.created_date,count(c.id) from platform_crm_client c where 1=1 ");
        if (userId != null) {
            stringBuilder.append(" and c.created_user_id = :userId ");
        }
        if (clientLevelId != null) {
            stringBuilder.append(" and c.client_level_id = :clientLevelId ");
        }
        if (beginDate != null) {
            stringBuilder.append(" and c.created_date >= :beginDate ");
        }
        if (endDate != null) {
            stringBuilder.append(" and c.created_date <= :endDate ");
        }
        stringBuilder.append(" group by c.created_date order by c.created_date asc ");

        Query query = this.entityManager.createNativeQuery(stringBuilder.toString());
        if (userId != null) {
            query.setParameter("userId", userId);
        }
        if (clientLevelId != null) {
            query.setParameter("clientLevelId", clientLevelId);
        }
        if (beginDate != null) {
            query.setParameter("beginDate", beginDate);
        }
        if (endDate != null) {
            query.setParameter("endDate", endDate);
        }
        return query.getResultList();
    }


}
