package cn.hopever.platform.crm.repository.impl;

import cn.hopever.platform.crm.domain.ClientRelatedUserTable;
import cn.hopever.platform.crm.domain.ClientTable;
import cn.hopever.platform.crm.domain.RelatedUserTable;
import cn.hopever.platform.crm.repository.CustomClientTableRepository;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
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
        return super.findAll(filterConditions2(relatedUserTable));
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

}
