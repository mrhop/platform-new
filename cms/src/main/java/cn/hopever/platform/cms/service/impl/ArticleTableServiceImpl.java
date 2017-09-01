package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.service.ArticleTableService;
import cn.hopever.platform.cms.vo.ArticleVo;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
@Service
@Transactional
public class ArticleTableServiceImpl implements ArticleTableService{
    @Override
    public Page<ArticleVo> getList(TableParameters body, Principal principal) {
        // website -- 该用户可用website
        // 且是否有moduleRole -- 这个在action中实现，使用注解的方式
        // article tag
        // 默认按照创建时间倒序
        // 是否发布 排序
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

    @Override
    public VueResults.Result updatePublished(Long id, boolean published) {
        return null;
    }
}
