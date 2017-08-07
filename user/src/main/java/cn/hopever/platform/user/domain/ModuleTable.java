package cn.hopever.platform.user.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by Donghui Huo on 2016/9/8.
 */
@Entity
@Table(name = "platform_user_module")
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"client", "parent", "children", "authorities"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.user.module")
public class ModuleTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "client_id", nullable = true)
    private ClientTable client;

    @Column(name = "module_name", nullable = false, length = 50)
    private String moduleName;

    @Column(name = "module_order", nullable = true)
    private Integer moduleOrder = 0;

    @Column(name = "module_url", length = 200)
    private String moduleUrl;

    @Column(name = "icon_class", length = 50)
    private String iconClass;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private ModuleTable parent;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.ALL})
    @OrderBy("moduleOrder asc")
    private List<ModuleTable> children;

    @Column(name = "available", nullable = false)
    private boolean available = true;

    @Column(name = "activated", nullable = false)
    private boolean activated = true;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "platform_user_module_module_role", joinColumns = @JoinColumn(name = "module_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<ModuleRoleTable> authorities;

}
