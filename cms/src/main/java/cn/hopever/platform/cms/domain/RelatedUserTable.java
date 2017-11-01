package cn.hopever.platform.cms.domain;

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
@Table(name = "platform_cms_related_user")
@Data
@EqualsAndHashCode(of = {"id"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.cms.related_user")
public class RelatedUserTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account", nullable = false, unique = true)
    private String account;

    @Column(name = "name")
    private String name;

    @Column(name = "created_date")
    private Date createdDate;

    @OneToMany(mappedBy = "createdUser")
    private List<ArticleTable> articleTables;
    @OneToMany(mappedBy = "createdUser")
    private List<MediaTable> mediaTables;
    @OneToMany(mappedBy = "createdUser")
    private List<ThemeTable> themeTables;
    @OneToMany(mappedBy = "createdUser")
    private List<WebsiteTable> websiteTables;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "relatedUserTable")
    private List<ThemeRelatedUserTable> themeRelatedUserTables;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "relatedUserTable")
    private List<WebsiteRelatedUserTable> websiteRelatedUserTables;

}
