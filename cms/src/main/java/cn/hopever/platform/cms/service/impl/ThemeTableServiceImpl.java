package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.domain.ThemeTable;
import cn.hopever.platform.cms.repository.ThemeTableRepository;
import cn.hopever.platform.cms.service.ThemeTableService;
import cn.hopever.platform.cms.vo.ThemeVo;
import cn.hopever.platform.cms.vo.ThemeVoAssembler;
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
 * 同样只是基本操作
 * 同样有一个获取theme list 而不是page的func
 */
@Service
@Transactional
public class ThemeTableServiceImpl implements ThemeTableService {

    @Autowired
    private ThemeTableRepository themeTableRepository;
    @Autowired
    private ThemeVoAssembler themeVoAssembler;

    @Override
    public Page<ThemeVo> getList(TableParameters body, Principal principal) {
        PageRequest pageRequest;
        if (body.getSorts() == null || body.getSorts().isEmpty()) {
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize());
        } else {
            String key = body.getSorts().keySet().iterator().next();
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.fromString(body.getSorts().get(key)), key);
        }

        Page<ThemeTable> page = themeTableRepository.findAll(pageRequest);
        List<ThemeVo> list = new ArrayList<>();
        for (ThemeTable themeTable : page) {
            list.add(themeVoAssembler.toResource(themeTable));
        }
        return new PageImpl<ThemeVo>(list, pageRequest, page.getTotalElements());
    }

    @Override
    public void delete(Long id, Principal principal) {
        this.themeTableRepository.delete(id);
    }

    @Override
    public ThemeVo info(Long id, Principal principal) {
        ThemeTable themeTable = themeTableRepository.findOne(id);
        return themeVoAssembler.toResource(themeTable);
    }

    @Override
    //需处理files
    // 可以更新截图文件，如何确定是更新，并删除之前的呢？
    public VueResults.Result update(ThemeVo themeVo, MultipartFile[] files, Principal principal) {
        ThemeTable themeTable = themeTableRepository.findOne(themeVo.getId());
        themeVoAssembler.toDomain(themeVo, themeTable);
        themeTableRepository.save(themeTable);
        return VueResults.generateSuccess("更新成功", "更新成功");
    }

    @Override
    //需处理files
    public VueResults.Result save(ThemeVo themeVo, MultipartFile[] files, Principal principal) {
        ThemeTable themeTable = new ThemeTable();
        themeVoAssembler.toDomain(themeVo, themeTable);
        themeTableRepository.save(themeTable);
        return VueResults.generateSuccess("创建成功", "创建成功");
    }

    @Override
    public List<SelectOption> getOptions(Principal principal) {
        Iterable<ThemeTable> list = themeTableRepository.findAll();
        List<SelectOption> listReturn = new ArrayList<>();
        for (ThemeTable themeTable : list) {
            listReturn.add(new SelectOption(themeTable.getName(), themeTable.getId()));
        }
        return listReturn;
    }
}
