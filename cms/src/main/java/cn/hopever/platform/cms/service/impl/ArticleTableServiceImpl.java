package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.config.CmsProperties;
import cn.hopever.platform.cms.domain.*;
import cn.hopever.platform.cms.repository.*;
import cn.hopever.platform.cms.service.ArticleTableService;
import cn.hopever.platform.cms.vo.ArticleVo;
import cn.hopever.platform.cms.vo.ArticleVoAssembler;
import cn.hopever.platform.cms.vo.HtmlStructure;
import cn.hopever.platform.utils.json.JacksonUtil;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.SelectOption;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

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
    private RelatedUserTableRepository relatedUserTableRepository;

    @Autowired
    private ArticleVoAssembler articleVoAssembler;

    @Autowired
    private BlockTableRepository blockTableRepository;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private CmsProperties cmsProperties;

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
        BeanUtils.copyNotNullProperties(articleTable, articleTable1, "id", "articleTagTables", "blockTables", "staticResourceTables");
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

    @Override
    public String preview(Long id, String originPath) {
        ArticleTable articleTable = articleTableRepository.findOne(id);
        Object cache = redisTemplate.boundValueOps("cms-article-website" + articleTable.getWebsiteTable().getWebsiteId() + "-article" + articleTable.getId()).get();
        if (cache != null) {
            return cache.toString();
        } else {
            List<StaticResourceTable> staticCssResourceTables = new ArrayList<>();
            List<StaticResourceTable> staticJsResourceTables = new ArrayList<>();
            List<BlockTable> blockTables = new ArrayList<>();
            // 进行组合

            staticCssResourceTables.addAll(staticResourceTableRepository.findByThemeTableAndTypeOrderByResourceOrderAsc(articleTable.getWebsiteTable().getThemeTable(), "stylesheet"));
            staticJsResourceTables.addAll(staticResourceTableRepository.findByThemeTableAndTypeOrderByResourceOrderAsc(articleTable.getWebsiteTable().getThemeTable(), "script"));
            staticCssResourceTables.addAll(staticResourceTableRepository.findByWebsiteTableAndTypeOrderByResourceOrderAsc(articleTable.getWebsiteTable(), "stylesheet"));
            staticJsResourceTables.addAll(staticResourceTableRepository.findByWebsiteTableAndTypeOrderByResourceOrderAsc(articleTable.getWebsiteTable(), "script"));
            staticCssResourceTables.addAll(staticResourceTableRepository.findByArticleTableAndTypeOrderByResourceOrderAsc(articleTable, "stylesheet"));
            staticJsResourceTables.addAll(staticResourceTableRepository.findByArticleTableAndTypeOrderByResourceOrderAsc(articleTable, "script"));
            blockTables.addAll(articleTable.getBlockTables());
            blockTables.addAll(articleTable.getTemplateTable().getBlockTables());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(
                    "<html>\n" +
                            "<head>\n" +
                            "    <meta charset=\"utf-8\">\n" +
                            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no\">\n" +
                            "    <title i18n-content=\"title\">" + articleTable.getTitle() + "--" + articleTable.getWebsiteTable().getTitle() + "</title>\n");
            for (StaticResourceTable staticResourceTable : staticCssResourceTables) {
                stringBuilder.append("    <link href=\"" + cmsProperties.getStaticResourceRelativePath() + staticResourceTable.getUrl() + "\" rel=\"stylesheet\" type=\"text/css\"/>\n");
            }
            stringBuilder.append("</head>\n");
            stringBuilder.append("<body>\n");
            List<Map<String, Object>> positions = new ArrayList<>();
            try {
                if (articleTable.getTemplateTable().getContentPosition() != null) {
                    Map<String, Object> position = new HashMap<>();
                    position.put("object", articleTable);
                    position.put("position", JacksonUtil.mapper.readValue(articleTable.getTemplateTable().getContentPosition(), Map.class));
                    positions.add(position);
                }
                for (BlockTable blockTable : blockTables) {
                    Map<String, Object> position = new HashMap<>();
                    position.put("object", blockTable);
                    position.put("position", JacksonUtil.mapper.readValue(blockTable.getPosition(), Map.class));
                    positions.add(position);
                }
                List<HtmlStructure.Row> list = new ArrayList<>();
                this.getRows(positions, 0, list);
                // 开始执行 list的处理
                for (HtmlStructure.Row row : list) {
                    this.generateRowHtml(row, 6, stringBuilder);
                }
                System.out.println(list.size());
            } catch (IOException e) {
                e.printStackTrace();
            }
            stringBuilder.append("</body>\n");
            for (StaticResourceTable staticResourceTable : staticJsResourceTables) {
                stringBuilder.append("    <script type=\"text/javascript\" src=\"" + cmsProperties.getStaticResourceRelativePath() + staticResourceTable.getUrl() + "\"></script>\n");
            }
            stringBuilder.append("</html>\n");
            String returnStr = stringBuilder.toString();
            redisTemplate.boundValueOps("cms-article-website" + articleTable.getWebsiteTable().getWebsiteId() + "-article" + articleTable.getId()).set(returnStr);
            return returnStr;
        }
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
            articleTable.setCreatedUser(relatedUserTableRepository.findOneByAccount(principal.getName()));
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
        if ("update".equals(operation)) {
            redisTemplate.delete("cms-article-website" + articleTable.getWebsiteTable().getWebsiteId() + "-article" + articleTable.getId());
        }
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


    private List<HtmlStructure.Row> getRows(List<Map<String, Object>> positions, int beginY, List<HtmlStructure.Row> rows) {
        int endY = beginY + 1;
        List<Object> objects = new ArrayList<>();
        int positionsSize = positions.size();
        HtmlStructure.Row row = new HtmlStructure.Row();
        for (int y = beginY; y < 6; y++) {
            for (Iterator<Map<String, Object>> iter = positions.iterator(); iter.hasNext(); ) {
                Map<String, Object> position = iter.next();
                Map<String, Map<String, Integer>> positionTmp = (Map<String, Map<String, Integer>>) position.get("position");
                if (positionTmp.get("begin").get("y") == y) {
                    if (y == beginY || y > beginY && y < endY) {
                        if (endY < positionTmp.get("end").get("y")) {
                            endY = positionTmp.get("end").get("y");
                        }
                        objects.add(position);
                        iter.remove();
                    }
                }
            }
        }
        Map<String, Map<String, Integer>> rowPosition = new HashMap<>();
        Map<String, Integer> rowBegin = new HashMap<>();
        rowBegin.put("x", 0);
        rowBegin.put("y", beginY);
        Map<String, Integer> rowEnd = new HashMap<>();
        rowEnd.put("x", 6);
        rowEnd.put("y", endY);
        rowPosition.put("begin", rowBegin);
        rowPosition.put("end", rowEnd);
        row.setPosition(rowPosition);
        row.setObjects(objects);
        this.setColumns(row, 0);
        rows.add(row);
        if (endY < 6) {
            getRows(positions, endY, rows);
        }
        return rows;
    }

    private void setColumns(HtmlStructure.Row row, int beginX) {
        if ((row.getObjects() == null || row.getObjects().size() == 0) && row.getColumns().size() == 0) {
            // 给出一个空的column 目前来看不做处理
//            HtmlStructure.Column column = new HtmlStructure.Column();
//            column.setPosition(row.getPosition());
//            row.getColumns().add(column);
        } else {
            HtmlStructure.Column column = new HtmlStructure.Column();
            int xEnd = beginX + 1;
            int rowObjectSize = row.getObjects().size();
            List<Object> listColumnObj = new ArrayList<>();
            for (int x = beginX; x < row.getPosition().get("end").get("x"); x++) {
                for (Iterator<Object> iter = row.getObjects().iterator(); iter.hasNext(); ) {
                    Object position = iter.next();
                    Map<String, Map<String, Integer>> objPosition = (Map<String, Map<String, Integer>>) ((Map) position).get("position");
                    if (objPosition.get("begin").get("x") == x) {
                        if (x == beginX || x > beginX && x < xEnd) {
                            if (xEnd < objPosition.get("end").get("x")) {
                                xEnd = objPosition.get("end").get("x");
                            }
                            listColumnObj.add(position);
                            iter.remove();
                        }
                    }
                }
            }
            if (listColumnObj.size() > 0) {
                Map<String, Map<String, Integer>> columnPosition = new HashMap<>();
                Map<String, Integer> rowBegin = new HashMap<>();
                rowBegin.put("x", beginX);
                rowBegin.put("y", row.getPosition().get("begin").get("y"));
                Map<String, Integer> rowEnd = new HashMap<>();
                rowEnd.put("x", xEnd);
                rowEnd.put("y", row.getPosition().get("end").get("y"));
                columnPosition.put("begin", rowBegin);
                columnPosition.put("end", rowEnd);
                column.setPosition(columnPosition);
                column.setObjects(listColumnObj);
                this.getRows(column, rowBegin.get("y"));
                row.getColumns().add(column);
            }
            if (xEnd < row.getPosition().get("end").get("x")) {
                this.setColumns(row, xEnd);
            }
        }
    }

    private void getRows(HtmlStructure.Column column, int beginY) {
        if (column.getObjects().size() > 0) {
            if (column.getObjects().size() == 1) {
                Map<String, Map<String, Integer>> objPosition = (Map<String, Map<String, Integer>>) ((Map) column.getObjects().get(0)).get("position");
                if (column.getPosition().get("begin").get("x") == objPosition.get("begin").get("x")
                        && column.getPosition().get("begin").get("y") == objPosition.get("begin").get("y")
                        && column.getPosition().get("end").get("x") == objPosition.get("end").get("x")
                        && column.getPosition().get("end").get("y") == objPosition.get("end").get("y")) {
                    column.setObject(column.getObjects().get(0));
                    return;
                }
            }
            HtmlStructure.Row row = new HtmlStructure.Row();
            List<Object> objects = new ArrayList<>();
            int endY = beginY + 1;
            int positionsSize = column.getObjects().size();
            for (int y = beginY; y < column.getPosition().get("end").get("y"); y++) {
                for (Iterator<Object> iter = column.getObjects().iterator(); iter.hasNext(); ) {
                    Map<String, Object> position = (Map) iter.next();
                    Map<String, Map<String, Integer>> positionTmp = (Map<String, Map<String, Integer>>) position.get("position");
                    if (positionTmp.get("begin").get("y") == y) {
                        if (y == beginY || y > beginY && y < endY) {
                            if (endY < positionTmp.get("end").get("y")) {
                                endY = positionTmp.get("end").get("y");
                            }
                            objects.add(position);
                            iter.remove();
                        }
                    }
                }
            }
            Map<String, Map<String, Integer>> rowPosition = new HashMap<>();
            Map<String, Integer> rowBegin = new HashMap<>();
            rowBegin.put("x", column.getPosition().get("begin").get("x"));
            rowBegin.put("y", beginY);
            Map<String, Integer> rowEnd = new HashMap<>();
            rowEnd.put("x", column.getPosition().get("end").get("x"));
            rowEnd.put("y", endY);
            rowPosition.put("begin", rowBegin);
            rowPosition.put("end", rowEnd);
            row.setPosition(rowPosition);
            row.setObjects(objects);
            this.setColumns(row, row.getPosition().get("begin").get("x"));
            column.getRows().add(row);
            if (endY < column.getPosition().get("end").get("y")) {
                this.getRows(column, endY);
            }
        }
    }

    private void generateRowHtml(HtmlStructure.Row row, int colHeight, StringBuilder stringBuilder) {
        String beginPoint = row.getPosition().get("begin").get("x") + "-" + row.getPosition().get("begin").get("y");
        int height = row.getPosition().get("end").get("y") - row.getPosition().get("begin").get("y");
        int width = row.getPosition().get("end").get("x") - row.getPosition().get("begin").get("x");
        int relativeHeight = height * 6 / colHeight;
        stringBuilder.append("        <div class=\"row" + " begin-" + beginPoint + " width-" + width + " height-" + height + " relative-height-" + relativeHeight + "\">\n");
        for (HtmlStructure.Column column : row.getColumns()) {
            // 需要使用递归的方法处理
            this.generateColumnHtml(column, width, height, stringBuilder);
        }
        stringBuilder.append("        </div>\n");
    }

    private void generateColumnHtml(HtmlStructure.Column column, int rowWidth, int rowHeight, StringBuilder stringBuilder) {
        String beginPoint = column.getPosition().get("begin").get("x") + "-" + column.getPosition().get("begin").get("y");
        int height = column.getPosition().get("end").get("y") - column.getPosition().get("begin").get("y");
        int width = column.getPosition().get("end").get("x") - column.getPosition().get("begin").get("x");
        int colWidth = width * 12 / rowWidth;
        int relativeHeight = height * 6 / rowHeight;
        String mainContentClass = "";
        if ((column.getRows() == null || column.getRows().size() == 0) && column.getObject() != null) {
            Object object = ((Map) column.getObject()).get("object");
            if (object instanceof TemplateTable) {
                mainContentClass = " main-content";
            }
        }
        stringBuilder.append("        <div class=\"col col-md-" + colWidth + mainContentClass + " begin-" + beginPoint + " width-" + width + " height-" + height + " relative-height-" + relativeHeight + "\">\n");
        if (column.getRows() != null && column.getRows().size() > 0) {
            for (HtmlStructure.Row row : column.getRows()) {
                this.generateRowHtml(row, height, stringBuilder);
            }
        } else {
            if (column.getObject() != null) {
                Object object = ((Map) column.getObject()).get("object");
                if (object instanceof ArticleTable) {
                    stringBuilder.append(((ArticleTable) object).getContent());
                    if (((ArticleTable) object).getScript() != null) {
                        stringBuilder.append("    <script type=\"text/javascript\">");
                        stringBuilder.append(((ArticleTable) object).getScript());
                        stringBuilder.append("    </script>");
                    }
                } else if (object instanceof BlockTable) {
                    if (((BlockTable) object).getContent() != null) {
                        stringBuilder.append(((BlockTable) object).getContent());
                    }
                    if (((BlockTable) object).getScript() != null) {
                        stringBuilder.append("    <script type=\"text/javascript\">");
                        stringBuilder.append(((BlockTable) object).getScript());
                        stringBuilder.append("    </script>");
                    }
                }
            }
        }
        stringBuilder.append("        </div>\n");
    }

    // 此处需要给出一个tag block的一个处理 最新新闻什么的，然后是需要一个

}
