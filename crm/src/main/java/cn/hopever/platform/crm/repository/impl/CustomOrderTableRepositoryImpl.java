package cn.hopever.platform.crm.repository.impl;

import cn.hopever.platform.crm.domain.OrderTable;
import cn.hopever.platform.crm.repository.CustomOrderTableRepository;
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
@Repository("customOrderTableRepository")
public class CustomOrderTableRepositoryImpl extends SimpleJpaRepository<OrderTable, Long> implements CustomOrderTableRepository {

    private final EntityManager entityManager;

    public CustomOrderTableRepositoryImpl(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(OrderTable.class, entityManager), entityManager);
        this.entityManager = entityManager;
    }


    @Override
    public Page<OrderTable> findByFilters(Map<String, Object> mapFilter, Pageable pageable) {
        return null;
    }

}
