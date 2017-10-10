package cn.hopever.platform.crm.repository.impl;

import cn.hopever.platform.crm.domain.ProductTable;
import cn.hopever.platform.crm.repository.CustomProductTableRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Map;

/**
 * Created by Donghui Huo on 2016/11/30.
 */
@Repository("customProductTableRepository")
public class CustomProductTableRepositoryImpl extends SimpleJpaRepository<ProductTable, Long> implements CustomProductTableRepository {

    private final EntityManager entityManager;

    public CustomProductTableRepositoryImpl(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(ProductTable.class, entityManager), entityManager);
        this.entityManager = entityManager;
    }


    @Override
    public Page<ProductTable> findByFilters(Map<String, Object> mapFilter, Pageable pageable) {
        return null;
    }

}
