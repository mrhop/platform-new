package cn.hopever.platform.cms.vo;

import cn.hopever.platform.cms.domain.MediaTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class MediaVoAssembler implements GenericVoAssembler<MediaVo, MediaTable> {

    @Override
    public MediaVo toResource(MediaTable mediaTable) {
        MediaVo mediaVo = new MediaVo();
        BeanUtils.copyNotNullProperties(mediaTable,mediaVo);
        mediaVo.setCreatedDate(mediaTable.getCreatedDate().getTime());
        if(mediaTable.getPublishDate()!=null){
            mediaVo.setPublishDate(mediaTable.getPublishDate().getTime());
        }
        mediaVo.setMediaTagId(mediaTable.getMediaTagTable().getId());
        mediaVo.setMediaTagName(mediaTable.getMediaTagTable().getName());
        if(mediaTable.getWebsiteTable()!=null){
            mediaVo.setWebsiteId(mediaTable.getWebsiteTable().getId());
            mediaVo.setWebsiteName(mediaTable.getWebsiteTable().getName());
        }
        if(mediaTable.getThemeTable()!=null){
            mediaVo.setThemeId(mediaTable.getThemeTable().getId());
            mediaVo.setThemeName(mediaTable.getThemeTable().getName());
        }
        return mediaVo;
    }

    @Override
    public MediaTable toDomain(MediaVo mediaVo, MediaTable mediaTable) {
        BeanUtils.copyNotNullProperties(mediaVo,mediaTable);
        if(mediaVo.getPublishDate()!=null){
            mediaTable.setPublishDate(new Date(mediaVo.getPublishDate()));
        }
        return mediaTable;
    }
}
