package cn.hopever.platform.cms.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/30.
 * 上传时，采用zip包上传，然后统一放置在对应的theme下，或者顺序创建
 */
@Entity
@Table(name = "platform_cms_media_tag", indexes = {@Index(columnList = "website_id")})
@Data
@EqualsAndHashCode(of = {"id"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.cms.mediatag")
public class MediaTagTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", length = 50)
    private String name;

    // 预置 document image video audio 四种形式
    @Column(name = "tag_id", length = 50, unique = true)
    private String tagId;

    @ManyToOne(optional = true)
    @JoinColumn(name = "website_id")
    private WebsiteTable websiteTable;

    @ManyToOne(optional = true)
    @JoinColumn(name = "theme_id")
    private ThemeTable themeTable;


    @OneToMany(mappedBy = "mediaTagTable", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<MediaTable> mediaTables;
}
