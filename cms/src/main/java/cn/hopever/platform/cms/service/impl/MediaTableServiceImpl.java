package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.domain.MediaTable;
import cn.hopever.platform.cms.domain.MediaTagTable;
import cn.hopever.platform.cms.domain.WebsiteTable;
import cn.hopever.platform.cms.repository.*;
import cn.hopever.platform.cms.service.MediaTableService;
import cn.hopever.platform.cms.vo.MediaVo;
import cn.hopever.platform.cms.vo.MediaVoAssembler;
import cn.hopever.platform.utils.file.FileUtil;
import cn.hopever.platform.utils.moji.MojiUtils;
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
import java.util.Map;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
@Service
@Transactional
public class MediaTableServiceImpl implements MediaTableService {

    @Autowired
    private MojiUtils mojiUtils;
    @Autowired
    private MediaTableRepository mediaTableRepository;
    @Autowired
    private MediaTagTableRepository mediaTagTableRepository;
    @Autowired
    private RelatedUserTableRepository relatedUserTableRepository;
    @Autowired
    private CustomMediaTableRepository customMediaTableRepository;
    @Autowired
    private MediaVoAssembler mediaVoAssembler;
    @Autowired
    private WebsiteTableRepository websiteTableRepository;

    @Override
    public Page<MediaVo> getList(TableParameters body, Principal principal) {
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
        if (body.getFilters() != null && body.getFilters().containsKey("mediaTagId")) {
            body.getFilters().put("mediaTagTable", mediaTagTableRepository.findOne(Long.valueOf(body.getFilters().get("mediaTagId").toString())));
            body.getFilters().remove("mediaTagId");
        }
        Page<MediaTable> page = customMediaTableRepository.findByFilters(body.getFilters(), pageRequest);
        List<MediaVo> list = new ArrayList<>();
        for (MediaTable mediaTable : page) {
            list.add(mediaVoAssembler.toResource(mediaTable));
        }
        return new PageImpl<MediaVo>(list, pageRequest, page.getTotalElements());
    }

    @Override
    public void delete(Long id, Principal principal) {
        this.mediaTableRepository.delete(id);
    }

    @Override
    public MediaVo info(Long id, Principal principal) {
        MediaTable mediaTable = mediaTableRepository.findOne(id);
        return mediaVoAssembler.toResource(mediaTable);
    }

    @Override
    public VueResults.Result update(MediaVo mediaVo, MultipartFile[] files, Principal principal) {
        MediaTable mediaTable = mediaTableRepository.findOne(mediaVo.getId());
        mediaVoAssembler.toDomain(mediaVo, mediaTable);
        if (mediaTable.isPublished() && mediaTable.getPublishDate() == null) {
            mediaTable.setPublishDate(new Date());
        }
        if (files != null && files.length > 0) {
            mediaTable.setSize(files[0].getSize());
            mediaTable.setFilename(files[0].getOriginalFilename());
            try {
                Map<String, List<String>> mapFiles = mojiUtils.uploadImg("cms/website/" + mediaTable.getWebsiteTable().getWebsiteId() + "/media/" + mediaTable.getMediaTagTable().getTagId() + "/", files);
                List<String> list = mapFiles.get("fileKeys");
                mediaTable.setUrl(list.get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mediaTableRepository.save(mediaTable);
        return null;
    }

    @Override
    public VueResults.Result save(MediaVo mediaVo, MultipartFile[] files, Principal principal) {
        MediaTable mediaTable = new MediaTable();
        mediaVoAssembler.toDomain(mediaVo, mediaTable);
        mediaTable.setCreatedDate(new Date());
        mediaTable.setCreatedUser(relatedUserTableRepository.findOneByAccount(principal.getName()));
        if (mediaTable.isPublished() && mediaTable.getPublishDate() == null) {
            mediaTable.setPublishDate(new Date());
        }
        if (mediaVo.getWebsiteId() != null) {
            mediaTable.setWebsiteTable(websiteTableRepository.findOne(mediaVo.getWebsiteId()));
        }
        if (mediaVo.getMediaTagId() != null) {
            mediaTable.setMediaTagTable(mediaTagTableRepository.findOne(mediaVo.getMediaTagId()));
        }
        // do with file
        if (files != null && files.length > 0) {
            mediaTable.setSize(files[0].getSize());
            if (mediaTable.getName() == null || mediaTable.getName().length() == 0) {
                mediaTable.setName(FileUtil.getFileNameNoExtend(files[0].getOriginalFilename()));
            }
            mediaTable.setFilename(files[0].getOriginalFilename());
            mediaTable.setFileType(FileUtil.getExtensionName(files[0].getOriginalFilename()));
            mediaTable.setType(FileUtil.getFileGeneralType(files[0].getOriginalFilename()));
            try {
                Map<String, List<String>> mapFiles = mojiUtils.uploadImg("cms/website/" + mediaTable.getWebsiteTable().getWebsiteId() + "/media/" + mediaTable.getMediaTagTable().getTagId() + "/", files);
                List<String> list = mapFiles.get("fileKeys");
                mediaTable.setUrl(list.get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mediaTableRepository.save(mediaTable);
        return null;
    }

    @Override
    public List<MediaVo> getListByMediaTagAndPublished(Long mediaTagId) {
        List<MediaTable> list = mediaTableRepository.findByMediaTagTableAndPublished(mediaTagTableRepository.findOne(mediaTagId), true);
        if (list != null && list.size() > 0) {
            List<MediaVo> listReturn = new ArrayList<>();
            for (MediaTable mediaTable : list) {
                listReturn.add(mediaVoAssembler.toResource(mediaTable));
            }
            return listReturn;
        }
        return null;
    }

    @Override
    public VueResults.Result upload(MultipartFile[] files, String tagId, Long websiteId, Principal principal) {
        // 如果tagId是空，那么根据上传文件的类型，来判断出需要的tagId
        if (tagId == null) {
            tagId = FileUtil.getFileGeneralType(files[0].getOriginalFilename());
        }
        WebsiteTable websiteTable = websiteTableRepository.findOne(websiteId);
        MediaTable mediaTable = new MediaTable();
        MediaTagTable mediaTagTable = mediaTagTableRepository.findOneByTagId(tagId);
        if (mediaTagTable == null) {
            mediaTagTable = new MediaTagTable();
            mediaTagTable.setName(tagId);
            mediaTagTable.setTagId(tagId);
            mediaTagTable.setWebsiteTable(websiteTable);
            mediaTagTableRepository.save(mediaTagTable);
        }
        mediaTable.setCreatedDate(new Date());
        mediaTable.setCreatedUser(relatedUserTableRepository.findOneByAccount(principal.getName()));
        mediaTable.setPublished(true);
        mediaTable.setPublishDate(new Date());
        mediaTable.setWebsiteTable(websiteTable);
        mediaTable.setMediaTagTable(mediaTagTable);
        // do with file
        if (files != null && files.length > 0) {
            mediaTable.setSize(files[0].getSize());
            if (mediaTable.getName() == null || mediaTable.getName().length() == 0) {
                mediaTable.setName(FileUtil.getFileNameNoExtend(files[0].getOriginalFilename()));
            }
            mediaTable.setFilename(files[0].getOriginalFilename());
            mediaTable.setFileType(FileUtil.getExtensionName(files[0].getOriginalFilename()));
            mediaTable.setType(FileUtil.getFileGeneralType(files[0].getOriginalFilename()));
            try {
                Map<String, List<String>> mapFiles = mojiUtils.uploadImg("cms/website/" + mediaTable.getWebsiteTable().getWebsiteId() + "/media/" + mediaTagTable.getTagId() + "/", files);
                List<String> list = mapFiles.get("fileKeys");
                mediaTable.setUrl(list.get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mediaTableRepository.save(mediaTable);
        return null;
    }

    @Override
    public VueResults.Result updatePublished(Long id, boolean published, Principal principal) {
        if (published) {
            mediaTableRepository.publishMedia(new Date(), id);
        } else {
            mediaTableRepository.unpublishMedia(id);
        }
        return VueResults.generateSuccess("更新成功", "更新发布状态成功");
    }
}
