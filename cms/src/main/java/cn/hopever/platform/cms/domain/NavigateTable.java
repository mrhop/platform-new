package cn.hopever.platform.cms.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Donghui Huo on 2016/9/8.
 */
@Entity
@Table(name = "platform_cms_navigate")
@Data
@EqualsAndHashCode(of = {"id"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.cms.navigate")
public class NavigateTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "navigate_order", nullable = true)
    private Integer navigateOrder = 0;

    @Column(name = "relate_type")
    private Short relateType;

    // 用于和order一起来决定顺序
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "before_id")
    private NavigateTable beforeNavigate;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleTable articleTable;

    @Column(name = "icon_class", length = 50)
    private String iconClass;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private NavigateTable parent;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @OrderBy("navigateOrder asc")
    private List<NavigateTable> children;

    @Column(name = "activated", nullable = false)
    private boolean activated = true;

    @ManyToOne(optional = false)
    @JoinColumn(name = "website_id")
    private WebsiteTable websiteTable;

}
