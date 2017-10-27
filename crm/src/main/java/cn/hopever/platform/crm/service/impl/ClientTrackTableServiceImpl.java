package cn.hopever.platform.crm.service.impl;

import cn.hopever.platform.crm.config.CommonMethods;
import cn.hopever.platform.crm.domain.ClientTrackTable;
import cn.hopever.platform.crm.repository.ClientTableRepository;
import cn.hopever.platform.crm.repository.ClientTrackTableRepository;
import cn.hopever.platform.crm.repository.CustomClientTrackTableRepository;
import cn.hopever.platform.crm.repository.RelatedUserTableRepository;
import cn.hopever.platform.crm.service.ClientTrackTableService;
import cn.hopever.platform.crm.vo.ClientTrackVo;
import cn.hopever.platform.crm.vo.ClientTrackVoAssembler;
import cn.hopever.platform.utils.tools.BeanUtils;
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
 * Created by Donghui Huo on 2017/10/10.
 */
@Service
@Transactional
public class ClientTrackTableServiceImpl implements ClientTrackTableService {
    @Autowired
    private ClientTrackTableRepository clientTrackTableRepository;

    @Autowired
    private CustomClientTrackTableRepository customClientTrackTableRepository;

    @Autowired
    private ClientTableRepository clientTableRepository;

    @Autowired
    private ClientTrackVoAssembler clientTrackVoAssembler;
    @Autowired
    private RelatedUserTableRepository relatedUserTableRepository;

    @Override
    public Page<ClientTrackVo> getList(TableParameters body, Principal principal) {
        return null;
    }

    @Override
    public void delete(Long id, Principal principal) {
        ClientTrackTable clientTrackTable = clientTrackTableRepository.findOne(id);
        if (CommonMethods.isAdmin(principal) || clientTrackTable.getTrackUser().getAccount().equals(principal.getName())) {
            clientTrackTableRepository.delete(id);
        }
    }

    @Override
    public ClientTrackVo info(Long id, Principal principal) {
        ClientTrackTable clientTrackTable = clientTrackTableRepository.findOne(id);
        if (CommonMethods.isAdmin(principal) || clientTrackTable.getTrackUser().getAccount().equals(principal.getName())) {
            return clientTrackVoAssembler.toResource(clientTrackTable);
        }
        return null;
    }

    @Override
    public VueResults.Result update(ClientTrackVo clientTrackVo, MultipartFile[] files, Principal principal) {
        ClientTrackTable clientTrackTable = clientTrackTableRepository.findOne(clientTrackVo.getId());
        clientTrackVoAssembler.toDomain(clientTrackVo, clientTrackTable);
        if (CommonMethods.isAdmin(principal) || clientTrackTable.getTrackUser().getAccount().equals(principal.getName())) {
            BeanUtils.copyNotNullProperties(clientTrackVo, clientTrackTable);
            clientTrackTableRepository.save(clientTrackTable);
        } else {
            return VueResults.generateError("无法更新", "权限不足");
        }
        return null;
    }

    @Override
    public VueResults.Result save(ClientTrackVo clientTrackVo, MultipartFile[] files, Principal principal) {
        ClientTrackTable clientTrackTable = new ClientTrackTable();
        clientTrackVoAssembler.toDomain(clientTrackVo, clientTrackTable);
        if (clientTrackTable.getTrackDate() == null) {
            clientTrackTable.setTrackDate(new Date());
        }
        clientTrackTable.setClientTable(clientTableRepository.findOne(clientTrackVo.getClientId()));
        clientTrackTable.setTrackUser(relatedUserTableRepository.findOneByAccount(principal.getName()));
        clientTrackTableRepository.save(clientTrackTable);
        return null;
    }

    @Override
    public Page<ClientTrackVo> getList(TableParameters body, Principal principal, Long clientId) {
        Map<String, Object> map = body.getFilters();
        if (map == null) {
            map = new HashMap<>();
        }
        map.put("clientTable", clientTableRepository.findOne(clientId));
        if (!CommonMethods.isAdmin(principal)) {
            map.put("trackUser", relatedUserTableRepository.findOneByAccount(principal.getName()));
        } else {
            if (map.get("trackUserId") != null) {
                map.put("trackUser", relatedUserTableRepository.findOne(Long.valueOf(map.get("trackUserId").toString())));
                map.remove("trackUserId");
            }
        }
        body.setFilters(map);
        PageRequest pageRequest;
        if (body.getSorts() == null || body.getSorts().isEmpty()) {
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.DESC, "trackDate");
        } else {
            String key = body.getSorts().keySet().iterator().next();
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.fromString(body.getSorts().get(key)), key);
        }
        Page<ClientTrackTable> page = customClientTrackTableRepository.findByFilters(body.getFilters(), pageRequest);
        List<ClientTrackVo> list = new ArrayList<>();
        for (ClientTrackTable clientTrackTable : page) {
            ClientTrackVo clientTrackVo = clientTrackVoAssembler.toResource(clientTrackTable);
            list.add(clientTrackVo);
        }
        return new PageImpl<>(list, pageRequest, page.getTotalElements());
    }

    @Override
    public List<Object[]> analyzeClientTrackFromTrackUser(Date beginDate, Date endDate, Long clientId, Long userId) {
        return customClientTrackTableRepository.findClientTrackFromTrackUser(beginDate, endDate, clientId, userId);
    }
}
