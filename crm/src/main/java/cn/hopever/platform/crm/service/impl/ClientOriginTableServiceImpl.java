package cn.hopever.platform.crm.service.impl;

import cn.hopever.platform.crm.domain.ClientOriginTable;
import cn.hopever.platform.crm.domain.ClientTable;
import cn.hopever.platform.crm.repository.ClientOriginTableRepository;
import cn.hopever.platform.crm.service.ClientOriginTableService;
import cn.hopever.platform.crm.vo.ClientOriginVo;
import cn.hopever.platform.crm.vo.ClientOriginVoAssembler;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/10/10.
 */
@Service
@Transactional
public class ClientOriginTableServiceImpl implements ClientOriginTableService {

    @Autowired
    private ClientOriginTableRepository clientOriginTableRepository;

    @Autowired
    private ClientOriginVoAssembler clientOriginVoAssembler;

    @Override
    public Page<ClientOriginVo> getList(TableParameters body, Principal principal) {
        PageRequest pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.ASC, "id");
        Page<ClientOriginTable> page = clientOriginTableRepository.findAll(pageRequest);
        List<ClientOriginVo> list = new ArrayList<>();
        for (ClientOriginTable clientOriginTable : page) {
            list.add(clientOriginVoAssembler.toResource(clientOriginTable));
        }
        return new PageImpl<>(list, pageRequest, page.getTotalElements());
    }

    @Override
    public void delete(Long id, Principal principal) {
        ClientOriginTable clientOriginTable = clientOriginTableRepository.findOne(id);
        for (ClientTable clientTable : clientOriginTable.getClientTables()) {
            clientTable.setClientOriginTable(null);
        }
        clientOriginTableRepository.delete(clientOriginTable);
    }

    @Override
    public ClientOriginVo info(Long id, Principal principal) {
        return clientOriginVoAssembler.toResource(clientOriginTableRepository.findOne(id));
    }

    @Override
    public VueResults.Result update(ClientOriginVo clientOriginVo, MultipartFile[] files, Principal principal) {
        clientOriginTableRepository.save(clientOriginVoAssembler.toDomain(clientOriginVo, clientOriginTableRepository.findOne(clientOriginVo.getId())));
        return null;
    }

    @Override
    public VueResults.Result save(ClientOriginVo clientOriginVo, MultipartFile[] files, Principal principal) {
        clientOriginTableRepository.save(clientOriginVoAssembler.toDomain(clientOriginVo, new ClientOriginTable()));
        return null;
    }

    @Override
    public List<SelectOption> getClientOriginOptions(Principal principal) {
        Iterable<ClientOriginTable> clientOriginTables = clientOriginTableRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        List<SelectOption> list = new ArrayList<>();
        for (ClientOriginTable clientOriginTable : clientOriginTables) {
            list.add(new SelectOption(clientOriginTable.getName(), clientOriginTable.getId()));
        }
        return list;
    }
}
