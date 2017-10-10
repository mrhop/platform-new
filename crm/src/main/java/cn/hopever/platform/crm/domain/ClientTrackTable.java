package cn.hopever.platform.crm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Donghui Huo on 2017/8/30.
 */
@Entity
@Table(name = "platform_crm_client_track")
@Data
@EqualsAndHashCode(of = {"id"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.crm.client_track")
public class ClientTrackTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private ClientTable clientTable;

    // 采用select的方式
    @Column(name = "track_method", length = 50, nullable = false)
    private String trackMethod;

    @ManyToOne(optional = false)
    @JoinColumn(name = "related_user_id")
    private RelatedUserTable trackUser;

    @Column(name = "track_date", nullable = false)
    private Date trackDate;


    // 沟通时间 10分钟内，30分钟，1个小时
    @Column(name = "duration")
    private short duration;

    @Column(name = "comment", length = 2000)
    private String comment;

    // 沟通结果，无法沟通，非常消极，消极，可以持续，积极，非常积极，有望成单
    @Column(name = "result")
    private short result;

}
