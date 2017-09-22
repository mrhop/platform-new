package cn.hopever.platform.cms.repository.impl;

import cn.hopever.platform.cms.domain.ArticleTable;
import cn.hopever.platform.cms.domain.ArticleTagTable;
import cn.hopever.platform.cms.repository.CustomArticleTableRepository;
import org.apache.commons.lang.StringEscapeUtils;
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
 * Created by Donghui Huo on 2016/11/30.
 */
@Repository("customArticleTableRepository")
public class CustomArticleTableRepositoryImpl extends SimpleJpaRepository<ArticleTable, Long> implements CustomArticleTableRepository {

    private final EntityManager entityManager;

    public CustomArticleTableRepositoryImpl(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(ArticleTable.class, entityManager), entityManager);
        this.entityManager = entityManager;
    }


    @Override
    public Page<ArticleTable> findByFilters(Map<String, Object> mapFilter, Pageable pageable) {
        return super.findAll(filterConditions1(mapFilter), pageable);
    }

    private Specification<ArticleTable> filterConditions1(Map<String, Object> mapFilter) {
        return new Specification<ArticleTable>() {
            public Predicate toPredicate(Root<ArticleTable> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                query.distinct(true);
                Predicate predicateReturn = null;
                if (mapFilter != null && mapFilter.size() > 0) {
                    for (String key : mapFilter.keySet()) {
                        if ("websiteTable".equals(key) || "published".equals(key) || "type".equals(key)) {
                            if (predicateReturn != null) {
                                predicateReturn = builder.and(predicateReturn, builder.equal(root.get(key), mapFilter.get(key)));

                            } else {
                                predicateReturn = builder.equal(root.get(key), mapFilter.get(key));
                            }
                        } else if ("articleTagId".equals(key)) {
                            Join<ArticleTable, ArticleTagTable> takeJoin = root.join("articleTagTables");
                            Expression<Long> articleTagId = takeJoin.get("id");
                            if (predicateReturn != null) {
                                predicateReturn = builder.and(predicateReturn, builder.equal(articleTagId, mapFilter.get(key)));
                            } else {
                                predicateReturn = builder.equal(articleTagId, mapFilter.get(key));
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
