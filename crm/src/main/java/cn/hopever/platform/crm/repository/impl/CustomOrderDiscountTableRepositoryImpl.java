package cn.hopever.platform.crm.repository.impl;

import cn.hopever.platform.crm.domain.OrderDiscountTable;
import cn.hopever.platform.crm.repository.CustomOrderDiscountTableRepository;
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
@Repository("customOrderDiscountTableRepository")
public class CustomOrderDiscountTableRepositoryImpl extends SimpleJpaRepository<OrderDiscountTable, Long> implements CustomOrderDiscountTableRepository {

    private final EntityManager entityManager;

    public CustomOrderDiscountTableRepositoryImpl(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(OrderDiscountTable.class, entityManager), entityManager);
        this.entityManager = entityManager;
    }


    @Override
    public Page<OrderDiscountTable> findByFilters(Map<String, Object> mapFilter, Pageable pageable) {
        return null;
    }

}
