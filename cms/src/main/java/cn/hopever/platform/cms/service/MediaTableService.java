package cn.hopever.platform.cms.service;

import cn.hopever.platform.cms.vo.MediaVo;
import cn.hopever.platform.utils.web.GenericService;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/31.
 * 应该包含根据tagId获取可显示的media数目
 */
public interface MediaTableService extends GenericService<MediaVo> {
    public List<MediaVo> getListByMediaTagAndPublished(Long mediaTagId);

    public VueResults.Result upload(MultipartFile[] files, String tagId, Long websiteId, Long themeId, Principal principal);

    public VueResults.Result updatePublished(Long id, boolean published, Principal principal);

}
