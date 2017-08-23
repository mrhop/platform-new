package cn.hopever.platform.user.repository.impl;

import cn.hopever.platform.user.domain.ClientTable;
import cn.hopever.platform.user.domain.ModuleRoleTable;
import cn.hopever.platform.user.domain.ModuleTable;
import cn.hopever.platform.user.repository.CustomModuleTableRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
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
    public Page<ModuleTable> findByFilters(Map<String, Object> mapFilter, Pageable pageable, ClientTable userAdminClient) {
        return super.findAll(filterConditions1(mapFilter, userAdminClient), pageable);
    }

    //do this filter
    private Specification<ModuleTable> filterConditions1(Map<String, Object> mapFilter, ClientTable userAdminClient) {
        //实现这个
        return new Specification<ModuleTable>() {
            public Predicate toPredicate(Root<ModuleTable> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                Predicate predicateReturn = null;
                predicateReturn = builder.notEqual(root.get("client"), userAdminClient);
                //query.distinct(true);
                if (mapFilter != null && mapFilter.size() > 0) {
                    for (String key : mapFilter.keySet()) {
                        if (mapFilter.get(key) != null) {
                            if (key.equals("moduleName")) {
                                predicateReturn = builder.and(predicateReturn, builder.like(root.get(key), "%" + mapFilter.get(key) + "%"));
                            } else {
                                //需要针对filter来进行值得获取和填充
                                predicateReturn = builder.and(predicateReturn, builder.equal(root.get(key), mapFilter.get(key)));
                            }
                        }
                    }
                }
                return predicateReturn;
            }
        };
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
}
