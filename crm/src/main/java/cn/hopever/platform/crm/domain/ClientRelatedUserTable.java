package cn.hopever.platform.crm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by Donghui Huo on 2017/10/9.
 */
@Entity
@Table(name = "platform_crm_client_related_users")
@Data
@EqualsAndHashCode(of = {"id"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.crm.client_related_users")
public class ClientRelatedUserTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private ClientTable clientTable;


    @ManyToOne(optional = false)
    @JoinColumn(name = "related_user_id")
    private RelatedUserTable relatedUserTable;
}
