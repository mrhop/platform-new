package cn.hopever.platform.cms.repository.impl;

import cn.hopever.platform.cms.domain.ArticleTagTable;
import cn.hopever.platform.cms.repository.CustomArticleTagTableRepository;
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
@Repository("customArticleTagTableRepository")
public class CustomArticleTagTableRepositoryImpl extends SimpleJpaRepository<ArticleTagTable, Long> implements CustomArticleTagTableRepository {

    private final EntityManager entityManager;

    public CustomArticleTagTableRepositoryImpl(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(ArticleTagTable.class, entityManager), entityManager);
        this.entityManager = entityManager;
    }


    @Override
    public Page<ArticleTagTable> findByFilters(Map<String, Object> mapFilter, Pageable pageable) {
        return super.findAll(filterConditions1(mapFilter), pageable);
    }

    private Specification<ArticleTagTable> filterConditions1(Map<String, Object> mapFilter) {
        return new Specification<ArticleTagTable>() {
            public Predicate toPredicate(Root<ArticleTagTable> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                query.distinct(true);
                Predicate predicateReturn = null;
                if (mapFilter != null && mapFilter.size() > 0) {
                    for (String key : mapFilter.keySet()) {
                        if ("websiteTable".equals(key)) {
                            if (predicateReturn != null) {
                                predicateReturn = builder.and(predicateReturn, builder.equal(root.get(key), mapFilter.get(key)));

                            } else {
                                predicateReturn = builder.equal(root.get(key), mapFilter.get(key));
                            }
                        }else {
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
