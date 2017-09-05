package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.domain.WebsiteTable;
import cn.hopever.platform.cms.repository.ThemeTableRepository;
import cn.hopever.platform.cms.repository.WebsiteTableRepository;
import cn.hopever.platform.cms.service.WebsiteTableService;
import cn.hopever.platform.cms.vo.WebsiteVo;
import cn.hopever.platform.cms.vo.WebsiteVoAssembler;
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
 * 同样有一个获取website list 而不是page的func
 */
@Service
@Transactional
public class WebsiteTableServiceImpl implements WebsiteTableService {

    @Autowired
    private WebsiteTableRepository websiteTableRepository;
    @Autowired
    private ThemeTableRepository themeTableRepository;
    @Autowired
    private WebsiteVoAssembler websiteVoAssembler;

    @Override
    public Page<WebsiteVo> getList(TableParameters body, Principal principal) {
        PageRequest pageRequest;
        if (body.getSorts() == null || body.getSorts().isEmpty()) {
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize());
        } else {
            String key = body.getSorts().keySet().iterator().next();
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.fromString(body.getSorts().get(key)), key);
        }
        Page<WebsiteTable> page = websiteTableRepository.findAll(pageRequest);
        List<WebsiteVo> list = new ArrayList<>();
        for (WebsiteTable websiteTable : page) {
            list.add(websiteVoAssembler.toResource(websiteTable));
        }
        return new PageImpl<WebsiteVo>(list, pageRequest, page.getTotalElements());
    }

    @Override
    public void delete(Long id, Principal principal) {
        websiteTableRepository.delete(id);
    }

    @Override
    public WebsiteVo info(Long id, Principal principal) {
        WebsiteTable websiteTable = websiteTableRepository.findOne(id);
        return websiteVoAssembler.toResource(websiteTable);
    }

    @Override
    public VueResults.Result update(WebsiteVo websiteVo, MultipartFile[] files, Principal principal) {
        WebsiteTable websiteTable = websiteTableRepository.findOne(websiteVo.getId());
        websiteVoAssembler.toDomain(websiteVo, websiteTable);
        websiteTableRepository.save(websiteTable);
        return VueResults.generateSuccess("更新成功", "更新成功");
    }

    @Override
    //注意需要对文件进行处理
    public VueResults.Result save(WebsiteVo websiteVo, MultipartFile[] files, Principal principal) {
        WebsiteTable websiteTable = new WebsiteTable();
        websiteVoAssembler.toDomain(websiteVo, websiteTable);
        websiteTable.setThemeTable(themeTableRepository.findOne(websiteVo.getThemeId()));
        websiteTableRepository.save(websiteTable);
        return VueResults.generateSuccess("更新成功", "更新成功");
    }

    @Override
    public List<SelectOption> getOptions(Principal principal) {
        Iterable<WebsiteTable> list = websiteTableRepository.findAll();
        List<SelectOption> listReturn = new ArrayList<>();
        for (WebsiteTable websiteTable : list) {
            listReturn.add(new SelectOption(websiteTable.getName(), websiteTable.getId()));
        }
        return listReturn;
    }
}
