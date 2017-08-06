package cn.hopever.platform.user.repository;

import cn.hopever.platform.user.domain.ClientTable;
import cn.hopever.platform.user.domain.RoleTable;
import cn.hopever.platform.user.domain.UserTable;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface UserTableRepository extends PagingAndSortingRepository<UserTable, Long> {


    public UserTable findOneByUsername(String username);

    public UserTable findOneByEmail(String email);

    public UserTable findOneByPhone(String phone);

    @CachePut(cacheNames = {"hopever.user"}, key = "'user_'.concat(#p0.id)", condition = "#p0.id!=null")
    public UserTable save(UserTable userTable);

    @CacheEvict(cacheNames = {"hopever.user"}, key = "'user_'.concat(#p0)")
    public void delete(Long id);

//    @Cacheable(cacheNames = {"hopever.user"}, key = "'user_'.concat(#p0)")
    public UserTable findOne(Long id);

    public List<UserTable> findByUsernameLike(String username, Pageable pageable);

    public Page<UserTable> findDistinctByAuthoritiesInAndClientsIn(Collection<RoleTable> authorities, Collection<ClientTable> clients, Pageable pageable);

    public Page<UserTable> findByUsernameNot(String username, Pageable pageable);

    public Page<UserTable> findDistinctByCreateUserAndAuthoritiesInAndClientsIn(UserTable userTable, Collection<RoleTable> authorities, Collection<ClientTable> clients, Pageable pageable);

    @Modifying
    @Query("update UserTable u set u.createUser = ?1 where u.createUser = ?2")
    int updateCreateUser(UserTable userTable, UserTable userTablePrev);

    public List<UserTable> findByAuthoritiesInAndClientsIn(Collection<RoleTable> authorities, Collection<ClientTable> clients);

}
