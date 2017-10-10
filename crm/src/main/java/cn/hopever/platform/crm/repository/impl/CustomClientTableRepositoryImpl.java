package cn.hopever.platform.crm.repository.impl;

import cn.hopever.platform.crm.domain.ClientTable;
import cn.hopever.platform.crm.repository.CustomClientTableRepository;
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
@Repository("customClientTableRepository")
public class CustomClientTableRepositoryImpl extends SimpleJpaRepository<ClientTable, Long> implements CustomClientTableRepository {

    private final EntityManager entityManager;

    public CustomClientTableRepositoryImpl(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(ClientTable.class, entityManager), entityManager);
        this.entityManager = entityManager;
    }


    @Override
    public Page<ClientTable> findByFilters(Map<String, Object> mapFilter, Pageable pageable) {
        return null;
    }

}
