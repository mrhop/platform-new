package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.domain.ArticleTable;
import cn.hopever.platform.cms.domain.ArticleTagTable;
import cn.hopever.platform.cms.domain.BlockTable;
import cn.hopever.platform.cms.domain.StaticResourceTable;
import cn.hopever.platform.cms.repository.*;
import cn.hopever.platform.cms.service.ArticleTableService;
import cn.hopever.platform.cms.vo.ArticleVo;
import cn.hopever.platform.cms.vo.ArticleVoAssembler;
import cn.hopever.platform.utils.tools.BeanUtils;
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
 * Created by Donghui Huo on 2017/8/31.
 */
@Service
@Transactional
public class ArticleTableServiceImpl implements ArticleTableService {

    @Autowired
    private ArticleTableRepository articleTableRepository;

    @Autowired
    private WebsiteTableRepository websiteTableRepository;

    @Autowired
    private TemplateTableRepository templateTableRepository;

    @Autowired
    private ArticleTagTableRepository articleTagTableRepository;

    @Autowired
    private CustomArticleTableRepository customArticleTableRepository;

    @Autowired
    private StaticResourceTableRepository staticResourceTableRepository;

    @Autowired
    private ArticleVoAssembler articleVoAssembler;

    @Autowired
    private BlockTableRepository blockTableRepository;

    @Override
    public Page<ArticleVo> getList(TableParameters body, Principal principal) {
        // website -- 该用户可用website
        // 且是否有moduleRole -- 这个在action中实现，使用注解的方式
        // article tag
        // 默认按照创建时间倒序
        // 是否发布 排序
        return getInternalList(body, (short) 0);
    }

    @Override
    public void delete(Long id, Principal principal) {
        articleTableRepository.delete(id);
    }

    @Override
    public ArticleVo info(Long id, Principal principal) {
        ArticleTable articleTable = articleTableRepository.findOne(id);
        return articleVoAssembler.toResourceAll(articleTable);
    }

    @Override
    public VueResults.Result update(ArticleVo articleVo, MultipartFile[] files, Principal principal) {
        this.internalSaveArticle(articleVo, "update", (short) 0, principal);
        return null;
    }

    @Override
    public VueResults.Result save(ArticleVo articleVo, MultipartFile[] files, Principal principal) {
        this.internalSaveArticle(articleVo, "save", (short) 0, principal);
        return null;
    }

    @Override
    public VueResults.Result updatePublished(Long id, boolean published) {
        if (published) {
            articleTableRepository.publishArticle(new Date(), id);
        } else {
            articleTableRepository.unpublishArticle(id);
        }
        return VueResults.generateSuccess("更新成功", "更新发布状态成功");
    }

    @Override
    public VueResults.Result saveNews(ArticleVo articleVo, Principal principal) {
        internalSaveArticle(articleVo, "save", (short) 1, principal);
        return null;
    }

    @Override
    public VueResults.Result saveEvent(ArticleVo articleVo, Principal principal) {
        internalSaveArticle(articleVo, "save", (short) 2, principal);
        return null;
    }

    @Override
    public VueResults.Result updateNews(ArticleVo articleVo, Principal principal) {
        internalSaveArticle(articleVo, "update", (short) 1, principal);
        return null;
    }

    @Override
    public VueResults.Result updateEvent(ArticleVo articleVo, Principal principal) {
        internalSaveArticle(articleVo, "update", (short) 2, principal);
        return null;
    }

    @Override
    public Page<ArticleVo> getNewsList(TableParameters body, Principal principal) {
        return getInternalList(body, (short) 1);
    }

    @Override
    public Page<ArticleVo> getEventList(TableParameters body, Principal principal) {
        return getInternalList(body, (short) 2);
    }

    @Override
    public VueResults.Result updatePublished(Long id, boolean published, Principal principal) {
        if (published) {
            articleTableRepository.publishArticle(new Date(), id);
        } else {
            articleTableRepository.unpublishArticle(id);
        }
        return VueResults.generateSuccess("更新成功", "更新发布状态成功");
    }

    @Override
    public List<SelectOption> getArticleOptionsForNavigate(short relateType, Long websiteId) {
        List<ArticleTable> list = articleTableRepository.findByWebsiteTableAndType(websiteTableRepository.findOne(websiteId), relateType);
        List<SelectOption> returnList = new ArrayList<>();
        for (ArticleTable articleTable : list) {
            returnList.add(new SelectOption(articleTable.getTitle(), articleTable.getId()));
        }
        return returnList;
    }

    @Override
    public void copy(Long id) {
        ArticleTable articleTable = articleTableRepository.findOne(id);
        List<BlockTable> list = blockTableRepository.findByArticleTableAndTemplateTableOrderByPositionAsc(articleTable, articleTable.getTemplateTable());
        ArticleTable articleTable1 = new ArticleTable();
        BeanUtils.copyNotNullProperties(articleTable, articleTable1, "id", "articleTagTables", "blockTables");
        List<ArticleTagTable> articleTagTables = articleTable.getArticleTagTables();
        if (articleTagTables != null && articleTagTables.size() > 0) {
            List<ArticleTagTable> articleTagTables1 = new ArrayList<>();
            articleTagTables1.addAll(articleTagTables);
            articleTable1.setArticleTagTables(articleTagTables1);
        }
        if (articleTable.getStaticResourceTables() != null && articleTable.getStaticResourceTables().size() > 0) {
            List<StaticResourceTable> staticResourceTables1 = new ArrayList<>();
            for (StaticResourceTable staticResourceTable : articleTable.getStaticResourceTables()) {
                if (!"script".equals(staticResourceTable.getType()) && !"stylesheet".equals(staticResourceTable.getType())) {
                    StaticResourceTable staticResourceTable1 = new StaticResourceTable();
                    BeanUtils.copyNotNullProperties(staticResourceTable, staticResourceTable1, "id", "articleTable", "beforeStaticResource");
                    staticResourceTable1.setArticleTable(articleTable1);
                    staticResourceTables1.add(staticResourceTable1);
                }
            }
            StaticResourceTable staticResourceTableBottom = staticResourceTableRepository.findTopByArticleTableAndTypeOrderByResourceOrderDesc(articleTable, "stylesheet");
            if (staticResourceTableBottom != null) {
                staticResourceTables1.add(recursiveStaticResourceTables(staticResourceTableBottom, articleTable1));
            }
            staticResourceTableBottom = staticResourceTableRepository.findTopByArticleTableAndTypeOrderByResourceOrderDesc(articleTable, "script");
            if (staticResourceTableBottom != null) {
                staticResourceTables1.add(recursiveStaticResourceTables(staticResourceTableBottom, articleTable1));
            }
            articleTable1.setStaticResourceTables(staticResourceTables1);
        }
        if (list != null && list.size() > 0) {
            List<BlockTable> list1 = new ArrayList<>();
            for (BlockTable blockTable : list) {
                BlockTable blockTable1 = new BlockTable();
                BeanUtils.copyNotNullProperties(blockTable, blockTable1, "id", "articleTable");
                blockTable1.setArticleTable(articleTable1);
                list1.add(blockTable1);
            }
            articleTable1.setBlockTables(list1);
        }
        articleTableRepository.save(articleTable1);
    }

    private Page<ArticleVo> getInternalList(TableParameters body, short type) {
        PageRequest pageRequest;
        if (body.getSorts() == null || body.getSorts().isEmpty()) {
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.DESC, "createdDate");
        } else {
            String key = body.getSorts().keySet().iterator().next();
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.fromString(body.getSorts().get(key)), key);
        }
        if (body.getFilters() != null && body.getFilters().containsKey("websiteId")) {
            body.getFilters().put("websiteTable", websiteTableRepository.findOne(Long.valueOf(body.getFilters().get("websiteId").toString())));
            body.getFilters().remove("websiteId");
        }
        if (body.getFilters() != null && body.getFilters().containsKey("articleTagsStr")) {
            body.getFilters().put("articleTagId", Long.valueOf(body.getFilters().get("articleTagsStr").toString()));
            body.getFilters().remove("articleTagsStr");
        }

        body.getFilters().put("type", type);
        Page<ArticleTable> page = customArticleTableRepository.findByFilters(body.getFilters(), pageRequest);
        List<ArticleVo> list = new ArrayList<>();
        for (ArticleTable articleTable : page) {
            list.add(articleVoAssembler.toResource(articleTable));
        }
        return new PageImpl<ArticleVo>(list, pageRequest, page.getTotalElements());
    }

    private void internalSaveArticle(ArticleVo articleVo, String operation, short type, Principal principal) {
        ArticleTable articleTable = null;
        if ("save".equals(operation)) {
            articleTable = new ArticleTable();
            articleTable.setType(type);
        } else {
            articleTable = articleTableRepository.findOne(articleVo.getId());
        }
        articleVoAssembler.toDomain(articleVo, articleTable);
        List<ArticleTagTable> list = new ArrayList<>();
        if (articleVo.getArticleTags() != null) {
            for (Long id : articleVo.getArticleTags()) {
                ArticleTagTable articleTagTable = articleTagTableRepository.findOne(id);
                if (articleTagTable.getArticleTables() != null) {
                    if (!articleTagTable.getArticleTables().contains(articleTable)) {
                        articleTagTable.getArticleTables().add(articleTable);
                    }
                } else {
                    List<ArticleTable> list1 = new ArrayList<>();
                    list1.add(articleTable);
                    articleTagTable.setArticleTables(list1);
                }
                list.add(articleTagTable);
            }
        }
        articleTable.setArticleTagTables(list);
        if (articleTable.isPublished() && articleTable.getPublishDate() == null) {
            articleTable.setPublishDate(new Date());
        }
        if (articleVo.getTemplateId() != null) {
            articleTable.setTemplateTable(templateTableRepository.findOne(articleVo.getTemplateId()));
        }
        if ("save".equals(operation)) {
            articleTable.setCreatedDate(new Date());
            articleTable.setCreateUser(principal.getName());
            if (articleVo.getWebsiteId() != null) {
                articleTable.setWebsiteTable(websiteTableRepository.findOne(articleVo.getWebsiteId()));
            }
            if (articleVo.getBlocks() != null && articleVo.getBlocks().size() > 0) {
                List<BlockTable> blockTables = new ArrayList<>();
                for (List<String> list1 : articleVo.getBlocks()) {
                    BlockTable blockTable = new BlockTable();
                    blockTable.setName(list1.get(0));
                    blockTable.setPosition(list1.get(1));
                    blockTable.setContent(list1.get(2));
                    blockTable.setScript(list1.get(3));
                    blockTables.add(blockTable);
                    blockTable.setArticleTable(articleTable);
                    blockTable.setTemplateTable(articleTable.getTemplateTable());
                    blockTables.add(blockTable);
                }
                blockTableRepository.save(blockTables);
            }
        }
        articleTableRepository.save(articleTable);
    }


    private StaticResourceTable recursiveStaticResourceTables(StaticResourceTable staticResourceTable, ArticleTable articleTable) {
        StaticResourceTable staticResourceTable1 = new StaticResourceTable();
        BeanUtils.copyNotNullProperties(staticResourceTable, staticResourceTable1, "id", "articleTable", "beforeStaticResource");
        staticResourceTable1.setArticleTable(articleTable);
        if (staticResourceTable.getBeforeStaticResource() != null) {
            staticResourceTable1.setBeforeStaticResource(recursiveStaticResourceTables(staticResourceTable.getBeforeStaticResource(), articleTable));
        }
        return staticResourceTable1;
    }
}
