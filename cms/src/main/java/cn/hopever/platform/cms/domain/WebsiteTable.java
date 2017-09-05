package cn.hopever.platform.cms.domain;

import cn.hopever.platform.utils.json.JacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.IOException;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/30.
 * 上传时，采用zip包上传，然后统一放置在对应的theme下，或者顺序创建
 */
@Entity
@Table(name = "platform_cms_website")
@Data
@EqualsAndHashCode(of = {"id"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.cms.website")
public class WebsiteTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "website_id", length = 50)
    private String websiteId;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

    @Column(name = "url", length = 50)
    private String url;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "phone", length = 50)
    private String phone;

    @Column(name = "resource_location", length = 50)
    private String resourceLocation;

    @Column(name = "resource_prefix_url", length = 50)
    private String resourcePrefixUrl;

    @Column(name = "related_users")
    private String relatedUsers;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "screenshots", length = 510)
    private String screenshots;

    @Column(name = "description")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "theme_id")
    private ThemeTable themeTable;

    //因为和articleTagTables有依赖关系，所以处理完articleTag之后再处理articleTable
    @OneToMany(mappedBy = "websiteTable")
    private List<ArticleTable> articleTables;

    @OneToMany(mappedBy = "websiteTable", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<ArticleTagTable> articleTagTables;

    @OneToMany(mappedBy = "websiteTable", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<BlockTable> blockTables;

    @OneToMany(mappedBy = "websiteTable", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<StaticResourceTable> staticResourceTables;

    @OneToMany(mappedBy = "websiteTable", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<MediaTagTable> mediaTagTables;


    @OneToMany(mappedBy = "websiteTable", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<NavigateTable> navigateTables;


    public List<String> getScreenshots()  {
        if (this.screenshots != null) {
            try {
                return JacksonUtil.mapper.readValue(this.screenshots, List.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void setScreenshots(List<String> screenshots){
        try {
            this.screenshots = JacksonUtil.mapper.writeValueAsString(screenshots);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<String> getRelatedUsers()  {
        if (this.relatedUsers != null) {
            try {
                return JacksonUtil.mapper.readValue(this.relatedUsers, List.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void setRelatedUsers(List<String> relatedUsers)  {
        try {
            this.relatedUsers = JacksonUtil.mapper.writeValueAsString(relatedUsers);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
