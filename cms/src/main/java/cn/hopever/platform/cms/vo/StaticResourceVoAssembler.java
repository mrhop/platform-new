package cn.hopever.platform.cms.vo;

import cn.hopever.platform.cms.config.CmsProperties;
import cn.hopever.platform.cms.domain.StaticResourceTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class StaticResourceVoAssembler implements GenericVoAssembler<StaticResourceVo, StaticResourceTable> {

    @Autowired
    private CmsProperties cmsProperties;

    @Override
    public StaticResourceVo toResource(StaticResourceTable staticResourceTable) {
        StaticResourceVo staticResourceVo = new StaticResourceVo();
        BeanUtils.copyNotNullProperties(staticResourceTable, staticResourceVo);
        if (staticResourceTable.getArticleTable() != null) {
            staticResourceVo.setArticleId(staticResourceTable.getArticleTable().getId());
            staticResourceVo.setArticleTitle(staticResourceTable.getArticleTable().getTitle());
        }
        if (staticResourceTable.getThemeTable() != null) {
            staticResourceVo.setThemeId(staticResourceTable.getThemeTable().getId());
            staticResourceVo.setThemeName(staticResourceTable.getName());
        }
        if (staticResourceTable.getWebsiteTable() != null) {
            staticResourceVo.setWebsiteId(staticResourceTable.getWebsiteTable().getId());
            staticResourceVo.setWebsiteName(staticResourceTable.getWebsiteTable().getName());
        }
        if (staticResourceTable.getBeforeStaticResource() != null) {
            staticResourceVo.setBeforeId(staticResourceTable.getBeforeStaticResource().getId());
            staticResourceVo.setBeforeName(staticResourceTable.getBeforeStaticResource().getName());
        }
        staticResourceVo.setUrl(staticResourceVo.getUrl() != null ? cmsProperties.getStaticResourceRelativePath() + staticResourceVo.getUrl() : null);
        return staticResourceVo;
    }

    @Override
    public StaticResourceTable toDomain(StaticResourceVo staticResourceVo, StaticResourceTable staticResourceTable) {
        BeanUtils.copyNotNullProperties(staticResourceVo, staticResourceTable);
        return staticResourceTable;
    }
}
