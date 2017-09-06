package cn.hopever.platform.cms.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by Donghui Huo on 2017/8/30.
 */
@Entity
@Table(name = "platform_cms_block")
@Data
@EqualsAndHashCode(of = {"id"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.cms.block")
public class BlockTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(length = 50)
    private String position;

    @Column(columnDefinition = "text")
    private String content;

    @Column(columnDefinition = "text")
    private String script;

    // 目的是保证唯一性，在更换theme和template的情况下不会丢失数据，可以自由切换
    @ManyToOne(optional = false)
    @JoinColumn(name = "template_id")
    private TemplateTable templateTable;

    @ManyToOne
    @JoinColumn(name = "website_id")
    private WebsiteTable websiteTable;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleTable articleTable;

}
