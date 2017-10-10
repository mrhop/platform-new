package cn.hopever.platform.crm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/30.
 */
@Entity
@Table(name = "platform_crm_client_level")
@Data
@EqualsAndHashCode(of = {"id"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.crm.client_level")
public class ClientLevelTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "level", length = 50, nullable = false)
    private String level;

    @Column(name = "additional_msg", length = 2000)
    private String additionalMsg;

    @OneToMany(mappedBy = "clientLevelTable")
    private List<ClientTable> clientTables;
}
