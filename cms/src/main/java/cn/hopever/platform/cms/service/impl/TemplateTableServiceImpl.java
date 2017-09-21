package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.domain.BlockTable;
import cn.hopever.platform.cms.domain.TemplateTable;
import cn.hopever.platform.cms.repository.BlockTableRepository;
import cn.hopever.platform.cms.repository.CustomTemplateTableRepository;
import cn.hopever.platform.cms.repository.TemplateTableRepository;
import cn.hopever.platform.cms.repository.ThemeTableRepository;
import cn.hopever.platform.cms.service.TemplateTableService;
import cn.hopever.platform.cms.vo.TemplateVo;
import cn.hopever.platform.cms.vo.TemplateVoAssembler;
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
 * 只做最基本的处理，但是有一个根据themeId获取所有template的options,尽量不要删除template，因为涉及到太多的关联信息，就如website以及theme
 */
@Service
@Transactional
public class TemplateTableServiceImpl implements TemplateTableService {
    @Autowired
    private TemplateTableRepository templateTableRepository;
    @Autowired
    private CustomTemplateTableRepository customTemplateTableRepository;
    @Autowired
    private TemplateVoAssembler templateVoAssembler;
    @Autowired
    private ThemeTableRepository themeTableRepository;
    @Autowired
    private BlockTableRepository blockTableRepository;

    @Override
    public Page<TemplateVo> getList(TableParameters body, Principal principal) {
        PageRequest pageRequest;
        if (body.getSorts() == null || body.getSorts().isEmpty()) {
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.ASC, "id");
        } else {
            String key = body.getSorts().keySet().iterator().next();
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.fromString(body.getSorts().get(key)), key);
        }
        if (body.getFilters() != null && body.getFilters().containsKey("themeId")) {
            body.getFilters().put("themeTable", themeTableRepository.findOne(Long.valueOf(body.getFilters().get("themeId").toString())));
            body.getFilters().remove("themeId");
        }
        Page<TemplateTable> page = customTemplateTableRepository.findByFilters(body.getFilters(), pageRequest);
        List<TemplateVo> list = new ArrayList<>();
        for (TemplateTable templateTable : page) {
            list.add(templateVoAssembler.toResource(templateTable));
        }
        return new PageImpl<TemplateVo>(list, pageRequest, page.getTotalElements());
    }

    @Override
    public void delete(Long id, Principal principal) {
        templateTableRepository.delete(id);
    }

    @Override
    public TemplateVo info(Long id, Principal principal) {
        TemplateTable templateTable = templateTableRepository.findOne(id);
        return templateVoAssembler.toResource(templateTable);
    }

    @Override
    public VueResults.Result update(TemplateVo templateVo, MultipartFile[] files, Principal principal) {
        TemplateTable templateTable = templateTableRepository.findOne(templateVo.getId());
        templateVoAssembler.toDomain(templateVo, templateTable);
        templateTableRepository.save(templateTable);
        return null;
    }

    @Override
    public VueResults.Result save(TemplateVo templateVo, MultipartFile[] files, Principal principal) {
        TemplateTable templateTable = new TemplateTable();
        templateVoAssembler.toDomain(templateVo, templateTable);
        templateTable.setThemeTable(themeTableRepository.findOne(templateVo.getThemeId()));
        templateTableRepository.save(templateTable);
        if (templateVo.getBlocks() != null && templateVo.getBlocks().size() > 0) {
            List<BlockTable> blockTables = new ArrayList<>();
            for (List<String> list : templateVo.getBlocks()) {
                BlockTable blockTable = new BlockTable();
                blockTable.setTemplateTable(templateTable);
                blockTable.setName(list.get(0));
                blockTable.setPosition(list.get(1));
                blockTable.setContent(list.get(2));
                blockTable.setScript(list.get(3));
                blockTables.add(blockTable);
            }
            blockTableRepository.save(blockTables);
        }
        return null;
    }


    @Override
    public List<SelectOption> getOptionsByThemeId(Long themeId) {
        List<SelectOption> listReturn = new ArrayList<>();
        List<TemplateTable> list = templateTableRepository.findByThemeTable(themeTableRepository.findOne(themeId));
        for (TemplateTable templateTable : list) {
            listReturn.add(new SelectOption(templateTable.getName(), templateTable.getId()));
        }
        return listReturn;
    }
}
