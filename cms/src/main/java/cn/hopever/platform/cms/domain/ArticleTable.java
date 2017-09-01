package cn.hopever.platform.cms.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/30.
 * 上传时，采用zip包上传，然后统一放置在对应的theme下，或者顺序创建
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

    @Column(name = "content", length = 2500)
    private String content;

    @Column(name = "script", length = 2500)
    private String script;

    @Column(name = "published")
    private boolean published;

    @Column(name = "click_times")
    private Integer clickTimes;

    @ManyToMany(mappedBy = "articleTables")
    private List<ArticleTagTable> articleTagTables;

    @Column(name = "publish_date")
    private Date publishDate;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "create_user")
    private String createUser;

    @ManyToOne(optional = false)
    @JoinColumn(name = "website_id")
    private WebsiteTable websiteTable;

    @OneToMany(mappedBy = "articleTable", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<BlockTable> blockTables;

    @OneToMany(mappedBy = "articleTable", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<StaticResourceTable> staticResourceTables;

}