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
@Table(name = "platform_crm_client")
@Data
@EqualsAndHashCode(of = {"id"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.crm.client")
public class ClientTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 自动生成的客户码
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "contact", nullable = false)
    private String contact;

    @Column(name = "address")
    private String address;

    @Column(name = "telephone", length = 50)
    private String telephone;

    @Column(name = "cellphone", length = 50)
    private String cellphone;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "wechat", length = 50)
    private String wechat;

    @Column(name = "skype", length = 50)
    private String skype;

    @Column(name = "additional_msg", length = 2000)
    private String additionalMsg;


    @ManyToOne
    @JoinColumn(name = "client_origin_id")
    private ClientOriginTable clientOriginTable;

    @ManyToOne
    @JoinColumn(name = "client_level_id")
    private ClientLevelTable clientLevelTable;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryTable countryTable;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "clientTable")
    private List<ClientRelatedUserTable> clientRelatedUserTables;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "clientTable")
    private List<ClientTrackTable> clientTrackTables;


    @Column(name = "created_date")
    private Date createdDate;


    @ManyToOne
    @JoinColumn(name = "created_user_id")
    private RelatedUserTable createdUser;

}
