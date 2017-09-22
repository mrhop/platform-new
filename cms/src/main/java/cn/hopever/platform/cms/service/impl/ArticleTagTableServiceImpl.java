package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.domain.ArticleTagTable;
import cn.hopever.platform.cms.repository.ArticleTagTableRepository;
import cn.hopever.platform.cms.repository.CustomArticleTagTableRepository;
import cn.hopever.platform.cms.repository.WebsiteTableRepository;
import cn.hopever.platform.cms.service.ArticleTagTableService;
import cn.hopever.platform.cms.vo.ArticleTagVo;
import cn.hopever.platform.cms.vo.ArticleTagVoAssembler;
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
 */
@Service
@Transactional
public class ArticleTagTableServiceImpl implements ArticleTagTableService {
    @Autowired
    private WebsiteTableRepository websiteTableRepository;

    @Autowired
    private ArticleTagTableRepository articleTagTableRepository;

    @Autowired
    private CustomArticleTagTableRepository customArticleTagTableRepository;

    @Autowired
    private ArticleTagVoAssembler articleTagVoAssembler;

    @Override
    public Page<ArticleTagVo> getList(TableParameters body, Principal principal) {
        PageRequest pageRequest;
        if (body.getSorts() == null || body.getSorts().isEmpty()) {
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.ASC, "id");
        } else {
            String key = body.getSorts().keySet().iterator().next();
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.fromString(body.getSorts().get(key)), key);
        }
        if (body.getFilters() != null && body.getFilters().containsKey("websiteId")) {
            body.getFilters().put("websiteTable", websiteTableRepository.findOne(Long.valueOf(body.getFilters().get("websiteId").toString())));
            body.getFilters().remove("websiteId");
        }
        Page<ArticleTagTable> page = customArticleTagTableRepository.findByFilters(body.getFilters(), pageRequest);
        List<ArticleTagVo> list = new ArrayList<>();
        for (ArticleTagTable articleTagTable : page) {
            list.add(articleTagVoAssembler.toResource(articleTagTable));
        }
        return new PageImpl<ArticleTagVo>(list, pageRequest, page.getTotalElements());
    }

    @Override
    public void delete(Long id, Principal principal) {
        this.articleTagTableRepository.delete(id);
    }

    @Override
    public ArticleTagVo info(Long id, Principal principal) {
        ArticleTagTable articleTagTable = articleTagTableRepository.findOne(id);
        return articleTagVoAssembler.toResource(articleTagTable);
    }

    @Override
    public VueResults.Result update(ArticleTagVo articleTagVo, MultipartFile[] files, Principal principal) {
        ArticleTagTable articleTagTable = articleTagTableRepository.findOne(articleTagVo.getId());
        articleTagVoAssembler.toDomain(articleTagVo, articleTagTable);
        articleTagTableRepository.save(articleTagTable);
        return null;
    }

    @Override
    public VueResults.Result save(ArticleTagVo articleTagVo, MultipartFile[] files, Principal principal) {
        if (articleTagTableRepository.findOneByTagId(articleTagVo.getTagId()) != null) {
            return VueResults.generateError("创建失败", "tagID已存在");
        }
        ArticleTagTable articleTagTable = new ArticleTagTable();
        articleTagVoAssembler.toDomain(articleTagVo, articleTagTable);
        articleTagTable.setTagId(articleTagVo.getTagId());
        articleTagTable.setWebsiteTable(websiteTableRepository.findOne(articleTagVo.getWebsiteId()));
        articleTagTableRepository.save(articleTagTable);
        return null;
    }

    @Override
    public List<SelectOption> getArticleTagOptions(Long websiteId, Principal principal) {
        List<ArticleTagTable> list = articleTagTableRepository.findByWebsiteTable(websiteTableRepository.findOne(websiteId));
        List<SelectOption> listReturn = new ArrayList<>();
        for (ArticleTagTable articleTagTable : list) {
            SelectOption selectOption = new SelectOption(articleTagTable.getName(), articleTagTable.getId());
            listReturn.add(selectOption);
        }
        return listReturn;
    }
}
