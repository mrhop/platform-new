package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.domain.NavigateTable;
import cn.hopever.platform.cms.domain.WebsiteTable;
import cn.hopever.platform.cms.repository.ArticleTableRepository;
import cn.hopever.platform.cms.repository.CustomNavigateTableRepository;
import cn.hopever.platform.cms.repository.NavigateTableRepository;
import cn.hopever.platform.cms.repository.WebsiteTableRepository;
import cn.hopever.platform.cms.service.NavigateTableService;
import cn.hopever.platform.cms.vo.NavigateVo;
import cn.hopever.platform.cms.vo.NavigateVoAssembler;
import cn.hopever.platform.utils.web.SelectOption;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.TreeOption;
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
 * 继续将选项部分实现，然后处理后续的其他操作
 */
@Service
@Transactional
public class NavigateTableServiceImpl implements NavigateTableService {

    @Autowired
    private NavigateTableRepository navigateTableRepository;
    @Autowired
    private CustomNavigateTableRepository customNavigateTableRepository;
    @Autowired
    private ArticleTableRepository articleTableRepository;

    @Autowired
    private NavigateVoAssembler navigateVoAssembler;

    @Autowired
    private WebsiteTableRepository websiteTableRepository;

    // website 过滤 parent 过滤
    @Override
    public Page<NavigateVo> getList(TableParameters body, Principal principal) {
        PageRequest pageRequest;
        if (body.getSorts() == null || body.getSorts().isEmpty()) {
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.ASC, "navigateOrder");
        } else {
            String key = body.getSorts().keySet().iterator().next();
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.fromString(body.getSorts().get(key)), key);
        }
        if (body.getFilters() != null && body.getFilters().containsKey("websiteId")) {
            body.getFilters().put("websiteTable", websiteTableRepository.findOne(Long.valueOf(body.getFilters().get("websiteId").toString())));
            body.getFilters().remove("websiteId");
        }
        if (body.getFilters() != null && body.getFilters().containsKey("parentId")) {
            body.getFilters().put("parent", navigateTableRepository.findOne(Long.valueOf(body.getFilters().get("parentId").toString())));
            body.getFilters().remove("parentId");
        }
        Page<NavigateTable> page = customNavigateTableRepository.findByFilters(body.getFilters(), pageRequest);
        List<NavigateVo> list = new ArrayList<>();
        for (NavigateTable navigateTable : page) {
            list.add(navigateVoAssembler.toResource(navigateTable));
        }
        return new PageImpl<NavigateVo>(list, pageRequest, page.getTotalElements());
    }

    @Override
    public void delete(Long id, Principal principal) {
        NavigateTable nt = navigateTableRepository.findOne(id);
        NavigateTable navigateTableBefore = nt.getBeforeNavigate();
        NavigateTable navigateTableAfter = navigateTableRepository.findOneByBeforeNavigate(nt);
        if (navigateTableBefore != null) {
            recursiveNavigateOrderBack(navigateTableBefore);
        }
        if (navigateTableAfter != null) {
            navigateTableAfter.setBeforeNavigate(navigateTableBefore);
            this.navigateTableRepository.save(navigateTableAfter);
        } else if (navigateTableBefore != null) {
            this.navigateTableRepository.save(navigateTableBefore);
        }
        this.navigateTableRepository.delete(nt);
    }

    @Override
    public NavigateVo info(Long id, Principal principal) {
        NavigateTable navigateTable = navigateTableRepository.findOne(id);
        return navigateVoAssembler.toResource(navigateTable);
    }

    @Override
    public VueResults.Result update(NavigateVo navigateVo, MultipartFile[] files, Principal principal) {
        NavigateTable navigateTable = navigateTableRepository.findOne(navigateVo.getId());
        navigateVoAssembler.toDomain(navigateVo, navigateTable);
        if (navigateVo.getArticleId() != null) {
            navigateTable.setArticleTable(articleTableRepository.findOne(navigateVo.getArticleId()));
        }
        NavigateTable navigateTableParent = null;
        if (navigateVo.getParentId() != null) {
            navigateTableParent = navigateTableRepository.findOne(navigateVo.getParentId());
        }
        if (navigateVo.getBeforeId() != null && !(navigateTable.getBeforeNavigate() != null && navigateTable.getBeforeNavigate().getId() == navigateVo.getBeforeId())) {
            NavigateTable navigateTableOld = navigateTableRepository.findOneByBeforeNavigate(navigateTable);
            if (navigateTableOld != null) {
                navigateTableOld.setBeforeNavigate(navigateTable.getBeforeNavigate());
                navigateTableRepository.save(navigateTableOld);
            }
            NavigateTable navigateTable1 = navigateTableRepository.findOne(navigateVo.getBeforeId());
            navigateTable.setBeforeNavigate(navigateTable1);
            NavigateTable navigateTableAfter = navigateTableRepository.findOneByBeforeNavigate(navigateTable1);
            if (navigateTableAfter != null) {
                navigateTableAfter.setBeforeNavigate(navigateTable);
                navigateTableRepository.save(navigateTableAfter);
            }
            navigateTable.setNavigateOrder(navigateTable1.getNavigateOrder());
            recursiveNavigateOrder(navigateTable1);
        } else {
            NavigateTable navigateTable1 = navigateTableRepository.findTopByParentAndWebsiteTableOrderByNavigateOrderDesc(navigateTableParent, navigateTable.getWebsiteTable());
            if (navigateTable1 != null) {
                if (navigateTable.getId() != navigateTable1.getId()) {
                    NavigateTable navigateTableOld = navigateTableRepository.findOneByBeforeNavigate(navigateTable);
                    if (navigateTableOld != null) {
                        navigateTableOld.setBeforeNavigate(navigateTable.getBeforeNavigate());
                        navigateTableRepository.save(navigateTableOld);
                    }
                    navigateTable.setBeforeNavigate(navigateTable1);
                    navigateTable.setNavigateOrder(navigateTable1.getNavigateOrder() + 1);
                }
            } else {
                navigateTable.setBeforeNavigate(null);
                navigateTable.setNavigateOrder(0);
            }
        }
        navigateTable.setParent(navigateTableParent);
        navigateTableRepository.save(navigateTable);
        return null;
    }

    @Override
    public VueResults.Result save(NavigateVo navigateVo, MultipartFile[] files, Principal principal) {
        NavigateTable navigateTable = new NavigateTable();
        navigateTable = navigateVoAssembler.toDomain(navigateVo, navigateTable);
        WebsiteTable websiteTable = null;
        NavigateTable navigateTableParent = null;
        NavigateTable navigateTableAfter = null;

        if (navigateVo.getWebsiteId() != null) {
            websiteTable = websiteTableRepository.findOne(navigateVo.getWebsiteId());
            navigateTable.setWebsiteTable(websiteTable);
        }
        if (navigateVo.getArticleId() != null) {
            navigateTable.setArticleTable(articleTableRepository.findOne(navigateVo.getArticleId()));
        }
        if (navigateVo.getParentId() != null) {
            navigateTableParent = navigateTableRepository.findOne(navigateVo.getParentId());
            navigateTable.setParent(navigateTableParent);
        }
        if (navigateVo.getBeforeId() != null) {
            NavigateTable navigateTable1 = navigateTableRepository.findOne(navigateVo.getBeforeId());
            navigateTable.setBeforeNavigate(navigateTable1);
            navigateTableAfter = navigateTableRepository.findOneByBeforeNavigate(navigateTable1);
            if (navigateTableAfter != null) {
                navigateTableAfter.setBeforeNavigate(navigateTable);
            }
            navigateTable.setNavigateOrder(navigateTable1.getNavigateOrder());
            recursiveNavigateOrder(navigateTable1);
        } else {
            NavigateTable navigateTable1 = navigateTableRepository.findTopByParentAndWebsiteTableOrderByNavigateOrderDesc(navigateTableParent, websiteTable);
            if (navigateTable1 != null) {
                navigateTable.setBeforeNavigate(navigateTable1);
                navigateTable.setNavigateOrder(navigateTable1.getNavigateOrder() + 1);
            } else {
                navigateTable.setNavigateOrder(0);
            }
        }
        navigateTableRepository.save(navigateTable);
        if (navigateTableAfter != null) {
            navigateTableRepository.save(navigateTableAfter);
        }
        return null;
    }

    @Override
    public List<TreeOption> getParentsOptions(Long websiteId, Long id) {
        WebsiteTable websiteTable = websiteTableRepository.findOne(websiteId);
        List<NavigateTable> list = null;
        if (id == null) {
            list = navigateTableRepository.findByWebsiteTableAndParentIsNullOrderByNavigateOrderAsc(websiteTable);
        } else {
            list = navigateTableRepository.findByWebsiteTableAndParentIsNullAndIdNotOrderByNavigateOrderAsc(websiteTable, id);
        }
        List<TreeOption> listReturn = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (NavigateTable navigateTable : list) {
                TreeOption treeOption = recursiveParentsOptions(navigateTable, id);
                if (treeOption != null) {
                    listReturn.add(treeOption);
                }
            }
        }
        return listReturn;
    }

    @Override
    public List<SelectOption> getBeforeOptions(Long parentId, Long websiteId, Long id) {
        List<SelectOption> listReturn = new ArrayList<>();
        NavigateTable navigateTableParent = null;
        List<NavigateTable> list = new ArrayList<>();
        if (parentId != null && parentId != -1) {
            navigateTableParent = navigateTableRepository.findOne(parentId);
            if (id == null) {
                list = navigateTableRepository.findByParentOrderByNavigateOrderAsc(navigateTableParent);
            } else {
                list = navigateTableRepository.findByParentAndIdNotOrderByNavigateOrderAsc(navigateTableParent, id);
            }
        } else {
            if (id == null) {
                list = navigateTableRepository.findByWebsiteTableAndParentIsNullOrderByNavigateOrderAsc(websiteTableRepository.findOne(websiteId));
            } else {
                list = navigateTableRepository.findByWebsiteTableAndParentIsNullAndIdNotOrderByNavigateOrderAsc(websiteTableRepository.findOne(websiteId), id);
            }
        }
        if (list != null && list.size() > 0) {
            for (NavigateTable navigateTable : list) {
                listReturn.add(new SelectOption(navigateTable.getName(), navigateTable.getId()));
            }
        }
        return listReturn;
    }

    @Override
    public VueResults.Result updateActivated(Long id, boolean activated) {
        if (activated) {
            navigateTableRepository.activateArticle(id);
        } else {
            navigateTableRepository.unActivateArticle(id);
        }
        return VueResults.generateSuccess("更新成功", "更新激活状态成功");
    }


    private void recursiveNavigateOrder(NavigateTable navigateTable) {
        navigateTable.setNavigateOrder(navigateTable.getNavigateOrder() - 1);
        if (navigateTable.getBeforeNavigate() != null) {
            recursiveNavigateOrder(navigateTable.getBeforeNavigate());
        }
    }

    private void recursiveNavigateOrderBack(NavigateTable navigateTable) {
        navigateTable.setNavigateOrder(navigateTable.getNavigateOrder() + 1);
        if (navigateTable.getBeforeNavigate() != null) {
            recursiveNavigateOrderBack(navigateTable.getBeforeNavigate());
        }
    }

    private TreeOption recursiveParentsOptions(NavigateTable navigateTable, Long id) {
        if (id == null || id != navigateTable.getId()) {
            TreeOption treeOption = new TreeOption(navigateTable.getId(), navigateTable.getName());
            treeOption.setEmitClick(true);
            treeOption.setIconClass(navigateTable.getIconClass());
            //treeOption.setUrl(navigateTable.getModuleUrl());
            if (navigateTable.getChildren() != null && navigateTable.getChildren().size() > 0) {
                List<TreeOption> list = new ArrayList<>();
                for (NavigateTable navigateTable1 : navigateTable.getChildren()) {
                    TreeOption treeOptionTemp = recursiveParentsOptions(navigateTable1, id);
                    if (treeOptionTemp != null) {
                        list.add(treeOptionTemp);
                    }
                }
                treeOption.setChildren(list);
            }
            return treeOption;
        }
        return null;
    }
}
