package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.domain.BlockTable;
import cn.hopever.platform.cms.domain.TemplateTable;
import cn.hopever.platform.cms.domain.ThemeTable;
import cn.hopever.platform.cms.domain.WebsiteTable;
import cn.hopever.platform.cms.repository.*;
import cn.hopever.platform.cms.service.WebsiteTableService;
import cn.hopever.platform.cms.vo.WebsiteVo;
import cn.hopever.platform.cms.vo.WebsiteVoAssembler;
import cn.hopever.platform.utils.moji.MojiUtils;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.SelectOption;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Map;

/**
 * Created by Donghui Huo on 2017/8/31.
 * 同样只是基本操作
 * 同样有一个获取website list 而不是page的func
 */
@Service
@Transactional
public class WebsiteTableServiceImpl implements WebsiteTableService {

    Logger logger = LoggerFactory.getLogger(WebsiteTableServiceImpl.class);

    @Autowired
    private MojiUtils mojiUtils;

    @Autowired
    private WebsiteTableRepository websiteTableRepository;
    @Autowired
    private CustomWebsiteTableRepository customWebsiteTableRepository;
    @Autowired
    private ThemeTableRepository themeTableRepository;
    @Autowired
    private WebsiteVoAssembler websiteVoAssembler;
    @Autowired
    private TemplateTableRepository templateTableRepository;
    @Autowired
    private BlockTableRepository blockTableRepository;

    @Override
    public Page<WebsiteVo> getList(TableParameters body, Principal principal) {
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
        Page<WebsiteTable> page = customWebsiteTableRepository.findByFilters(body.getFilters(), pageRequest);
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
        List<String> preScreenShots = websiteTable.getScreenshots();
        if (files != null && files.length > 0) {
            try {
                Map<String, List<String>> mapScreenshots = mojiUtils.uploadImg("cms/website/" + websiteTable.getWebsiteId() + "/screenshots", files);
                List<String> list = mapScreenshots.get("fileKeys");
                if (list != null && list.size() > 0) {
                    if (preScreenShots != null) {
                        for (int i = 0; i < files.length; i++) {
                            MultipartFile file = files[i];
                            if (!file.isEmpty()) {
                                if (i >= preScreenShots.size()) {
                                    preScreenShots.add(list.remove(0));
                                }
                            } else {
                                preScreenShots.set(i, list.remove(0));
                            }
                        }
                        websiteTable.setScreenshots(preScreenShots);
                    } else {
                        websiteTable.setScreenshots(list);
                    }
                }
            } catch (Exception e) {
                logger.error("update theme screemshots failed", e);
            }
        }
        websiteTableRepository.save(websiteTable);
        return null;
    }

    @Override
    //注意需要对文件进行处理
    public VueResults.Result save(WebsiteVo websiteVo, MultipartFile[] files, Principal principal) {
        WebsiteTable websiteTable = new WebsiteTable();
        websiteVoAssembler.toDomain(websiteVo, websiteTable);
        ThemeTable themeTable = themeTableRepository.findOne(websiteVo.getThemeId());
        websiteTable.setThemeTable(themeTable);
        if (files != null && files.length > 0) {
            try {
                Map<String, List<String>> mapScreenshots = mojiUtils.uploadImg("cms/website/" + websiteTable.getWebsiteId() + "/screenshots/", files);
                List<String> list = mapScreenshots.get("fileKeys");
                if (list != null && list.size() > 0) {
                    websiteTable.setScreenshots(list);
                }
            } catch (Exception e) {
                logger.error("save theme screenshots failed", e);
            }
        }
        websiteTableRepository.save(websiteTable);
        if (themeTable.getTemplateTables() != null) {
            List<TemplateTable> templateTables = new ArrayList<>();
            List<BlockTable> blockTables = new ArrayList<>();
            for (TemplateTable templateTable : themeTable.getTemplateTables()) {
                TemplateTable templateTableTemp = new TemplateTable();
                BeanUtils.copyNotNullProperties(templateTable, templateTableTemp, "themeTable", "blockTables", "articleTables");
                templateTableTemp.setWebsiteTable(websiteTable);
                templateTables.add(templateTableTemp);
                if (templateTable.getBlockTables() != null) {
                    for (BlockTable blockTable : templateTable.getBlockTables()) {
                        BlockTable blockTableTemp = new BlockTable();
                        BeanUtils.copyNotNullProperties(blockTable, blockTableTemp, "templateTable", "articleTable");
                        blockTableTemp.setTemplateTable(templateTableTemp);
                        blockTables.add(blockTableTemp);
                    }
                }
            }
            templateTableRepository.save(templateTables);
            if (blockTables.size() > 0) {
                blockTableRepository.save(blockTables);
            }
        }
        return null;
    }

    @Override
    public List<SelectOption> getOptions() {
        Iterable<WebsiteTable> list = websiteTableRepository.findAll();
        List<SelectOption> listReturn = new ArrayList<>();
        for (WebsiteTable websiteTable : list) {
            listReturn.add(new SelectOption(websiteTable.getName(), websiteTable.getId()));
        }
        return listReturn;
    }

    @Override
    public List<SelectOption> getOptions(Principal principal) {
        Iterable<WebsiteTable> list = websiteTableRepository.findAll();
        List<SelectOption> listReturn = new ArrayList<>();
        for (WebsiteTable websiteTable : list) {
            if (websiteTable.getRelatedUsers().contains(principal.getName())) {
                listReturn.add(new SelectOption(websiteTable.getName(), websiteTable.getId()));
            }
        }
        return listReturn;
    }
}
