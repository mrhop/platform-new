package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.service.MediaTableService;
import cn.hopever.platform.cms.vo.MediaVo;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public class MediaTableServiceImpl implements MediaTableService {
    @Override
    public Page<MediaVo> getList(TableParameters body, Principal principal) {
        return null;
    }

    @Override
    public void delete(Long id, Principal principal) {

    }

    @Override
    public MediaVo info(Long id, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result update(MediaVo MediaVo, MultipartFile[] files, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result save(MediaVo MediaVo, MultipartFile[] files, Principal principal) {
        return null;
    }
}
