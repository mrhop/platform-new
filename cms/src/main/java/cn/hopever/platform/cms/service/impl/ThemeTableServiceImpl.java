package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.service.ThemeTableService;
import cn.hopever.platform.cms.vo.ThemeVo;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public class ThemeTableServiceImpl implements ThemeTableService {
    @Override
    public Page<ThemeVo> getList(TableParameters body, Principal principal) {
        return null;
    }

    @Override
    public void delete(Long id, Principal principal) {

    }

    @Override
    public ThemeVo info(Long id, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result update(ThemeVo themeVo, MultipartFile[] files, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result save(ThemeVo themeVo, MultipartFile[] files, Principal principal) {
        return null;
    }
}
