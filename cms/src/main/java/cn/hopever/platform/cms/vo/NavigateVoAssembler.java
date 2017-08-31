package cn.hopever.platform.cms.vo;

import cn.hopever.platform.cms.domain.NavigateTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class NavigateVoAssembler implements GenericVoAssembler<NavigateVo, NavigateTable> {

    @Override
    public NavigateVo toResource(NavigateTable navigateTable) {
        NavigateVo navigateVo = new NavigateVo();
        BeanUtils.copyNotNullProperties(navigateTable, navigateVo);
        if (navigateTable.getArticleTable() != null) {
            navigateVo.setArticleId(navigateTable.getArticleTable().getId());
            navigateVo.setArticleTitle(navigateTable.getArticleTable().getTitle());
        }
        if (navigateTable.getBeforeNavigate() != null) {
            navigateVo.setBeforeId(navigateTable.getBeforeNavigate().getId());
        }

        if (navigateTable.getParent() != null) {
            navigateVo.setParentId(navigateTable.getParent().getId());
            navigateVo.setParentName(navigateTable.getParent().getName());
        }
        navigateVo.setWebsiteId(navigateTable.getWebsiteTable().getId());
        navigateVo.setWebsiteName(navigateTable.getWebsiteTable().getName());
        return navigateVo;
    }

    @Override
    public NavigateTable toDomain(NavigateVo navigateVo, NavigateTable navigateTable) {
        BeanUtils.copyNotNullProperties(navigateVo, navigateTable);
        return navigateTable;
    }
}
