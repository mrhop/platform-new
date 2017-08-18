package cn.hopever.platform.user.service.impl;

import cn.hopever.platform.user.domain.*;
import cn.hopever.platform.user.repository.*;
import cn.hopever.platform.user.service.ClientTableService;
import cn.hopever.platform.user.vo.ClientVo;
import cn.hopever.platform.user.vo.ClientVoAssembler;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.SelectOption;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
    private ClientVoAssembler clientVoAssembler;
    @Autowired
    private ClientTableRepository clientTableRepository;

    @Autowired
    private RoleTableRepository roleTableRepository;

    @Autowired
    private CustomClientTableRepository customClientTableRepository;

    @Autowired
    private UserTableRepository userTableRepository;

    @Autowired
    private ClientRoleTableRepository clientRoleTableRepository;

    @Autowired
    private ResouceScopeTableRepository resouceScopeTableRepository;

    @Override
    public VueResults.Result save(ClientVo client) {
        if (clientTableRepository.findOneByClientId(client.getClientId()) != null) {
            return VueResults.generateError("创建失败", "应用账户已存在");
        }
        ClientTable clientTable = new ClientTable();
        BeanUtils.copyNotNullProperties(client, clientTable);
        if (client.getScopeIds() != null) {
            List<ClientResouceScopeTable> list = new ArrayList<>();
            for (long id : client.getScopeIds()) {
                ResouceScopeTable resouceScopeTable = resouceScopeTableRepository.findOne(id);
                ClientResouceScopeTable clientResouceScopeTable = new ClientResouceScopeTable();
                clientResouceScopeTable.setClient(clientTable);
                clientResouceScopeTable.setScope(resouceScopeTable);
                if (client.getAutoApprovaledScopeIds() != null && client.getAutoApprovaledScopeIds().contains(id)) {
                    clientResouceScopeTable.setAutoApprove(true);
                }
                list.add(clientResouceScopeTable);
            }
            clientTable.setClientResouceScopeTables(list);
        }
        clientTableRepository.save(clientTable);
        RoleTable roleTable = new RoleTable();
        roleTable.setAuthority("ROLE_" + clientTable.getClientId());
        roleTable.setLevel((short) 3);
        roleTable.setName(clientTable.getClientName());
        roleTableRepository.save(roleTable);
        return VueResults.generateSuccess("创建成功", "应用创建成功");
    }

    @Override
    public VueResults.Result update(ClientVo client) {
        ClientTable clientTable = clientTableRepository.findOne(client.getId());
        BeanUtils.copyNotNullProperties(client, clientTable);
        if (client.getScopeIds() != null) {
            List<ClientResouceScopeTable> list = new ArrayList<>();
            for (long id : client.getScopeIds()) {
                ResouceScopeTable resouceScopeTable = resouceScopeTableRepository.findOne(id);
                ClientResouceScopeTable clientResouceScopeTable = new ClientResouceScopeTable();
                clientResouceScopeTable.setClient(clientTable);
                clientResouceScopeTable.setScope(resouceScopeTable);
                if (client.getAutoApprovaledScopeIds() != null && client.getAutoApprovaledScopeIds().contains(id)) {
                    clientResouceScopeTable.setAutoApprove(true);
                }
                list.add(clientResouceScopeTable);
            }
            clientTable.getClientResouceScopeTables().clear();
            clientTable.getClientResouceScopeTables().addAll(list);
        }
        clientTableRepository.save(clientTable);
        return VueResults.generateSuccess("更新成功", "应用更新成功");
    }

    @Override
    public void delete(ClientTable client) {
        clientTableRepository.delete(client);
    }

    @Override
    public void delete(long id) {
        ClientTable clientTable = clientTableRepository.findOne(id);
        if (clientTable != null && !clientTable.getClientId().equals("user_admin_client")) {
            if (clientTable.getUsers() != null) {
                for (UserTable ut : clientTable.getUsers()) {
                    ut.getClients().remove(clientTable);
                }
            }
            clientTableRepository.delete(clientTable);
            roleTableRepository.delete(roleTableRepository.findOneByAuthority("ROLE_" + clientTable.getClientId()));
        }
    }

    @Override
    public ClientTable getById(Long id) {
        return clientTableRepository.findOne(id);
    }

    @Override
    public ClientVo getVoById(Long id) {
        return clientVoAssembler.toResource(clientTableRepository.findOne(id));
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
    public Page<ClientVo> getList(TableParameters body) {
        PageRequest pageRequest;
        if (body.getSorts() == null || body.getSorts().isEmpty()) {
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.ASC, "id");
        } else {
            String key = body.getSorts().keySet().iterator().next();
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.fromString(body.getSorts().get(key)), key);
        }
        Page<ClientTable> page = customClientTableRepository.findByFilters(body.getFilters(), pageRequest);
        List<ClientVo> list = new ArrayList<>();
        for (ClientTable clientTable : page) {
            list.add(clientVoAssembler.toResource(clientTable));
        }
        return new PageImpl<ClientVo>(list, pageRequest, page.getTotalElements());
    }

    @Override
    public List<SelectOption> getResouceScopeOptions() {
        List<SelectOption> listReturn = new ArrayList<>();
        Iterable<ResouceScopeTable> list = resouceScopeTableRepository.findAll();
        for (ResouceScopeTable resouceScopeTable : list) {
            SelectOption selectOption = new SelectOption(resouceScopeTable.getName(), resouceScopeTable.getId());
            listReturn.add(selectOption);
        }
        return listReturn;
    }

    @Override
    public List<SelectOption> getAutoApprovaledScopeOptions(Long id) {
        List<SelectOption> listReturn = new ArrayList<>();
        ClientTable clientTable = clientTableRepository.findOne(id);
        Iterable<ResouceScopeTable> list = resouceScopeTableRepository.findAll();
        for (ClientResouceScopeTable clientResouceScopeTable : clientTable.getClientResouceScopeTables()) {
            SelectOption selectOption = new SelectOption(clientResouceScopeTable.getScope().getName(), clientResouceScopeTable.getScope().getId());
            listReturn.add(selectOption);
        }
        return listReturn;
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
