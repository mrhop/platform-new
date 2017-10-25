package cn.hopever.platform.user.repository.impl;

import cn.hopever.platform.user.domain.ClientTable;
import cn.hopever.platform.user.domain.RoleTable;
import cn.hopever.platform.user.domain.UserTable;
import cn.hopever.platform.user.repository.CustomUserTableRepository;
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
 * Created by Donghui Huo on 2016/11/2.
 */
@Repository("customUserTableRepository")
public class CustomUserTableRepositoryImpl extends SimpleJpaRepository<UserTable, Long> implements CustomUserTableRepository {
    private final EntityManager entityManager;

    public CustomUserTableRepositoryImpl(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(UserTable.class, entityManager), entityManager);
        this.entityManager = entityManager;
    }


    @Override
    public Page<UserTable> findByUsernameNotAndFilters(String username, Map mapFilter, Pageable pageable) {
        //采用Specification的方式整合条件处理，并调用super的getall的方式
        return super.findAll(filterConditions1(username, mapFilter), pageable);
    }


    private Specification<UserTable> filterConditions1(String username, Map<String, Object> mapFilter) {
        return new Specification<UserTable>() {
            public Predicate toPredicate(Root<UserTable> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                Predicate predicateReturn = builder.notEqual(root.get("username"), username);
                query.distinct(true);
                if (mapFilter != null) {
                    for (String key : mapFilter.keySet()) {
                        if (mapFilter.get(key) != null) {
                            if (key.equals("enabled")) {
                                predicateReturn = builder.and(predicateReturn, builder.equal(root.get(key), mapFilter.get(key)));
                            } else {
                                predicateReturn = builder.and(predicateReturn, builder.like(root.get(key), "%" + StringEscapeUtils.escapeSql(mapFilter.get(key).toString()) + "%"));
                            }
                        }
                    }
                }
                return predicateReturn;
            }
        };
    }


    @Override
    public Page<UserTable> findByCreateUserAndAuthoritiesInAndClientsInAndFilters(UserTable userTable, String authority1, String authority2, Map<String, Object> mapFilter, Pageable pageable) {
        return super.findAll(filterConditions2(userTable, authority1, authority2, mapFilter), pageable);
    }

    private Specification<UserTable> filterConditions2(UserTable userTable, String authority1, String authority2, Map<String, Object> mapFilter) {
        return new Specification<UserTable>() {
            public Predicate toPredicate(Root<UserTable> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                Predicate predicateReturn = null;
                query.distinct(true);
                if (mapFilter != null && mapFilter.size() > 0) {
                    for (String key : mapFilter.keySet()) {
                        if (mapFilter.get(key) != null) {
                            if (key.equals("enabled")) {
                                if (predicateReturn == null) {
                                    predicateReturn = builder.equal(root.get(key), mapFilter.get(key));
                                } else {
                                    predicateReturn = builder.and(predicateReturn, builder.equal(root.get(key), mapFilter.get(key)));
                                }
                            } else if (mapFilter.get(key) != null && key.equals("authorityIds")) {
                                Join<UserTable, RoleTable> takeJoin = root.join("authorities");
                                Expression<Long> roleId = takeJoin.get("id");
                                if (predicateReturn != null) {
                                    predicateReturn = builder.and(predicateReturn, roleId.in((List)mapFilter.get(key)));
                                } else {
                                    predicateReturn = roleId.in((List) mapFilter.get(key));
                                }
                            } else if (mapFilter.get(key) != null && key.equals("clientIds")) {
                                Path<ClientTable> group = root.<ClientTable>get("clients");
                                Join<UserTable, ClientTable> takeJoin = root.join("clients");
                                Expression<Long> clientId = takeJoin.get("id");
                                if (predicateReturn != null) {
                                    predicateReturn = builder.and(predicateReturn, clientId.in((List)mapFilter.get(key)));
                                } else {
                                    predicateReturn = clientId.in((List) mapFilter.get(key));
                                }
                            } else {
                                if (predicateReturn == null) {
                                    predicateReturn = builder.like(root.get(key), "%" + StringEscapeUtils.escapeSql(mapFilter.get(key).toString()) + "%");
                                } else {
                                    predicateReturn = builder.and(predicateReturn, builder.like(root.get(key), "%" + StringEscapeUtils.escapeSql(mapFilter.get(key).toString()) + "%"));
                                }
                            }
                        }
                    }
                }
                Join<UserTable, RoleTable> takeJoin = root.join("authorities");
                Expression<String> authority = takeJoin.get("authority");
                Predicate predicate1 = builder.equal(authority, authority1);
                Predicate predicate2 = builder.equal(authority, authority2);
                predicate1 = builder.or(builder.and(builder.equal(root.get("createUser"), userTable), predicate1), predicate2);
                if (predicateReturn == null) {
                    predicateReturn = predicate1;
                } else {
                    predicateReturn = builder.and(predicateReturn, predicate1);
                }
                return predicateReturn;
            }
        };
    }

    @Override
    public List<String> findByAuthorityAndClientId(String authority, String clientId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> cq = criteriaBuilder.createQuery(String.class);
        Root<UserTable> userTableRoot = cq.from(UserTable.class);
        Predicate predicate = filterConditions3(authority, clientId).toPredicate(userTableRoot, cq, criteriaBuilder);
        cq.select(userTableRoot.get("username")).where(predicate).distinct(true);
        return entityManager.createQuery(cq).getResultList();
    }

    private Specification<UserTable> filterConditions3(String authority, String clientId) {
        return new Specification<UserTable>() {
            public Predicate toPredicate(Root<UserTable> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                Predicate predicateReturn = null;
                query.distinct(true);
                Join<UserTable, RoleTable> takeJoin1 = root.join("authorities");
                Expression<String> authorityExpress = takeJoin1.get("authority");
                Predicate predicate1 = builder.equal(authorityExpress, authority);
                Join<UserTable, ClientTable> takeJoin2 = root.join("clients");
                Expression<String> clientIdExpress = takeJoin2.get("clientId");
                Predicate predicate2 = builder.equal(clientIdExpress, clientId);
                predicateReturn = builder.and(predicate1, predicate2);
                return predicateReturn;
            }
        };
    }


}
