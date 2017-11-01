package cn.hopever.platform.cms.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by Donghui Huo on 2017/10/9.
 */
@Entity
@Table(name = "platform_cms_website_related_users")
@Data
@EqualsAndHashCode(of = {"id"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.cms.website_related_user")
public class WebsiteRelatedUserTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "website_id")
    private WebsiteTable websiteTable;


    @ManyToOne(optional = false)
    @JoinColumn(name = "related_user_id")
    private RelatedUserTable relatedUserTable;
}
