package cn.hopever.platform.cms.service;

import cn.hopever.platform.cms.vo.MediaTagVo;
import cn.hopever.platform.utils.web.GenericService;
import cn.hopever.platform.utils.web.SelectOption;

import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/31.
 * 这个类似于blockTag，没有多余的操作
 */
public interface MediaTagTableService extends GenericService<MediaTagVo> {
    public List<SelectOption> getOptionsByWebsiteId(Long websiteId);

    public List<SelectOption> getOptionsByThemeId(Long themeId);

}
