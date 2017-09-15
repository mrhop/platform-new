package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.config.CmsProperties;
import cn.hopever.platform.cms.domain.ArticleTable;
import cn.hopever.platform.cms.domain.StaticResourceTable;
import cn.hopever.platform.cms.domain.ThemeTable;
import cn.hopever.platform.cms.domain.WebsiteTable;
import cn.hopever.platform.cms.repository.*;
import cn.hopever.platform.cms.service.StaticResourceTableService;
import cn.hopever.platform.cms.vo.StaticResourceVo;
import cn.hopever.platform.cms.vo.StaticResourceVoAssembler;
import cn.hopever.platform.utils.file.FileUtil;
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

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/31.
 * 查询会根据theme查询，website查询，article的话需要放在article里面去处理，但是查询放在这里
 * 这三个ID，各自独立，不用关联
 */
@Service
@Transactional
public class StaticResourceTableServiceImpl implements StaticResourceTableService {

    private Logger logger = LoggerFactory.getLogger(StaticResourceTableServiceImpl.class);

    @Autowired
    private CmsProperties cmsProperties;

    @Autowired
    private StaticResourceTableRepository staticResourceTableRepository;

    @Autowired
    private CustomStaticResourceTableRepository customStaticResourceTableRepository;

    @Autowired
    private StaticResourceVoAssembler staticResourceVoAssembler;

    @Autowired
    private ThemeTableRepository themeTableRepository;
    @Autowired
    private WebsiteTableRepository websiteTableRepository;
    @Autowired
    private ArticleTableRepository articleTableRepository;

    @Override
    public Page<StaticResourceVo> getList(TableParameters body, Principal principal) {
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
        if (body.getFilters() != null && body.getFilters().containsKey("websiteId")) {
            body.getFilters().put("websiteTable", websiteTableRepository.findOne(Long.valueOf(body.getFilters().get("websiteId").toString())));
            body.getFilters().remove("websiteId");
        }
        if (body.getFilters() != null && body.getFilters().containsKey("articleId")) {
            body.getFilters().put("articleTable", themeTableRepository.findOne(Long.valueOf(body.getFilters().get("articleId").toString())));
            body.getFilters().remove("articleId");
        }
        Page<StaticResourceTable> page = customStaticResourceTableRepository.findByFilters(body.getFilters(), pageRequest);
        List<StaticResourceVo> list = new ArrayList<>();
        for (StaticResourceTable staticResourceTable : page) {
            list.add(staticResourceVoAssembler.toResource(staticResourceTable));
        }
        return new PageImpl<StaticResourceVo>(list, pageRequest, page.getTotalElements());
    }

    @Override
    public void delete(Long id, Principal principal) {
        StaticResourceTable nt = staticResourceTableRepository.findOne(id);
        StaticResourceTable staticResourceTableBefore = nt.getBeforeStaticResource();
        StaticResourceTable staticResourceTableAfter = staticResourceTableRepository.findOneByBeforeStaticResource(nt);
        if (staticResourceTableBefore != null) {
            recursiveResourceOrderBack(staticResourceTableBefore);
        }
        if (staticResourceTableAfter != null) {
            staticResourceTableAfter.setBeforeStaticResource(staticResourceTableBefore);
            this.staticResourceTableRepository.save(staticResourceTableAfter);
        } else if (staticResourceTableBefore != null) {
            this.staticResourceTableRepository.save(staticResourceTableBefore);
        }
        this.staticResourceTableRepository.delete(nt);
    }

    @Override
    public StaticResourceVo info(Long id, Principal principal) {
        StaticResourceTable staticResourceTable = staticResourceTableRepository.findOne(id);
        return staticResourceVoAssembler.toResource(staticResourceTable);
    }

    @Override
    public VueResults.Result update(StaticResourceVo staticResourceVo, MultipartFile[] files, Principal principal) {
        // 注意files的处理 替换
        StaticResourceTable staticResourceTable = staticResourceTableRepository.findOne(staticResourceVo.getId());
        staticResourceVoAssembler.toDomain(staticResourceVo, staticResourceTable);
        MultipartFile file = files != null ? files[0] : null;
        if (file != null && !file.isEmpty()) {
            try {
                FileUtil.writeFile(file.getInputStream(), cmsProperties.getStaticResourcePath() + staticResourceTable.getUrl());
                staticResourceTable.setSize(file.getSize());
            } catch (IOException e) {
                logger.error("update error", e);
                return VueResults.generateError("更新失败", "更新资源失败");
            }
        }
        if ("stylesheet".equals(staticResourceTable.getType()) || "script".equals(staticResourceTable.getType()))
            if (staticResourceVo.getBeforeId() != null && !(staticResourceTable.getBeforeStaticResource() != null && staticResourceTable.getBeforeStaticResource().getId() == staticResourceVo.getBeforeId())) {
                StaticResourceTable staticResourceTableOld = staticResourceTableRepository.findOneByBeforeStaticResource(staticResourceTable);
                if (staticResourceTableOld != null) {
                    staticResourceTableOld.setBeforeStaticResource(staticResourceTable.getBeforeStaticResource());
                    staticResourceTableRepository.save(staticResourceTableOld);
                }
                StaticResourceTable staticResourceTable1 = staticResourceTableRepository.findOne(staticResourceVo.getBeforeId());
                StaticResourceTable staticResourceTableAfter = staticResourceTableRepository.findOneByBeforeStaticResource(staticResourceTable1);

                if (staticResourceTableAfter != null) {
                    staticResourceTableAfter.setBeforeStaticResource(staticResourceTable);
                    staticResourceTableRepository.save(staticResourceTableAfter);
                }
                staticResourceTable.setBeforeStaticResource(staticResourceTable1);
                staticResourceTable.setResourceOrder(staticResourceTable1.getResourceOrder());
                recursiveResourceOrder(staticResourceTable1);
            } else {
                StaticResourceTable staticResourceTable1 = null;
                if (staticResourceTable.getThemeTable() != null) {
                    staticResourceTable1 = staticResourceTableRepository.findTopByThemeTableAndTypeOrderByResourceOrderDesc(staticResourceTable.getThemeTable(), staticResourceTable.getType());
                } else if (staticResourceTable.getThemeTable() != null) {
                    staticResourceTable1 = staticResourceTableRepository.findTopByWebsiteTableAndTypeOrderByResourceOrderDesc(staticResourceTable.getWebsiteTable(), staticResourceTable.getType());
                } else if (staticResourceTable.getThemeTable() != null) {
                    staticResourceTable1 = staticResourceTableRepository.findTopByArticleTableAndTypeOrderByResourceOrderDesc(staticResourceTable.getArticleTable(), staticResourceTable.getType());
                }
                if (staticResourceTable1 != null) {
                    if (staticResourceTable.getId() != staticResourceTable1.getId()) {
                        StaticResourceTable staticResourceTableOld = staticResourceTableRepository.findOneByBeforeStaticResource(staticResourceTable);
                        if (staticResourceTableOld != null) {
                            staticResourceTableOld.setBeforeStaticResource(staticResourceTable.getBeforeStaticResource());
                            staticResourceTableRepository.save(staticResourceTableOld);
                        }
                        staticResourceTable.setBeforeStaticResource(staticResourceTable1);
                        staticResourceTable.setResourceOrder(staticResourceTable1.getResourceOrder() + 1);
                    }
                } else {
                    staticResourceTable.setBeforeStaticResource(null);
                    staticResourceTable.setResourceOrder(0);
                }
            }
        staticResourceTableRepository.save(staticResourceTable);
        return null;
    }

    @Override
    public VueResults.Result save(StaticResourceVo staticResourceVo, MultipartFile[] files, Principal principal) {
        MultipartFile file = files != null ? files[0] : null;
        if (file != null && !file.isEmpty()) {
            int cssOrder = 0;
            StaticResourceTable currentCssStaticResourceTable = null;
            int scriptOrder = 0;
            StaticResourceTable currentScriptStaticResourceTable = null;
            ThemeTable themeTable = null;
            WebsiteTable websiteTable = null;
            ArticleTable articleTable = null;
            String globalPath = null;
            if (staticResourceVo.getThemeId() != null) {
                themeTable = themeTableRepository.findOne(staticResourceVo.getThemeId());
                globalPath = "theme/" + themeTable.getThemeId();
                currentCssStaticResourceTable = staticResourceTableRepository.findTopByThemeTableAndTypeOrderByResourceOrderDesc(themeTable, "stylesheet");
                currentScriptStaticResourceTable = staticResourceTableRepository.findTopByThemeTableAndTypeOrderByResourceOrderDesc(themeTable, "script");
            } else if (staticResourceVo.getWebsiteId() != null) {
                websiteTable = websiteTableRepository.findOne(staticResourceVo.getWebsiteId());
                globalPath = "website/" + websiteTable.getWebsiteId();
                currentCssStaticResourceTable = staticResourceTableRepository.findTopByWebsiteTableAndTypeOrderByResourceOrderDesc(websiteTable, "stylesheet");
                currentScriptStaticResourceTable = staticResourceTableRepository.findTopByWebsiteTableAndTypeOrderByResourceOrderDesc(websiteTable, "script");
            } else if (staticResourceVo.getArticleId() != null) {
                articleTable = articleTableRepository.findOne(staticResourceVo.getArticleId());
                globalPath = "acticle/" + articleTable.getWebsiteTable().getWebsiteId() + "/acticle_" + articleTable.getId();
                currentCssStaticResourceTable = staticResourceTableRepository.findTopByArticleTableAndTypeOrderByResourceOrderDesc(articleTable, "stylesheet");
                currentScriptStaticResourceTable = staticResourceTableRepository.findTopByArticleTableAndTypeOrderByResourceOrderDesc(articleTable, "script");
            }
            cssOrder = currentCssStaticResourceTable != null ? currentCssStaticResourceTable.getResourceOrder() : 0;
            scriptOrder = currentScriptStaticResourceTable != null ? currentScriptStaticResourceTable.getResourceOrder() : 0;

            String fileName = file.getOriginalFilename();
            if (fileName.toLowerCase().endsWith(".zip")) {
                //压缩文件不存在order，需要按照顺序读取，并放置在同类型的后面，图片和字体不存在前后顺序
                List<StaticResourceTable> staticResourceTables = new ArrayList<>();
                try {
                    LinkedHashMap<String, Long> map = FileUtil.unZip(file.getInputStream(), cmsProperties.getStaticResourcePath() + globalPath);
                    for (String filePath : map.keySet()) {
                        StaticResourceTable staticResourceTable = staticResourceTableRepository.findOneByUrl(globalPath + "/" + filePath);
                        if (staticResourceTable == null) {
                            staticResourceTable = new StaticResourceTable();
                            staticResourceTable.setName(staticResourceVo.getName() + "-" + FileUtil.getFileName(filePath));
                            staticResourceTable.setArticleTable(articleTable);
                            staticResourceTable.setFilename(FileUtil.getFileName(filePath));
                            staticResourceTable.setFileType(FileUtil.getExtensionName(filePath));
                            staticResourceTable.setThemeTable(themeTable);
                            staticResourceTable.setType(FileUtil.getFileGeneralType(filePath));
                            staticResourceTable.setUrl(globalPath + "/" + filePath);
                            staticResourceTable.setSize(map.get(filePath));
                            staticResourceTable.setWebsiteTable(websiteTable);
                            if ("stylesheet".equals(staticResourceTable.getType())) {
                                staticResourceTable.setResourceOrder(cssOrder);
                                staticResourceTable.setBeforeStaticResource(currentCssStaticResourceTable);
                                currentCssStaticResourceTable = staticResourceTable;
                                cssOrder += 1;
                            } else if ("script".equals(staticResourceTable.getType())) {
                                staticResourceTable.setResourceOrder(scriptOrder);
                                staticResourceTable.setBeforeStaticResource(currentScriptStaticResourceTable);
                                currentScriptStaticResourceTable = staticResourceTable;
                                scriptOrder += 1;
                            }
                        } else {
                            staticResourceTable.setSize(map.get(filePath));
                        }
                        staticResourceTables.add(staticResourceTable);
                    }
                } catch (Exception e) {
                    logger.error("sver error", e);
                    return VueResults.generateError("保存失败", "保存资源失败");
                }
                staticResourceTableRepository.save(staticResourceTables);
            } else {
                globalPath = globalPath + "/static";
                String type = FileUtil.getFileGeneralType(fileName);
                if ("stylesheet".equals(type)) {
                    globalPath += "/css/" + fileName;
                } else if ("script".equals(type)) {
                    globalPath += "/js/" + fileName;
                } else if ("image".equals(type)) {
                    globalPath += "/images/" + fileName;
                } else if ("font".equals(type)) {
                    globalPath += "/fonts/" + fileName;
                } else if ("document".equals(type)) {
                    globalPath += "/docs/" + fileName;
                }
                try {
                    FileUtil.writeFile(file.getInputStream(), cmsProperties.getStaticResourcePath() + globalPath);
                    StaticResourceTable staticResourceTable = staticResourceTableRepository.findOneByUrl(globalPath);
                    if (staticResourceTable == null) {
                        staticResourceTable = new StaticResourceTable();
                        staticResourceTable.setName(staticResourceVo.getName());
                        staticResourceTable.setArticleTable(articleTable);
                        staticResourceTable.setFilename(FileUtil.getFileName(fileName));
                        staticResourceTable.setFileType(FileUtil.getExtensionName(fileName));
                        staticResourceTable.setThemeTable(themeTable);
                        staticResourceTable.setType(type);
                        staticResourceTable.setUrl(globalPath);
                        staticResourceTable.setSize(file.getSize());
                        staticResourceTable.setWebsiteTable(websiteTable);
                        if ("stylesheet".equals(type)) {
                            staticResourceTable.setBeforeStaticResource(currentCssStaticResourceTable);
                            staticResourceTable.setResourceOrder(cssOrder);
                        } else if ("script".equals(type)) {
                            staticResourceTable.setBeforeStaticResource(currentScriptStaticResourceTable);
                            staticResourceTable.setResourceOrder(scriptOrder);
                        }
                    } else {
                        staticResourceTable.setName(staticResourceVo.getName());
                        staticResourceTable.setSize(file.getSize());
                    }
                    staticResourceTableRepository.save(staticResourceTable);
                } catch (IOException e) {
                    logger.error("sver error", e);
                    return VueResults.generateError("保存失败", "保存资源失败");
                }
            }
        }
        return null;
    }


    @Override
    public List<SelectOption> getBeforeOptions(Long id) {
        List<SelectOption> listReturn = new ArrayList<>();
        List<StaticResourceTable> list = new ArrayList<>();
        StaticResourceTable staticResourceTable = staticResourceTableRepository.findOne(id);
        String fileType = staticResourceTable.getType();

        if (staticResourceTable.getThemeTable() != null) {
            list = staticResourceTableRepository.findByThemeTableAndTypeAndIdNotOrderByResourceOrderAsc(staticResourceTable.getThemeTable(), fileType, id);
        } else if (staticResourceTable.getWebsiteTable() != null) {
            list = staticResourceTableRepository.findByWebsiteTableAndTypeAndIdNotOrderByResourceOrderAsc(staticResourceTable.getWebsiteTable(), fileType, id);
        } else if (staticResourceTable.getArticleTable() != null) {
            list = staticResourceTableRepository.findByArticleTableAndTypeAndIdNotOrderByResourceOrderAsc(staticResourceTable.getArticleTable(), fileType, id);
        }
        if (list != null && list.size() > 0) {
            for (StaticResourceTable staticResourceTableTemp : list) {
                listReturn.add(new SelectOption(staticResourceTableTemp.getName(), staticResourceTableTemp.getId()));
            }
        }
        return listReturn;
    }

    private void recursiveResourceOrder(StaticResourceTable staticResourceTable) {
        staticResourceTable.setResourceOrder(staticResourceTable.getResourceOrder() - 1);
        if (staticResourceTable.getBeforeStaticResource() != null) {
            recursiveResourceOrder(staticResourceTable.getBeforeStaticResource());
        }
    }

    private void recursiveResourceOrderBack(StaticResourceTable staticResourceTable) {
        staticResourceTable.setResourceOrder(staticResourceTable.getResourceOrder() + 1);
        if (staticResourceTable.getBeforeStaticResource() != null) {
            recursiveResourceOrderBack(staticResourceTable.getBeforeStaticResource());
        }
    }
}
