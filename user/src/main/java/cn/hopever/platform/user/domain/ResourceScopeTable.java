package cn.hopever.platform.user.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Donghui Huo on 2016/9/8.
 */
@Entity
@Table(name = "platform_user_resource_scope")
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"clientResourceScopeTables"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.user.resourcescope")
public class ResourceScopeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "scope_id", nullable = false, length = 50, unique = true)
    private String scopeId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "scope", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<ClientResourceScopeTable> clientResourceScopeTables;

}
