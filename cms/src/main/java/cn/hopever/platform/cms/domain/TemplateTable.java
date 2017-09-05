package cn.hopever.platform.cms.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/30.
 */
@Entity
@Table(name = "platform_cms_template")
@Data
@EqualsAndHashCode(of = {"id"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.cms.template")
public class TemplateTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", length = 50)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "theme_id")
    private ThemeTable themeTable;

    @Column(name = "description")
    private String description;

    @Column(name = "content_position")
    private String contentPosition;

    @OneToMany(mappedBy = "templateTable", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<BlockTable> blockTables;

    @OneToMany(mappedBy = "templateTable", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<ArticleTable> articleTables;

}
