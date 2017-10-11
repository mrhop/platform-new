package cn.hopever.platform.crm.service.impl;

import cn.hopever.platform.crm.domain.ClientLevelTable;
import cn.hopever.platform.crm.repository.ClientLevelTableRepository;
import cn.hopever.platform.crm.service.ClientLevelTableService;
import cn.hopever.platform.crm.vo.ClientLevelVo;
import cn.hopever.platform.crm.vo.ClientLevelVoAssembler;
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
 * Created by Donghui Huo on 2017/8/31.
 */
@Service
@Transactional
public class ClientLevelTableServiceImpl implements ClientLevelTableService {

    @Autowired
    private ClientLevelTableRepository clientLevelTableRepository;

    @Autowired
    private ClientLevelVoAssembler clientLevelVoAssembler;

    @Override
    public Page<ClientLevelVo> getList(TableParameters body, Principal principal) {
        PageRequest pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.ASC, "orderAmount", "id");
        Page<ClientLevelTable> page = clientLevelTableRepository.findAll(pageRequest);
        List<ClientLevelVo> list = new ArrayList<>();
        for (ClientLevelTable clientLevelTable : page) {
            list.add(clientLevelVoAssembler.toResource(clientLevelTable));
        }
        return new PageImpl<>(list, pageRequest, page.getTotalElements());
    }

    @Override
    public void delete(Long id, Principal principal) {

    }

    @Override
    public ClientLevelVo info(Long id, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result update(ClientLevelVo clientLevelVo, MultipartFile[] files, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result save(ClientLevelVo clientLevelVo, MultipartFile[] files, Principal principal) {
        return null;
    }

    @Override
    public List<SelectOption> getClientLevelOptions(Principal principal) {
        return null;
    }

    @Override
    public List<SelectOption> getClientLevelNoOrderAmountOptions(Principal principal) {
        return null;
    }
}
