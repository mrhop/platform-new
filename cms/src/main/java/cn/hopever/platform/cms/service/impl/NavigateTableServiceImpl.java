package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.service.NavigateTableService;
import cn.hopever.platform.cms.vo.NavigateVo;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public class NavigateTableServiceImpl implements NavigateTableService {
    @Override
    public Page<NavigateVo> getList(TableParameters body, Principal principal) {
        return null;
    }

    @Override
    public void delete(Long id, Principal principal) {

    }

    @Override
    public NavigateVo info(Long id, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result update(NavigateVo NavigateVo, MultipartFile[] files, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result save(NavigateVo NavigateVo, MultipartFile[] files, Principal principal) {
        return null;
    }
}
