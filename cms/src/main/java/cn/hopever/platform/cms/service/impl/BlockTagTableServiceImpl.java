package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.domain.BlockTagTable;
import cn.hopever.platform.cms.repository.BlockTagTableRepository;
import cn.hopever.platform.cms.service.BlockTagTableService;
import cn.hopever.platform.cms.vo.BlockTagVo;
import cn.hopever.platform.cms.vo.BlockTagVoAssembler;
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
public class BlockTagTableServiceImpl implements BlockTagTableService {
    @Autowired
    private BlockTagTableRepository blockTagTableRepository;
    @Autowired
    private BlockTagVoAssembler blockTagVoAssembler;

    @Override
    public Page<BlockTagVo> getList(TableParameters body, Principal principal) {
        PageRequest pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.ASC, "id");
        Page<BlockTagTable> page = blockTagTableRepository.findAll(pageRequest);
        List<BlockTagVo> list = new ArrayList<>();
        for (BlockTagTable blockTagTable : page) {
            list.add(blockTagVoAssembler.toResource(blockTagTable));
        }
        return new PageImpl<BlockTagVo>(list, pageRequest, page.getTotalElements());
    }

    @Override
    public void delete(Long id, Principal principal) {
        blockTagTableRepository.delete(id);
    }

    @Override
    public BlockTagVo info(Long id, Principal principal) {
        BlockTagTable blockTagTable = blockTagTableRepository.findOne(id);
        return blockTagVoAssembler.toResource(blockTagTable);
    }

    @Override
    public VueResults.Result update(BlockTagVo blockTagVo, MultipartFile[] files, Principal principal) {
        BlockTagTable blockTagTable = blockTagTableRepository.findOne(blockTagVo.getId());
        blockTagVoAssembler.toDomain(blockTagVo, blockTagTable);
        blockTagTableRepository.save(blockTagTable);
        return null;
    }

    @Override
    public VueResults.Result save(BlockTagVo blockTagVo, MultipartFile[] files, Principal principal) {
        if (blockTagTableRepository.findOneByTagId(blockTagVo.getTagId()) != null) {
            return VueResults.generateError("创建失败", "tagID已存在");
        }
        BlockTagTable blockTagTable = new BlockTagTable();
        blockTagVoAssembler.toDomain(blockTagVo, blockTagTable);
        blockTagTableRepository.save(blockTagTable);
        return null;
    }

    @Override
    public List<SelectOption> getOptions() {
        List<SelectOption> listReturn = new ArrayList<>();
        Iterable<BlockTagTable> list = blockTagTableRepository.findAll();
        for (BlockTagTable blockTagTable : list) {
            listReturn.add(new SelectOption(blockTagTable.getName(), blockTagTable.getTagId()));
        }
        return listReturn;
    }
}
