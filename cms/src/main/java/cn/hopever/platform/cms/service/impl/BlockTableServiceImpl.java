package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.domain.ArticleTable;
import cn.hopever.platform.cms.domain.BlockTable;
import cn.hopever.platform.cms.domain.TemplateTable;
import cn.hopever.platform.cms.repository.*;
import cn.hopever.platform.cms.service.BlockTableService;
import cn.hopever.platform.cms.vo.BlockVo;
import cn.hopever.platform.cms.vo.BlockVoAssembler;
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
 */
@Service
@Transactional
public class BlockTableServiceImpl implements BlockTableService {
    @Autowired
    private WebsiteTableRepository websiteTableRepository;
    @Autowired
    private TemplateTableRepository templateTableRepository;
    @Autowired
    private ArticleTableRepository articleTableRepository;
    @Autowired
    private BlockTableRepository blockTableRepository;
    @Autowired
    private CustomBlockTableRepository customBlockTableRepository;
    @Autowired
    private BlockVoAssembler blockVoAssembler;

    @Override
    // 在没有过滤时，是否需要进行显示, 首先应该关联到website，才会有数据出现！！！ 下午继续
    public Page<BlockVo> getList(TableParameters body, Principal principal) {
        if (body.getFilters() != null && body.getFilters().containsKey("websiteId")) {
            PageRequest pageRequest;
            if (body.getSorts() == null || body.getSorts().isEmpty()) {
                pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.ASC, "position");
            } else {
                String key = body.getSorts().keySet().iterator().next();
                pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.fromString(body.getSorts().get(key)), key);
            }
            // 首先应该关联到website，才会有数据出现！！！ 下午继续
            if (body.getFilters() != null && body.getFilters().containsKey("websiteId")) {
                body.getFilters().put("websiteTable", websiteTableRepository.findOne(Long.valueOf(body.getFilters().get("websiteId").toString())));
                body.getFilters().remove("websiteId");
            }
            if (body.getFilters() != null && body.getFilters().containsKey("templateId")) {
                body.getFilters().put("templateTable", templateTableRepository.findOne(Long.valueOf(body.getFilters().get("templateId").toString())));
                body.getFilters().remove("templateId");
            }
            if (body.getFilters() != null && body.getFilters().containsKey("articleId")) {
                body.getFilters().put("articleTable", articleTableRepository.findOne(Long.valueOf(body.getFilters().get("articleId").toString())));
                body.getFilters().remove("articleId");
            }
            Page<BlockTable> page = customBlockTableRepository.findByFilters(body.getFilters(), pageRequest);
            List<BlockVo> list = new ArrayList<>();
            for (BlockTable blockTable : page) {
                list.add(blockVoAssembler.toResource(blockTable));
            }
            return new PageImpl<BlockVo>(list, pageRequest, page.getTotalElements());
        }
        return null;
    }

    @Override
    public void delete(Long id, Principal principal) {
        // 删除的时候，看一下删除的是template的，如果是，考虑删除关联的website的以及article的
        blockTableRepository.delete(id);
    }

    @Override
    public BlockVo info(Long id, Principal principal) {
        BlockTable blockTable = blockTableRepository.findOne(id);
        return blockVoAssembler.toResourceAll(blockTable);
    }

    @Override
    public VueResults.Result update(BlockVo blockVo, MultipartFile[] files, Principal principal) {
        BlockTable blockTable = blockTableRepository.findOne(blockVo.getId());
        blockVoAssembler.toDomain(blockVo, blockTable);
        blockTableRepository.save(blockTable);
        return VueResults.generateSuccess("更新成功", "更新成功");
    }

    @Override
    public VueResults.Result save(BlockVo blockVo, MultipartFile[] files, Principal principal) {
        BlockTable blockTable = new BlockTable();
        blockVoAssembler.toDomain(blockVo, blockTable);
        if (blockVo.getTemplateId() != null) {
            blockTable.setTemplateTable(templateTableRepository.findOne(blockVo.getTemplateId()));
        }
        if (blockVo.getArticleId() != null) {
            blockTable.setArticleTable(articleTableRepository.findOne(blockVo.getArticleId()));
        }
        blockTableRepository.save(blockTable);
        return VueResults.generateSuccess("创建成功", "创建成功");
    }

    @Override
    public List<BlockVo> getBlocksByTemplate(Long templateId) {
        List<BlockTable> list = this.blockTableRepository.findByTemplateTableOrderByPositionAsc(templateTableRepository.findOne(templateId));
        if (list != null && list.size() > 0) {
            List<BlockVo> listReturn = new ArrayList<>();
            for (BlockTable blockTable : list) {
                listReturn.add(blockVoAssembler.toResourceAll(blockTable));
            }
            return listReturn;
        }
        return null;
    }

    @Override
    public List<BlockVo> getBlocksByArticleAndTemplate(Long articleId, Long templateId) {
        ArticleTable articleTable = articleTableRepository.findOne(articleId);
        TemplateTable templateTable = templateId == null ? articleTable.getTemplateTable() : templateTableRepository.findOne(templateId);
        List<BlockTable> list = blockTableRepository.findByArticleTableAndTemplateTableOrderByPositionAsc(articleTable, templateTable);
        if (list != null && list.size() > 0) {
            List<BlockVo> listReturn = new ArrayList<>();
            for (BlockTable blockTable : list) {
                listReturn.add(blockVoAssembler.toResourceAll(blockTable));
            }
            return listReturn;
        }
        return null;
    }


    @Override
    public List<BlockVo> getBlocksByArticle(Long articleId) {
        ArticleTable articleTable = articleTableRepository.findOne(articleId);
        List<BlockTable> list = articleTable.getBlockTables();
        if (list != null && list.size() > 0) {
            List<BlockVo> listReturn = new ArrayList<>();
            for (BlockTable blockTable : list) {
                listReturn.add(blockVoAssembler.toResourceAll(blockTable));
            }
            return listReturn;
        }
        return null;
    }

    @Override
    public List<BlockVo> getBlocksAllByArticle(Long articleId) {
        ArticleTable articleTable = articleTableRepository.findOne(articleId);
        List<BlockVo> list = getBlocksByTemplate(articleTable.getTemplateTable().getId());
        if (list != null && list.size() > 0) {
            List<BlockTable> list1 = blockTableRepository.findByArticleTableOrderByPositionAsc(articleTable);
            List<BlockVo> listReturn = new ArrayList<>();
            for (BlockVo blockVo : list) {
                boolean flag = false;
                if (list1 != null && list.size() > 0) {
                    for (BlockTable blockTable1 : list1) {
                        if (blockTable1.getPosition().equals(blockVo.getPosition())) {
                            listReturn.add(blockVoAssembler.toResourceAll(blockTable1));
                            list1.remove(blockTable1);
                            flag = true;
                            break;
                        }
                    }
                }
                if (!flag) {
                    listReturn.add(blockVo);
                }
            }
            return listReturn;
        }
        return null;
    }

    @Override
    public BlockVo deleteAndReturn(Long id, Principal principal) {
        BlockTable blockTable = blockTableRepository.findOne(id);
        BlockVo blockVo = blockVoAssembler.toResource(blockTable);
        blockTableRepository.delete(id);
        return blockVo;
    }
}
