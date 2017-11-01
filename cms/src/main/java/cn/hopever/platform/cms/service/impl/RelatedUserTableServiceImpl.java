package cn.hopever.platform.cms.service.impl;


import cn.hopever.platform.cms.domain.RelatedUserTable;
import cn.hopever.platform.cms.repository.RelatedUserTableRepository;
import cn.hopever.platform.cms.service.RelatedUserTableService;
import cn.hopever.platform.cms.vo.RelatedUserVo;
import cn.hopever.platform.cms.vo.RelatedUserVoAssembler;
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
import java.util.Date;
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
        PageRequest pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.DESC, "createdDate");
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
        RelatedUserTable relatedUserTable = relatedUserVoAssembler.toDomain(relatedUserVo, relatedUserTableRepository.findOne(relatedUserVo.getId()));
        relatedUserTableRepository.save(relatedUserTable);
        return null;
    }

    @Override
    public VueResults.Result save(RelatedUserVo relatedUserVo, MultipartFile[] files, Principal principal) {
        RelatedUserTable relatedUserTable = new RelatedUserTable();
        relatedUserVoAssembler.toDomain(relatedUserVo, relatedUserTable);
        relatedUserTable.setCreatedDate(new Date());
        relatedUserTable.setAccount(relatedUserVo.getAccount());
        relatedUserTable.setName(relatedUserVo.getAccount());
        relatedUserTableRepository.save(relatedUserTable);
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
        RelatedUserTable relatedUserTable = relatedUserTableRepository.findOneByAccount(account);

        if (relatedUserTable != null) {
            return relatedUserVoAssembler.toResource(relatedUserTable);
        } else {
            return null;
        }
    }
}
