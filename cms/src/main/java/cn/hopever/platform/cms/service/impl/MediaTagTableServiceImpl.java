package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.service.MediaTagTableService;
import cn.hopever.platform.cms.vo.MediaTagVo;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public class MediaTagTableServiceImpl implements MediaTagTableService {
    @Override
    public Page<MediaTagVo> getList(TableParameters body, Principal principal) {
        return null;
    }

    @Override
    public void delete(Long id, Principal principal) {

    }

    @Override
    public MediaTagVo info(Long id, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result update(MediaTagVo MediaTagVo, MultipartFile[] files, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result save(MediaTagVo MediaTagVo, MultipartFile[] files, Principal principal) {
        return null;
    }
}
