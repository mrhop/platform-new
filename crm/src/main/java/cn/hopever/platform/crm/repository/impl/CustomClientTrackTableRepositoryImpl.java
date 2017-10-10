package cn.hopever.platform.crm.repository.impl;

import cn.hopever.platform.crm.domain.ClientTrackTable;
import cn.hopever.platform.crm.repository.CustomClientTrackTableRepository;
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
@Repository("customClientTrackTableRepository")
public class CustomClientTrackTableRepositoryImpl extends SimpleJpaRepository<ClientTrackTable, Long> implements CustomClientTrackTableRepository {

    private final EntityManager entityManager;

    public CustomClientTrackTableRepositoryImpl(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(ClientTrackTable.class, entityManager), entityManager);
        this.entityManager = entityManager;
    }


    @Override
    public Page<ClientTrackTable> findByFilters(Map<String, Object> mapFilter, Pageable pageable) {
        return null;
    }

}