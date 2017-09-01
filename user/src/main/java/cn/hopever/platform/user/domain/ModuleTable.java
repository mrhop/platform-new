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

    @Column(name = "module_id", nullable = false, length = 50, unique = true)
    private String moduleId;

    @Column(name = "module_order", nullable = true)
    private Integer moduleOrder = 0;

    // 用于和order一起来决定顺序
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "before_id")
    private ModuleTable beforeModule;

    @Column(name = "module_url", length = 200)
    private String moduleUrl;

    @Column(name = "icon_class", length = 50)
    private String iconClass;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private ModuleTable parent;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @OrderBy("moduleOrder asc")
    private List<ModuleTable> children;

    @Column(name = "available", nullable = false)
    private boolean available = true;

    @Column(name = "activated", nullable = false)
    private boolean activated = true;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "platform_user_module_module_role", joinColumns = @JoinColumn(name = "module_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<ModuleRoleTable> authorities;

}
