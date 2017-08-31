package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.service.ArticleTagTableService;
import cn.hopever.platform.cms.vo.ArticleTagVo;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public class ArticleTagTableServiceImpl implements ArticleTagTableService {
    @Override
    public Page<ArticleTagVo> getList(TableParameters body, Principal principal) {
        return null;
    }

    @Override
    public void delete(Long id, Principal principal) {

    }

    @Override
    public ArticleTagVo info(Long id, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result update(ArticleTagVo ArticleTagVo, MultipartFile[] files, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result save(ArticleTagVo ArticleTagVo, MultipartFile[] files, Principal principal) {
        return null;
    }
}
