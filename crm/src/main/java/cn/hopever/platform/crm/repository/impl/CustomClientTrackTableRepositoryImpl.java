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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
