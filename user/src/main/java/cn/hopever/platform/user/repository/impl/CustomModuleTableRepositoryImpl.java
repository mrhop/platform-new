package cn.hopever.platform.user.repository.impl;

import cn.hopever.platform.user.domain.ModuleRoleTable;
import cn.hopever.platform.user.domain.ModuleTable;
import cn.hopever.platform.user.repository.CustomModuleTableRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;

/**
 * Created by Donghui Huo on 2016/12/2.
 */
@Repository("customModuleTableRepository")
public class CustomModuleTableRepositoryImpl extends SimpleJpaRepository<ModuleTable, Long> implements CustomModuleTableRepository {

    private final EntityManager entityManager;

    public CustomModuleTableRepositoryImpl(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(ModuleTable.class, entityManager), entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Page<ModuleTable> findByFilters(Map<String, Object> mapFilter, Pageable pageable) {
        return super.findAll(filterConditions2(mapFilter), pageable);
    }

    private Specification<ModuleTable> filterConditions2(Map<String, Object> mapFilter) {
        return new Specification<ModuleTable>() {
            public Predicate toPredicate(Root<ModuleTable> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                Predicate predicateReturn = null;
                if (mapFilter != null && mapFilter.size() > 0) {
                    for (String key : mapFilter.keySet()) {
                        if (mapFilter.get(key) != null && (key.equals("client") || key.equals("parent") || key.equals("activated"))) {
                            if (predicateReturn != null) {
                                predicateReturn = builder.and(predicateReturn, builder.equal(root.get(key), mapFilter.get(key)));
                            } else {
                                predicateReturn = builder.equal(root.get(key), mapFilter.get(key));
                            }
                        } else if (mapFilter.get(key) != null && key.equals("authorityId")) {
                            Join<ModuleTable, ModuleRoleTable> takeJoin = root.join("authorities");
                            Expression<Long> roleId = takeJoin.get("id");
                            if (predicateReturn != null) {
                                predicateReturn = builder.and(predicateReturn, builder.equal(roleId, mapFilter.get(key)));
                            } else {
                                predicateReturn = builder.equal(roleId, mapFilter.get(key));
                            }
                        } else if (mapFilter.get(key) != null) {
                            if (predicateReturn != null) {
                                predicateReturn = builder.and(predicateReturn, builder.like(root.get(key), "%" + mapFilter.get(key) + "%"));
                            } else {
                                predicateReturn = builder.like(root.get(key), "%" + mapFilter.get(key) + "%");
                            }
                        }
                    }
                }
                return predicateReturn;
            }
        };
    }

    @Override
    public List<ModuleTable> findByModuleRoles(List<Long> moduleRoleIds) {
        return super.findAll(filterConditions3(moduleRoleIds), new Sort(Sort.Direction.ASC, "moduleOrder"));
    }

    @Override
    public List<String> findModuleIdsByModuleRoles(List<Long> moduleRoleIds) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> cq = criteriaBuilder.createQuery(String.class);
        Root<ModuleTable> moduleTableRoot = cq.from(ModuleTable.class);
        Predicate predicate = filterConditions3(moduleRoleIds).toPredicate(moduleTableRoot, cq, criteriaBuilder);
        cq.select(moduleTableRoot.get("moduleId")).where(predicate).distinct(true);
        return entityManager.createQuery(cq).getResultList();
    }

    private Specification<ModuleTable> filterConditions3(List<Long> moduleRoleIds) {
        return new Specification<ModuleTable>() {
            public Predicate toPredicate(Root<ModuleTable> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                query.distinct(true);
                Join<ModuleTable, ModuleRoleTable> takeJoin = root.join("authorities");
                Expression<Long> roleId = takeJoin.get("id");
                Predicate predicateReturn = roleId.in(moduleRoleIds);
                return predicateReturn;
            }
        };
    }
}
