package cn.hopever.platform.cms.vo;

import cn.hopever.platform.cms.domain.MediaTagTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class MediaTagVoAssembler implements GenericVoAssembler<MediaTagVo, MediaTagTable> {

    @Override
    public MediaTagVo toResource(MediaTagTable mediaTagTable) {
        MediaTagVo mediaTagVo = new MediaTagVo();
        BeanUtils.copyNotNullProperties(mediaTagTable, mediaTagVo);
        mediaTagVo.setWebsiteId(mediaTagTable.getWebsiteTable().getId());
        mediaTagVo.setWebsiteName(mediaTagTable.getWebsiteTable().getName());
        return mediaTagVo;
    }

    @Override
    public MediaTagTable toDomain(MediaTagVo mediaTagVo, MediaTagTable mediaTagTable) {
        BeanUtils.copyNotNullProperties(mediaTagVo, mediaTagTable);
        return mediaTagTable;
    }
}
