package cn.hopever.platform.user.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Donghui Huo on 2016/9/8.
 */
@Entity
@Table(name = "platform_user_client_role")
@Data
@EqualsAndHashCode(of={"id"})
@ToString(exclude = {"clients"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.user.clientrole")
public class ClientRoleTable implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "authority",nullable = false,length= 50,unique = true)
    private String authority;

    @Column(name = "level",nullable = false)
    private short level;

    @ManyToMany(mappedBy = "authorities")
    private List<ClientTable> clients;

    @Column(name = "name", nullable = false, length = 50, unique = true)
    private String name;

}
