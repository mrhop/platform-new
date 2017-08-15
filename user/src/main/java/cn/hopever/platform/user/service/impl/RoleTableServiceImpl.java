package cn.hopever.platform.user.service.impl;

import cn.hopever.platform.user.domain.RoleTable;
import cn.hopever.platform.user.repository.CustomRoleTableRepository;
import cn.hopever.platform.user.repository.RoleTableRepository;
import cn.hopever.platform.user.service.RoleTableService;
import cn.hopever.platform.utils.web.TableParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Donghui Huo on 2016/10/17.
 */
@Service("roleTableService")
@Transactional
public class RoleTableServiceImpl implements RoleTableService {

    Logger logger = LoggerFactory.getLogger(RoleTableServiceImpl.class);

    @Autowired
    private RoleTableRepository roleTableRepository;

    @Autowired
    private CustomRoleTableRepository customRoleTableRepository;

    @Override
    public RoleTable save(RoleTable role) {
        return null;
    }

    @Override
    public RoleTable update(RoleTable role) {
        return null;
    }

    @Override
    public List<RoleTable> getList(String authority) {
        List<RoleTable> listReturn = new ArrayList<>();
        if (authority.equals("ROLE_admin")) {
            listReturn.add(roleTableRepository.findOneByAuthority("ROLE_admin"));
            listReturn.add(roleTableRepository.findOneByAuthority("ROLE_common_user"));
        } else {
            listReturn.add(roleTableRepository.findOneByAuthority("ROLE_super_admin"));
            listReturn.add(roleTableRepository.findOneByAuthority("ROLE_admin"));
            listReturn.add(roleTableRepository.findOneByAuthority("ROLE_common_user"));
        }
        return listReturn;
    }

    @Override
    public Page<RoleTable> getList(TableParameters body) {
        PageRequest pageRequest;
        if (body.getSorts() == null || body.getSorts().isEmpty()) {
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.ASC, "level");
        } else {
            String key = body.getSorts().keySet().iterator().next();
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.fromString(body.getSorts().get(key)), key);
        }
        return customRoleTableRepository.findByFilters(body.getFilters(), pageRequest);
    }

    @Override
    public RoleTable get(Long id) {
        return roleTableRepository.findOne(id);
    }

    @Override
    public RoleTable getByAuthority(String authority) {
        return roleTableRepository.findOneByAuthority(authority);
    }
}
