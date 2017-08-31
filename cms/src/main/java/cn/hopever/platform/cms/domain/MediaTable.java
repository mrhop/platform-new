package cn.hopever.platform.cms.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Donghui Huo on 2017/8/30.
 * 上传时，采用zip包上传，然后统一放置在对应的theme或者website下面，甚至可以考虑template的后缀为tpl的文件的按照规则上传，或者通过选择生成tpl，最后还是要生成一个tpl文件出来并关联tpl的表
 * 可以进行对应的整体更新，或者单独的js和css更新，但是不包含图片以及其他静态资源
 */
@Entity
@Table(name = "platform_cms_media")
@Data
@EqualsAndHashCode(of = {"id"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.cms.media")
public class MediaTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "filename", length = 50)
    private String filename;

    //image video audio
    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "file_type", length = 50)
    private String fileType;

    @Column(name = "size")
    private long size;

    @Column(name = "url")
    private String url;

    @ManyToOne(optional = false)
    @JoinColumn(name = "media_tag_id")
    private MediaTagTable mediaTagTable;

    @ManyToOne(optional = false)
    @JoinColumn(name = "website_id")
    private WebsiteTable websiteTable;

    @Column(name = "publish_date")
    private Date publishDate;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "published")
    private boolean published;

    @Column(name = "click_times")
    private Integer clickTimes;
}
