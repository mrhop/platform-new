package cn.hopever.platform.cms.domain;

import cn.hopever.platform.utils.json.JacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/30.
 * 上传时，采用zip包上传，然后统一放置在对应的theme下，或者顺序创建
 */
@Entity
@Table(name = "platform_cms_theme")
@Data
@EqualsAndHashCode(of = {"id"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.cms.theme")
public class ThemeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "theme_id", length = 50, unique = true)
    private String themeId;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "screenshots", length = 510)
    private String screenshots;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "themeTable")
    private List<ThemeRelatedUserTable> themeRelatedUserTables;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "themeTable", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<TemplateTable> templateTables;

    @OneToMany(mappedBy = "themeTable", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<StaticResourceTable> staticResourceTables;

    @OneToMany(mappedBy = "themeTable", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<WebsiteTable> websiteTables;

    @OneToMany(mappedBy = "themeTable", cascade = {CascadeType.ALL})
    private List<MediaTagTable> mediaTagTables;

    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "created_user_id")
    private RelatedUserTable createdUser;

    public List<String> getScreenshots() {
        if (this.screenshots != null) {
            try {
                return JacksonUtil.mapper.readValue(this.screenshots, List.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void setScreenshots(List<String> screenshots) {
        if (screenshots != null && screenshots.size() > 0) {
            try {
                this.screenshots = JacksonUtil.mapper.writeValueAsString(screenshots);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            this.screenshots = null;
        }
    }
}
