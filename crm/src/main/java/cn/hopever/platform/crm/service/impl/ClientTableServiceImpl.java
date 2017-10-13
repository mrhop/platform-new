package cn.hopever.platform.crm.service.impl;

import cn.hopever.platform.crm.config.CommonMethods;
import cn.hopever.platform.crm.domain.*;
import cn.hopever.platform.crm.repository.*;
import cn.hopever.platform.crm.service.ClientTableService;
import cn.hopever.platform.crm.vo.ClientVo;
import cn.hopever.platform.crm.vo.ClientVoAssembler;
import cn.hopever.platform.utils.web.SelectOption;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.*;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
@Service
@Transactional
public class ClientTableServiceImpl implements ClientTableService {

    @Autowired
    private ClientTableRepository clientTableRepository;
    @Autowired
    private CustomClientTableRepository customClientTableRepository;
    @Autowired
    private RelatedUserTableRepository relatedUserTableRepository;
    @Autowired
    private ClientLevelTableRepository clientLevelTableRepository;
    @Autowired
    private ClientOriginTableRepository clientOriginTableRepository;
    @Autowired
    private CountryTableRepository countryTableRepository;
    @Autowired
    private ClientVoAssembler clientVoAssembler;


    @Override
    public Page<ClientVo> getList(TableParameters body, Principal principal) {
        Map<String, Object> map = body.getFilters();
        if (map == null) {
            map = new HashMap<>();
        }
        // 根据用户的规格查看这个用户可以处理的client
        if (!CommonMethods.isAdmin(principal)) {
            map.put("relatedUserId", relatedUserTableRepository.findOneByAccount(principal.getName()).getId());
        }
        if(map.get("clientOriginId")!=null){
            map.put("clientOriginTable",clientOriginTableRepository.findOne(Long.valueOf(map.get("clientOriginId").toString())));
            map.remove("clientOriginId");
        }
        if(map.get("clientLevelId")!=null){
            map.put("clientLevelTable",clientLevelTableRepository.findOne(Long.valueOf(map.get("clientLevelId").toString())));
            map.remove("clientLevelId");
        }
        if(map.get("countryId")!=null){
            map.put("countryTable",countryTableRepository.findOne(Long.valueOf(map.get("countryId").toString())));
            map.remove("countryId");
        }
        if(map.get("createdUserId")!=null){
            map.put("createdUser",relatedUserTableRepository.findOne(Long.valueOf(map.get("createdUserId").toString())));
            map.remove("createdUserId");
        }
        body.setFilters(map);
        PageRequest pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.ASC, "id");
        Page<ClientTable> page = customClientTableRepository.findByFilters(body.getFilters(), pageRequest);
        List<ClientVo> list = new ArrayList<>();
        for (ClientTable clientTable : page) {
            ClientVo clientVo = clientVoAssembler.toResource(clientTable);
            if (CommonMethods.isAdmin(principal)) {
                if (clientTable.getClientRelatedUserTables() != null) {
                    List<Long> relatedUserIds = new ArrayList<>();
                    List<String> relatedUserAccounts = new ArrayList<>();
                    for (ClientRelatedUserTable clientRelatedUserTable : clientTable.getClientRelatedUserTables()) {
                        relatedUserIds.add(clientRelatedUserTable.getRelatedUserTable().getId());
                        relatedUserAccounts.add(clientRelatedUserTable.getRelatedUserTable().getAccount());
                    }
                    clientVo.setRelatedUserIds(relatedUserIds);
                    clientVo.setRelatedUserAccounts(relatedUserAccounts);
                }
            }
            if (clientTable.getOrderTables() != null) {
                float orderSum = 0.0f;
                for (OrderTable orderTable : clientTable.getOrderTables()) {
                    if (orderTable.equals("finished")) {
                        orderSum += orderTable.getSalePrice();
                    }
                }
                clientVo.setOrderSum(orderSum);
            }
            list.add(clientVo);
        }
        return new PageImpl<>(list, pageRequest, page.getTotalElements());
    }

    @Override
    public void delete(Long id, Principal principal) {
        clientTableRepository.delete(id);
    }

    @Override
    public ClientVo info(Long id, Principal principal) {
        ClientTable clientTable = clientTableRepository.findOne(id);
        if (!CommonMethods.isAdmin(principal)) {
            // 判断是否有查看的权限
            if (clientTable.getClientRelatedUserTables() == null) {
                return null;
            } else {
                boolean flag = false;
                for (ClientRelatedUserTable clientRelatedUserTable : clientTable.getClientRelatedUserTables()) {
                    if (clientRelatedUserTable.getRelatedUserTable().getAccount().equals(principal.getName())) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    return null;
                }
            }
        }
        ClientVo clientVo = clientVoAssembler.toResource(clientTable);
        if (CommonMethods.isAdmin(principal)) {
            if (clientTable.getClientRelatedUserTables() != null) {
                List<Long> relatedUserIds = new ArrayList<>();
                List<String> relatedUserAccounts = new ArrayList<>();
                for (ClientRelatedUserTable clientRelatedUserTable : clientTable.getClientRelatedUserTables()) {
                    relatedUserIds.add(clientRelatedUserTable.getRelatedUserTable().getId());
                    relatedUserAccounts.add(clientRelatedUserTable.getRelatedUserTable().getAccount());
                }
                clientVo.setRelatedUserIds(relatedUserIds);
            }
        }
        return clientVo;
    }

    @Override
    public VueResults.Result update(ClientVo clientVo, MultipartFile[] files, Principal principal) {
        ClientTable clientTable = clientTableRepository.findOne(clientVo.getId());
        if (!CommonMethods.isAdmin(principal)) {
            // 判断是否有查看的权限
            if (clientTable.getClientRelatedUserTables() == null) {
                return VueResults.generateError("更新失败", "你没有权限对此客户进行操作");
            } else {
                boolean flag = false;
                for (ClientRelatedUserTable clientRelatedUserTable : clientTable.getClientRelatedUserTables()) {
                    if (clientRelatedUserTable.getRelatedUserTable().getAccount().equals(principal.getName())) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    return VueResults.generateError("更新失败", "你没有权限对此客户进行操作");
                }
            }
        }
        clientVoAssembler.toDomain(clientVo, clientTable);
        if (!clientTable.isTraded()) {
            if (clientVo.getClientLevelId() != null) {
                clientTable.setClientLevelTable(clientLevelTableRepository.findOne(clientVo.getClientLevelId()));
            }
        }
        if (clientVo.getClientOriginId() != null) {
            clientTable.setClientOriginTable(clientOriginTableRepository.findOne(clientVo.getClientOriginId()));
        }
        if (clientVo.getCountryId() != null) {
            clientTable.setCountryTable(countryTableRepository.findOne(clientVo.getCountryId()));
        }
        if (CommonMethods.isAdmin(principal)) {
            List<ClientRelatedUserTable> list = clientTable.getClientRelatedUserTables();
            if (list != null) {
                list.clear();
            } else {
                list = new ArrayList<>();
            }
            if (clientVo.getRelatedUserIds() != null) {
                for (Long relatedUserId : clientVo.getRelatedUserIds()) {
                    ClientRelatedUserTable clientRelatedUserTable = new ClientRelatedUserTable();
                    clientRelatedUserTable.setClientTable(clientTable);
                    clientRelatedUserTable.setRelatedUserTable(relatedUserTableRepository.findOne(relatedUserId));
                    list.add(clientRelatedUserTable);
                }
            }
            clientTable.setClientRelatedUserTables(list);
        }
        clientTableRepository.save(clientTable);
        return null;
    }

    @Override
    public VueResults.Result save(ClientVo clientVo, MultipartFile[] files, Principal principal) {
        // 到此处，要考虑created user，以及是否要做到关联用户里面【当不是admin时】
        ClientTable clientTable = new ClientTable();
        clientVoAssembler.toDomain(clientVo, clientTable);
        clientTable.setTraded(false);
        if (clientVo.getClientLevelId() != null) {
            clientTable.setClientLevelTable(clientLevelTableRepository.findOne(clientVo.getClientLevelId()));
        }
        if (clientVo.getClientOriginId() != null) {
            clientTable.setClientOriginTable(clientOriginTableRepository.findOne(clientVo.getClientOriginId()));
        }
        if (clientVo.getCountryId() != null) {
            clientTable.setCountryTable(countryTableRepository.findOne(clientVo.getCountryId()));
        }
        RelatedUserTable relatedUserTable = relatedUserTableRepository.findOneByAccount(principal.getName());
        List<ClientRelatedUserTable> list = new ArrayList<>();
        if (CommonMethods.isAdmin(principal)) {
            if (clientVo.getRelatedUserIds() != null) {
                for (Long relatedUserId : clientVo.getRelatedUserIds()) {
                    ClientRelatedUserTable clientRelatedUserTable = new ClientRelatedUserTable();
                    clientRelatedUserTable.setClientTable(clientTable);
                    clientRelatedUserTable.setRelatedUserTable(relatedUserTableRepository.findOne(relatedUserId));
                    list.add(clientRelatedUserTable);
                }
            }
        } else {
            ClientRelatedUserTable clientRelatedUserTable = new ClientRelatedUserTable();
            clientRelatedUserTable.setClientTable(clientTable);
            clientRelatedUserTable.setRelatedUserTable(relatedUserTable);
            list.add(clientRelatedUserTable);
        }
        clientTable.setClientRelatedUserTables(list);
        clientTable.setCreatedDate(new Date());
        clientTable.setCreatedUser(relatedUserTable);
        clientTable.setCode(CommonMethods.generateCode("client"));
        clientTableRepository.save(clientTable);
        return null;
    }

    // 查询和自己有关的客户
    @Override
    public List<SelectOption> getClientOptions(Principal principal) {
        List<SelectOption> list = new ArrayList<>();
        if (CommonMethods.isAdmin(principal)) {
            Iterable<ClientTable> clientTables = clientTableRepository.findAll(new Sort(Sort.Direction.ASC, "orderAmount", "id"));
            for (ClientTable clientTable : clientTables) {
                list.add(new SelectOption(clientTable.getName(), clientTable.getId()));
            }
        } else {
            List<ClientTable> clientTables = customClientTableRepository.findByRelatedUserId(relatedUserTableRepository.findOneByAccount(principal.getName()).getId());
            if (clientTables != null && clientTables.size() > 0) {
                for (ClientTable clientTable : clientTables) {
                    list.add(new SelectOption(clientTable.getName(), clientTable.getId()));
                }
            }
        }
        return list;
    }
}
