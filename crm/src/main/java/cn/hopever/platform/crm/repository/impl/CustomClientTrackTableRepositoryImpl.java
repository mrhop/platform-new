package cn.hopever.platform.crm.repository.impl;

import cn.hopever.platform.crm.domain.ClientTrackTable;
import cn.hopever.platform.crm.repository.CustomClientTrackTableRepository;
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
@Repository("customClientTrackTableRepository")
public class CustomClientTrackTableRepositoryImpl extends SimpleJpaRepository<ClientTrackTable, Long> implements CustomClientTrackTableRepository {

    private final EntityManager entityManager;

    public CustomClientTrackTableRepositoryImpl(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(ClientTrackTable.class, entityManager), entityManager);
        this.entityManager = entityManager;
    }


    @Override
    public Page<ClientTrackTable> findByFilters(Map<String, Object> mapFilter, Pageable pageable) {
        return super.findAll(filterConditions1(mapFilter), pageable);
    }

    @Override
    public List<Object[]> findClientTrackFromTrackUser(Date beginDate, Date endDate, Long clientId, Long userId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select c.track_date,count(c.id) from platform_crm_client_track c where 1=1 ");
        if (userId != null) {
            stringBuilder.append(" and c.related_user_id = :userId ");
        }
        if (clientId != null) {
            stringBuilder.append(" and c.client_id = :clientId ");
        }
        if (beginDate != null) {
            stringBuilder.append(" and c.track_date >= :beginDate ");
        }
        if (endDate != null) {
            stringBuilder.append(" and c.track_date <= :endDate ");
        }
        stringBuilder.append(" group by c.track_date order by c.track_date asc ");

        Query query = this.entityManager.createNativeQuery(stringBuilder.toString());
        if (userId != null) {
            query.setParameter("userId", userId);
        }
        if (clientId != null) {
            query.setParameter("clientId", clientId);
        }
        if (beginDate != null) {
            query.setParameter("beginDate", beginDate);
        }
        if (endDate != null) {
            query.setParameter("endDate", endDate);
        }
        return query.getResultList();
    }

    private Specification<ClientTrackTable> filterConditions1(Map<String, Object> mapFilter) {
        return new Specification<ClientTrackTable>() {
            public Predicate toPredicate(Root<ClientTrackTable> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                query.distinct(true);
                Predicate predicateReturn = null;
                if (mapFilter != null && mapFilter.size() > 0) {
                    for (String key : mapFilter.keySet()) {
                        if (predicateReturn != null) {
                            predicateReturn = builder.and(predicateReturn, builder.equal(root.get(key), mapFilter.get(key)));
                        } else {
                            predicateReturn = builder.equal(root.get(key), mapFilter.get(key));
                        }
                    }
                }
                return predicateReturn;
            }
        };
    }

}
