package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.service.TemplateTableService;
import cn.hopever.platform.cms.vo.TemplateVo;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public class TemplateTableServiceImpl implements TemplateTableService {
    @Override
    public Page<TemplateVo> getList(TableParameters body, Principal principal) {
        return null;
    }

    @Override
    public void delete(Long id, Principal principal) {

    }

    @Override
    public TemplateVo info(Long id, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result update(TemplateVo templateVo, MultipartFile[] files, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result save(TemplateVo templateVo, MultipartFile[] files, Principal principal) {
        return null;
    }
}
