package cn.hopever.platform.cms.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by Donghui Huo on 2017/10/9.
 */
@Entity
@Table(name = "platform_cms_theme_related_users")
@Data
@EqualsAndHashCode(of = {"id"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.cms.theme_related_user")
public class ThemeRelatedUserTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "theme_id")
    private ThemeTable themeTable;


    @ManyToOne(optional = false)
    @JoinColumn(name = "related_user_id")
    private RelatedUserTable relatedUserTable;
}
