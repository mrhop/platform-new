package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.config.CmsProperties;
import cn.hopever.platform.cms.domain.*;
import cn.hopever.platform.cms.repository.*;
import cn.hopever.platform.cms.service.TemplateTableService;
import cn.hopever.platform.cms.vo.HtmlStructure;
import cn.hopever.platform.cms.vo.TemplateVo;
import cn.hopever.platform.cms.vo.TemplateVoAssembler;
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
    private WebsiteTableRepository websiteTableRepository;
    @Autowired
    private BlockTableRepository blockTableRepository;
    @Autowired
    private StaticResourceTableRepository staticResourceTableRepository;
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;
    @Autowired
    private CmsProperties cmsProperties;

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
        if (body.getFilters() != null && body.getFilters().containsKey("websiteId")) {
            body.getFilters().put("websiteTable", websiteTableRepository.findOne(Long.valueOf(body.getFilters().get("websiteId").toString())));
            body.getFilters().remove("websiteId");
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
        redisTemplate.delete("cms-template-" + templateTable.getId());
        return null;
    }

    @Override
    public VueResults.Result save(TemplateVo templateVo, MultipartFile[] files, Principal principal) {
        TemplateTable templateTable = new TemplateTable();
        templateVoAssembler.toDomain(templateVo, templateTable);
        if (templateVo.getThemeId() != null) {
            List<TemplateTable> templateTables = new ArrayList<>();
            Map<Long, TemplateTable> mapTemplateTable = new HashMap<>();
            ThemeTable themeTable = themeTableRepository.findOne(templateVo.getThemeId());
            if (themeTable.getWebsiteTables() != null) {
                for (WebsiteTable websiteTable : themeTable.getWebsiteTables()) {
                    TemplateTable templateTableTemp = new TemplateTable();
                    BeanUtils.copyNotNullProperties(templateTable, templateTableTemp);
                    templateTableTemp.setWebsiteTable(websiteTable);
                    templateTables.add(templateTableTemp);
                    mapTemplateTable.put(websiteTable.getId(), templateTableTemp);
                }
            }
            templateTable.setThemeTable(themeTable);
            templateTables.add(templateTable);
            templateTableRepository.save(templateTables);
            if (templateVo.getBlocks() != null && templateVo.getBlocks().size() > 0) {
                List<BlockTable> blockTables = new ArrayList<>();
                for (List<String> list : templateVo.getBlocks()) {
                    BlockTable blockTable = new BlockTable();
                    blockTable.setName(list.get(0));
                    blockTable.setPosition(list.get(1));
                    blockTable.setContent(list.get(2));
                    blockTable.setScript(list.get(3));
                    blockTables.add(blockTable);
                    if (themeTable.getWebsiteTables() != null) {
                        for (WebsiteTable websiteTable : themeTable.getWebsiteTables()) {
                            BlockTable blockTableTemp = new BlockTable();
                            BeanUtils.copyNotNullProperties(blockTable, blockTableTemp);
                            blockTableTemp.setTemplateTable(mapTemplateTable.get(websiteTable.getId()));
                            blockTables.add(blockTableTemp);
                        }
                    }
                    blockTable.setTemplateTable(templateTable);
                    blockTables.add(blockTable);
                }
                blockTableRepository.save(blockTables);
            }
        } else if (templateVo.getWebsiteId() != null) {
            WebsiteTable websiteTable = websiteTableRepository.findOne(templateVo.getWebsiteId());
            templateTable.setWebsiteTable(websiteTable);
            templateTableRepository.save(templateTable);
            if (templateVo.getBlocks() != null && templateVo.getBlocks().size() > 0) {
                List<BlockTable> blockTables = new ArrayList<>();
                for (List<String> list : templateVo.getBlocks()) {
                    BlockTable blockTable = new BlockTable();
                    blockTable.setName(list.get(0));
                    blockTable.setPosition(list.get(1));
                    blockTable.setContent(list.get(2));
                    blockTable.setScript(list.get(3));
                    blockTables.add(blockTable);
                    blockTable.setTemplateTable(templateTable);
                    blockTables.add(blockTable);
                }
                blockTableRepository.save(blockTables);
            }
        }
        return null;
    }


    @Override
    public List<SelectOption> getOptionsByWebsiteId(Long websiteId) {
        List<SelectOption> listReturn = new ArrayList<>();
        List<TemplateTable> list = templateTableRepository.findByWebsiteTable(websiteTableRepository.findOne(websiteId));
        for (TemplateTable templateTable : list) {
            listReturn.add(new SelectOption(templateTable.getName(), templateTable.getId()));
        }
        return listReturn;
    }

    @Override
    public void copy(Long id) {
        TemplateTable templateTable = templateTableRepository.findOne(id);
        List<TemplateTable> listSave = new ArrayList<>();
        TemplateTable templateTableTmp = new TemplateTable();
        BeanUtils.copyNotNullProperties(templateTable, templateTableTmp, "id", "blockTables", "articleTables");
        List<BlockTable> list = null;
        if (templateTable.getBlockTables() != null) {
            list = new ArrayList<>();
            for (BlockTable blockTable : templateTable.getBlockTables()) {
                BlockTable blockTableTemp = new BlockTable();
                BeanUtils.copyNotNullProperties(blockTable, blockTableTemp, "id", "templateTable", "articleTable");
                blockTableTemp.setTemplateTable(templateTableTmp);
                list.add(blockTableTemp);
            }
        }
        templateTableTmp.setBlockTables(list);
        listSave.add(templateTableTmp);
        ThemeTable themeTable = templateTableTmp.getThemeTable();
        if (themeTable != null) {
            if (themeTable.getWebsiteTables() != null) {
                for (WebsiteTable websiteTable : themeTable.getWebsiteTables()) {
                    TemplateTable templateTable1 = new TemplateTable();
                    BeanUtils.copyNotNullProperties(templateTableTmp, templateTable1, "id", "blockTables", "themeTable");
                    List<BlockTable> blockTables = new ArrayList<>();
                    if (templateTableTmp.getBlockTables() != null) {
                        for (BlockTable blockTable : templateTableTmp.getBlockTables()) {
                            BlockTable blockTable1 = new BlockTable();
                            BeanUtils.copyNotNullProperties(blockTable, blockTable1, "id", "templateTable", "articleTable");
                            blockTable1.setTemplateTable(templateTable1);
                            blockTables.add(blockTable1);
                        }
                    }
                    templateTable1.setWebsiteTable(websiteTable);
                    templateTable1.setBlockTables(blockTables);
                    listSave.add(templateTable1);
                }
            }
        }
        templateTableRepository.save(listSave);
    }

    @Override
    public String preview(Long id, String originPath) {
        TemplateTable templateTable = templateTableRepository.findOne(id);
        Object cache = redisTemplate.boundValueOps("cms-template-" + templateTable.getId()).get();
        if (cache != null) {
            return cache.toString();
        } else {
            List<StaticResourceTable> staticCssResourceTables = new ArrayList<>();
            List<StaticResourceTable> staticJsResourceTables = new ArrayList<>();
            List<BlockTable> blockTables = new ArrayList<>();
            // 进行组合
            if (templateTable.getThemeTable() != null) {
                staticCssResourceTables.addAll(staticResourceTableRepository.findByThemeTableAndTypeOrderByResourceOrderAsc(templateTable.getThemeTable(), "stylesheet"));
                staticJsResourceTables.addAll(staticResourceTableRepository.findByThemeTableAndTypeOrderByResourceOrderAsc(templateTable.getThemeTable(), "script"));
            } else if (templateTable.getWebsiteTable() != null) {
                staticCssResourceTables.addAll(staticResourceTableRepository.findByThemeTableAndTypeOrderByResourceOrderAsc(templateTable.getWebsiteTable().getThemeTable(), "stylesheet"));
                staticJsResourceTables.addAll(staticResourceTableRepository.findByThemeTableAndTypeOrderByResourceOrderAsc(templateTable.getWebsiteTable().getThemeTable(), "script"));
                staticCssResourceTables.addAll(staticResourceTableRepository.findByWebsiteTableAndTypeOrderByResourceOrderAsc(templateTable.getWebsiteTable(), "stylesheet"));
                staticJsResourceTables.addAll(staticResourceTableRepository.findByWebsiteTableAndTypeOrderByResourceOrderAsc(templateTable.getWebsiteTable(), "script"));
            }
            blockTables.addAll(templateTable.getBlockTables());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(
                    "<html>\n" +
                            "<head>\n" +
                            "    <meta charset=\"utf-8\">\n" +
                            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no\">\n" +
                            "    <title i18n-content=\"title\">template-" + templateTable.getName() + "</title>\n");
            stringBuilder.append("    <link href=\"" + originPath + "/static/css/cms-iframe-preview.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
            for (StaticResourceTable staticResourceTable : staticCssResourceTables) {
                stringBuilder.append("    <link href=\"" + cmsProperties.getStaticResourceRelativePath() + staticResourceTable.getUrl() + "\" rel=\"stylesheet\" type=\"text/css\"/>\n");
            }
            stringBuilder.append("</head>\n");
            stringBuilder.append("<body>\n");
            List<Map<String, Object>> positions = new ArrayList<>();
            try {
                if (templateTable.getContentPosition() != null) {
                    Map<String, Object> position = new HashMap<>();
                    position.put("object", templateTable);
                    position.put("position", JacksonUtil.mapper.readValue(templateTable.getContentPosition(), Map.class));
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
            redisTemplate.boundValueOps("cms-template-" + templateTable.getId()).set(returnStr);
            return returnStr;
        }
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
                if (object instanceof TemplateTable) {
                    stringBuilder.append("Main Content Position");
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


}
