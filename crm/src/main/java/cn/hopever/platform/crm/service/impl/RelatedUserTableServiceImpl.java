package cn.hopever.platform.crm.service.impl;

import cn.hopever.platform.crm.service.RelatedUserTableService;
import cn.hopever.platform.crm.vo.RelatedUserVo;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * Created by Donghui Huo on 2017/10/10.
 * 登陆的时候添加
 */
@Service
@Transactional
public class RelatedUserTableServiceImpl implements RelatedUserTableService {
    @Override
    public Page<RelatedUserVo> getList(TableParameters body, Principal principal) {
        return null;
    }

    @Override
    public void delete(Long id, Principal principal) {

    }

    @Override
    public RelatedUserVo info(Long id, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result update(RelatedUserVo relatedUserVo, MultipartFile[] files, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result save(RelatedUserVo relatedUserVo, MultipartFile[] files, Principal principal) {
        return null;
    }
}
