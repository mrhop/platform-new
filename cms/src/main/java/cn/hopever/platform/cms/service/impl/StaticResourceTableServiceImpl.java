package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.domain.StaticResourceTable;
import cn.hopever.platform.cms.domain.WebsiteTable;
import cn.hopever.platform.cms.repository.*;
import cn.hopever.platform.cms.service.StaticResourceTableService;
import cn.hopever.platform.cms.vo.StaticResourceVo;
import cn.hopever.platform.cms.vo.StaticResourceVoAssembler;
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
 * 查询会根据theme查询，website查询，article的话需要放在article里面去处理，但是查询放在这里
 * 这三个ID，各自独立，不用关联
 */
@Service
@Transactional
public class StaticResourceTableServiceImpl implements StaticResourceTableService {

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
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize());
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
        if (staticResourceVo.getBeforeId() != null && !(staticResourceTable.getBeforeStaticResource() != null && staticResourceTable.getBeforeStaticResource().getId() == staticResourceVo.getBeforeId())) {
            StaticResourceTable staticResourceTableOld = staticResourceTableRepository.findOneByBeforeStaticResource(staticResourceTable);
            if (staticResourceTableOld != null) {
                staticResourceTableOld.setBeforeStaticResource(staticResourceTable.getBeforeStaticResource());
                staticResourceTableRepository.save(staticResourceTableOld);
            }
            StaticResourceTable staticResourceTable1 = staticResourceTableRepository.findOne(staticResourceVo.getBeforeId());
            staticResourceTable.setBeforeStaticResource(staticResourceTable1);
            StaticResourceTable staticResourceTableAfter = staticResourceTableRepository.findOneByBeforeStaticResource(staticResourceTable1);
            if (staticResourceTableAfter != null) {
                staticResourceTableAfter.setBeforeStaticResource(staticResourceTable);
                staticResourceTableRepository.save(staticResourceTableAfter);
            }
            staticResourceTable.setResourceOrder(staticResourceTable1.getResourceOrder());
            recursiveResourceOrder(staticResourceTable1);
        } else {
            StaticResourceTable staticResourceTable1 = null;
            if (staticResourceTable.getThemeTable() != null) {
                staticResourceTable1 = staticResourceTableRepository.findTopByThemeTableOrderByResourceOrderDesc(staticResourceTable.getThemeTable());
            } else if (staticResourceTable.getThemeTable() != null) {
                staticResourceTable1 = staticResourceTableRepository.findTopByWebsiteTableOrderByResourceOrderDesc(staticResourceTable.getWebsiteTable());
            } else if (staticResourceTable.getThemeTable() != null) {
                staticResourceTable1 = staticResourceTableRepository.findTopByArticleTableOrderByResourceOrderDesc(staticResourceTable.getArticleTable());
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
        return VueResults.generateSuccess("更新成功", "更新资源成功");
    }

    @Override
    public VueResults.Result save(StaticResourceVo staticResourceVo, MultipartFile[] files, Principal principal) {
        // 注意files的处理
        StaticResourceTable staticResourceTable = new StaticResourceTable();
        staticResourceTable = staticResourceVoAssembler.toDomain(staticResourceVo, staticResourceTable);
        WebsiteTable websiteTable = null;
        StaticResourceTable staticResourceTableParent = null;
        StaticResourceTable staticResourceTableAfter = null;

        if (staticResourceVo.getThemeId() != null) {
            staticResourceTable.setThemeTable(themeTableRepository.findOne(staticResourceVo.getThemeId()));
        } else if (staticResourceVo.getWebsiteId() != null) {
            websiteTable = websiteTableRepository.findOne(staticResourceVo.getWebsiteId());
            staticResourceTable.setWebsiteTable(websiteTable);
        } else if (staticResourceVo.getArticleId() != null) {
            staticResourceTable.setArticleTable(articleTableRepository.findOne(staticResourceVo.getArticleId()));
        }
        if (staticResourceVo.getBeforeId() != null) {
            StaticResourceTable staticResourceTable1 = staticResourceTableRepository.findOne(staticResourceVo.getBeforeId());
            staticResourceTable.setBeforeStaticResource(staticResourceTable1);
            staticResourceTableAfter = staticResourceTableRepository.findOneByBeforeStaticResource(staticResourceTable1);
            if (staticResourceTableAfter != null) {
                staticResourceTableAfter.setBeforeStaticResource(staticResourceTable);
            }
            staticResourceTable.setResourceOrder(staticResourceTable1.getResourceOrder());
            recursiveResourceOrder(staticResourceTable1);
        } else {
            StaticResourceTable staticResourceTable1 = null;
            if (staticResourceTable.getThemeTable() != null) {
                staticResourceTable1 = staticResourceTableRepository.findTopByThemeTableOrderByResourceOrderDesc(staticResourceTable.getThemeTable());
            } else if (staticResourceTable.getThemeTable() != null) {
                staticResourceTable1 = staticResourceTableRepository.findTopByWebsiteTableOrderByResourceOrderDesc(staticResourceTable.getWebsiteTable());
            } else if (staticResourceTable.getThemeTable() != null) {
                staticResourceTable1 = staticResourceTableRepository.findTopByArticleTableOrderByResourceOrderDesc(staticResourceTable.getArticleTable());
            }
            if (staticResourceTable1 != null) {
                staticResourceTable.setBeforeStaticResource(staticResourceTable1);
                staticResourceTable.setResourceOrder(staticResourceTable1.getResourceOrder() + 1);
            } else {
                staticResourceTable.setResourceOrder(0);
            }
        }
        staticResourceTableRepository.save(staticResourceTable);
        if (staticResourceTableAfter != null) {
            staticResourceTableRepository.save(staticResourceTableAfter);
        }
        return VueResults.generateSuccess("保存成功", "保存资源成功");
    }


    @Override
    public List<SelectOption> getBeforeOptions(Long scopeId, Long id, String type) {
        List<SelectOption> listReturn = new ArrayList<>();
        List<StaticResourceTable> list = new ArrayList<>();
        if (id == null) {
            if ("theme".equals(type)) {
                list = staticResourceTableRepository.findByThemeTableOrderByResourceOrderAsc(themeTableRepository.findOne(scopeId));
            } else if ("wehsite".equals(type)) {
                list = staticResourceTableRepository.findByWebsiteTableOrderByResourceOrderAsc(websiteTableRepository.findOne(scopeId));
            } else if ("article".equals(type)) {
                list = staticResourceTableRepository.findByArticleTableOrderByResourceOrderAsc(articleTableRepository.findOne(scopeId));
            }
        } else {
            if ("theme".equals(type)) {
                list = staticResourceTableRepository.findByThemeTableAndIdNotOrderByResourceOrderAsc(themeTableRepository.findOne(scopeId), id);
            } else if ("wehsite".equals(type)) {
                list = staticResourceTableRepository.findByWebsiteTableAndIdNotOrderByResourceOrderAsc(websiteTableRepository.findOne(scopeId), id);
            } else if ("article".equals(type)) {
                list = staticResourceTableRepository.findByArticleTableAndIdNotOrderByResourceOrderAsc(articleTableRepository.findOne(scopeId), id);
            }
        }
        if (list != null && list.size() > 0) {
            for (StaticResourceTable staticResourceTable : list) {
                listReturn.add(new SelectOption(staticResourceTable.getName(), staticResourceTable.getId()));
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
