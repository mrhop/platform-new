package cn.hopever.platform.user.service.impl;

import cn.hopever.platform.user.domain.ClientTable;
import cn.hopever.platform.user.domain.ModuleRoleTable;
import cn.hopever.platform.user.domain.ModuleTable;
import cn.hopever.platform.user.domain.UserTable;
import cn.hopever.platform.user.repository.ClientRoleTableRepository;
import cn.hopever.platform.user.repository.ClientTableRepository;
import cn.hopever.platform.user.repository.CustomClientTableRepository;
import cn.hopever.platform.user.repository.UserTableRepository;
import cn.hopever.platform.user.service.ClientTableService;
import cn.hopever.platform.utils.web.TableParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Donghui Huo on 2016/9/12.
 */
@Service("clientTableService")
@Transactional
public class ClientTableServiceImpl implements ClientTableService {

    Logger logger = LoggerFactory.getLogger(ClientTableServiceImpl.class);

    @Autowired
    private ClientTableRepository clientTableRepository;

    @Autowired
    private CustomClientTableRepository customClientTableRepository;

    @Autowired
    private UserTableRepository userTableRepository;

    @Autowired
    private ClientRoleTableRepository clientRoleTableRepository;

    @Override
    public ClientTable save(ClientTable client) {
        return clientTableRepository.save(client);
    }

    @Override
    public void delete(ClientTable client) {
        clientRoleTableRepository.delete(clientRoleTableRepository.findOneByAuthority(client.getClientId()));
        if (client.getModuleRoles() != null) {
            for (ModuleRoleTable mrt : client.getModuleRoles()) {
                mrt.setClient(null);
            }
        }
        if (client.getModules() != null) {
            for (ModuleTable mt : client.getModules()) {
                mt.setClient(null);
            }
        }
        if (client.getUsers() != null) {
            for (UserTable ut : client.getUsers()) {
                ut.getClients().remove(client);
            }
        }
        clientTableRepository.delete(client);
    }

    @Override
    public ClientTable getById(Long id) {
        return clientTableRepository.findOne(id);
    }

    @Override
    public Iterable<ClientTable> getAll() {
        return clientTableRepository.findAll();
    }

    @Override
    public List<ClientTable> getByUserName(String userName) {
        UserTable ut = userTableRepository.findOneByUsername(userName);
        List<UserTable> list = new ArrayList<>();
        list.add(ut);
        return clientTableRepository.findByUsersIn(list);
    }

    @Override
    public List<ClientTable> getByUserId(Long userId) {
        UserTable ut = userTableRepository.findOne(userId);
        List<UserTable> list = new ArrayList<>();
        list.add(ut);
        return clientTableRepository.findByUsersIn(list);
    }

    @Override
    public List<ClientTable> getByIds(List<Long> ids) {
        List<ClientTable> list = new ArrayList<>();
        for (Long id : ids) {
            list.add(clientTableRepository.findOne(id));
        }
        return list;
    }

    @Override
    public Page<ClientTable> getList(Pageable pageable, Map<String, Object> filterMap) {
        return customClientTableRepository.findByFilters(filterMap, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientTable> getList(TableParameters body) {
        Page<ClientTable> list;
        PageRequest pageRequest;
        if (body.getSorts() == null || body.getSorts().isEmpty()) {
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.ASC, "id");
        } else {
            String key = body.getSorts().keySet().iterator().next();
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.fromString(body.getSorts().get(key)), key);
        }
        return customClientTableRepository.findByFilters( body.getFilters(), pageRequest);
    }


    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDetails cd = clientTableRepository.findOneByClientId(clientId);
        if (cd == null) {
            logger.error("there is no client of id: " + clientId);
        }
        return cd;
    }

    @Override
    public ClientTable getByClientId(String clientId) throws ClientRegistrationException {
        ClientTable cd = clientTableRepository.findOneByClientId(clientId);
        return cd;
    }

}
