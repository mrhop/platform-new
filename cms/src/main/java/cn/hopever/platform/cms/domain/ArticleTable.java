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
@Table(name = "platform_cms_article")
@Data
@EqualsAndHashCode(of = {"id"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.cms.article")
public class ArticleTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

    @Column(name = "alias_url", length = 50)
    private String aliasUrl;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "script", columnDefinition = "text")
    private String script;

    @Column(name = "published")
    private boolean published;

    @Column(name = "click_times")
    private Integer clickTimes;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "platform_cms_article_article_tag", joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"), indexes = {@Index(columnList = "article_id"), @Index(columnList = "tag_id")})
    private List<ArticleTagTable> articleTagTables;

    @Column(name = "publish_date")
    private Date publishDate;

    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "created_user_id")
    private RelatedUserTable createdUser;

    @ManyToOne(optional = false)
    @JoinColumn(name = "website_id")
    private WebsiteTable websiteTable;

    @ManyToOne(optional = false)
    @JoinColumn(name = "template_id")
    private TemplateTable templateTable;

    @OneToMany(mappedBy = "articleTable", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @OrderBy("position asc")
    private List<BlockTable> blockTables;

    @OneToMany(mappedBy = "articleTable", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<StaticResourceTable> staticResourceTables;

    // 对应的article 0 ，news 1，event 2
    @Column(name = "type")
    private short type = 0;

}
