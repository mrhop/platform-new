package cn.hopever.platform.user.repository;

import cn.hopever.platform.user.domain.UserTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface CustomUserTableRepository {
    public Page<UserTable> findByUsernameNotAndFilters(String username, Map<String, Object> mapFilter, Pageable pageable);

    public Page<UserTable> findByCreateUserAndAuthoritiesInAndClientsInAndFilters(UserTable userTable, String authority1, String authority2, Map<String, Object> mapFilter, Pageable pageable);

    public List<String> findByAuthorityAndClientId(String authority, String clientId);
}
