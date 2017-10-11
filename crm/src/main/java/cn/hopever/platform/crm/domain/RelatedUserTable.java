package cn.hopever.platform.crm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/30.
 */
@Entity
@Table(name = "platform_crm_related_user")
@Data
@EqualsAndHashCode(of = {"id"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.crm.related_user")
public class RelatedUserTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "account", nullable = false)
    private String account;

    @Column(name = "name")
    private String name;

    @Column(name = "custom_discount")
    private boolean customDiscount = false;

    @Column(name = "lower_limit")
    private Float lowerLimit;

    @Column(name = "created_date")
    private Date createdDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "relatedUserTable")
    private List<ClientRelatedUserTable> clientRelatedUserTables;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "trackUser")
    private List<ClientTrackTable> clientTrackTables;

    @OneToMany(mappedBy = "createdUser")
    private List<OrderTable> orderTables;

    @OneToMany(mappedBy = "createdUser")
    private List<ClientTable> clientTables;
}
