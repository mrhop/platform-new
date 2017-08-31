package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.service.ArticleTableService;
import cn.hopever.platform.cms.vo.ArticleVo;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public class ArticleTableServiceImpl implements ArticleTableService{
    @Override
    public Page<ArticleVo> getList(TableParameters body, Principal principal) {
        return null;
    }

    @Override
    public void delete(Long id, Principal principal) {

    }

    @Override
    public ArticleVo info(Long id, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result update(ArticleVo ArticleVo, MultipartFile[] files, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result save(ArticleVo ArticleVo, MultipartFile[] files, Principal principal) {
        return null;
    }
}
