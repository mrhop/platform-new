package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.domain.MediaTagTable;
import cn.hopever.platform.cms.repository.CustomMediaTagTableRepository;
import cn.hopever.platform.cms.repository.MediaTagTableRepository;
import cn.hopever.platform.cms.repository.WebsiteTableRepository;
import cn.hopever.platform.cms.service.MediaTagTableService;
import cn.hopever.platform.cms.vo.MediaTagVo;
import cn.hopever.platform.cms.vo.MediaTagVoAssembler;
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
public class MediaTagTableServiceImpl implements MediaTagTableService {
    @Autowired
    private MediaTagTableRepository mediaTagTableRepository;

    @Autowired
    private CustomMediaTagTableRepository customMediaTagTableRepository;

    @Autowired
    private MediaTagVoAssembler mediaTagVoAssembler;

    @Autowired
    private WebsiteTableRepository websiteTableRepository;

    @Override
    public Page<MediaTagVo> getList(TableParameters body, Principal principal) {
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
        Page<MediaTagTable> page = customMediaTagTableRepository.findByFilters(body.getFilters(), pageRequest);
        List<MediaTagVo> list = new ArrayList<>();
        for (MediaTagTable mediaTagTable : page) {
            list.add(mediaTagVoAssembler.toResource(mediaTagTable));
        }
        return new PageImpl<MediaTagVo>(list, pageRequest, page.getTotalElements());
    }

    @Override
    public void delete(Long id, Principal principal) {
        mediaTagTableRepository.delete(id);
    }

    @Override
    public MediaTagVo info(Long id, Principal principal) {
        MediaTagTable mediaTagTable = mediaTagTableRepository.findOne(id);
        return mediaTagVoAssembler.toResource(mediaTagTable);
    }

    @Override
    public VueResults.Result update(MediaTagVo mediaTagVo, MultipartFile[] files, Principal principal) {
        MediaTagTable mediaTagTable = mediaTagTableRepository.findOne(mediaTagVo.getId());
        mediaTagVoAssembler.toDomain(mediaTagVo, mediaTagTable);
        mediaTagTableRepository.save(mediaTagTable);
        return null;
    }

    @Override
    public VueResults.Result save(MediaTagVo mediaTagVo, MultipartFile[] files, Principal principal) {
        if (mediaTagTableRepository.findOneByTagId(mediaTagVo.getTagId()) != null) {
            return VueResults.generateError("创建失败", "tagID已存在");
        }
        MediaTagTable mediaTagTable = new MediaTagTable();
        mediaTagVoAssembler.toDomain(mediaTagVo, mediaTagTable);
        mediaTagTable.setWebsiteTable(websiteTableRepository.findOne(mediaTagVo.getWebsiteId()));
        mediaTagTableRepository.save(mediaTagTable);
        return null;
    }

    @Override
    public List<SelectOption> getOptionsByWebsiteId(Long websiteId) {
        List<SelectOption> listReturn = new ArrayList<>();
        List<MediaTagTable> list = mediaTagTableRepository.findByWebsiteTable(websiteTableRepository.findOne(websiteId));
        for (MediaTagTable mediaTagTable : list) {
            listReturn.add(new SelectOption(mediaTagTable.getName(), mediaTagTable.getId()));
        }
        return listReturn;
    }
}
