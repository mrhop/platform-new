package cn.hopever.platform.cms.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by Donghui Huo on 2017/8/30.
 * 上传时，采用zip包上传，然后统一放置在对应的theme或者website下面，甚至可以考虑template的后缀为tpl的文件的按照规则上传，或者通过选择生成tpl，最后还是要生成一个tpl文件出来并关联tpl的表
 * 可以进行对应的整体更新，或者单独的js和css更新，但是不包含图片以及其他静态资源
 */
@Entity
@Table(name = "platform_cms_static_resource")
@Data
@EqualsAndHashCode(of = {"id"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.cms.staticresource")
public class StaticResourceTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "filename", length = 50)
    private String filename;

    //script css
    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "file_type", length = 50)
    private String fileType;

    @Column(name = "size")
    private long size;

    @Column(name = "url")
    private String url;

    @ManyToOne(optional = false)
    @JoinColumn(name = "theme_id")
    private ThemeTable themeTable;

    @ManyToOne
    @JoinColumn(name = "website_id")
    private WebsiteTable websiteTable;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleTable articleTable;

    // order 需要后续更新
    @Column(name = "resource_order", nullable = false)
    private Integer resourceOrder;

    // 用于和order一起来决定顺序
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "before_id")
    private StaticResourceTable beforeStaticResource;

}
