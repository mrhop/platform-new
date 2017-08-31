package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.service.StaticResourceTableService;
import cn.hopever.platform.cms.vo.StaticResourceVo;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public class StaticResourceTableServiceImpl implements StaticResourceTableService {
    @Override
    public Page<StaticResourceVo> getList(TableParameters body, Principal principal) {
        return null;
    }

    @Override
    public void delete(Long id, Principal principal) {

    }

    @Override
    public StaticResourceVo info(Long id, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result update(StaticResourceVo staticResourceVo, MultipartFile[] files, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result save(StaticResourceVo staticResourceVo, MultipartFile[] files, Principal principal) {
        return null;
    }
}
