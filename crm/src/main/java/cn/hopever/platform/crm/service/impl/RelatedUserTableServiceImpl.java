package cn.hopever.platform.crm.service.impl;

import cn.hopever.platform.crm.domain.RelatedUserTable;
import cn.hopever.platform.crm.repository.RelatedUserTableRepository;
import cn.hopever.platform.crm.service.RelatedUserTableService;
import cn.hopever.platform.crm.vo.RelatedUserVo;
import cn.hopever.platform.crm.vo.RelatedUserVoAssembler;
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
 * 登陆的时候添加
 */
@Service
@Transactional
public class RelatedUserTableServiceImpl implements RelatedUserTableService {
    @Autowired
    private RelatedUserTableRepository relatedUserTableRepository;

    @Autowired
    private RelatedUserVoAssembler relatedUserVoAssembler;

    @Override
    public Page<RelatedUserVo> getList(TableParameters body, Principal principal) {
        PageRequest pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.ASC, "id");
        Page<RelatedUserTable> page = relatedUserTableRepository.findAll(pageRequest);
        List<RelatedUserVo> list = new ArrayList<>();
        for (RelatedUserTable relatedUserTable : page) {
            list.add(relatedUserVoAssembler.toResource(relatedUserTable));
        }
        return new PageImpl<>(list, pageRequest, page.getTotalElements());
    }

    @Override
    public void delete(Long id, Principal principal) {

    }

    @Override
    public RelatedUserVo info(Long id, Principal principal) {
        return relatedUserVoAssembler.toResource(relatedUserTableRepository.findOne(id));
    }

    @Override
    public VueResults.Result update(RelatedUserVo relatedUserVo, MultipartFile[] files, Principal principal) {
        relatedUserTableRepository.save(relatedUserVoAssembler.toDomain(relatedUserVo, relatedUserTableRepository.findOne(relatedUserVo.getId())));
        return null;
    }

    @Override
    public VueResults.Result save(RelatedUserVo relatedUserVo, MultipartFile[] files, Principal principal) {
        return null;
    }

    @Override
    public List<SelectOption> getRelatedUserOptions(Principal principal) {
        List<SelectOption> list = new ArrayList<>();
        Iterable<RelatedUserTable> relatedUserTables = relatedUserTableRepository.findAll();
        if (relatedUserTables != null) {
            for (RelatedUserTable relatedUserTable : relatedUserTables) {
                SelectOption selectOption = new SelectOption(relatedUserTable.getAccount(), relatedUserTable.getId());
                list.add(selectOption);
            }
        }
        return list;
    }

    @Override
    public RelatedUserVo getOneByAccount(String account) {
        return relatedUserVoAssembler.toResource(relatedUserTableRepository.findOneByAccount(account));
    }
}
