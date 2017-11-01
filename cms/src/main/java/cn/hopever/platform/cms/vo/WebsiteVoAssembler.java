package cn.hopever.platform.cms.vo;

import cn.hopever.platform.cms.domain.WebsiteTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class WebsiteVoAssembler implements GenericVoAssembler<WebsiteVo, WebsiteTable> {

    @Override
    public WebsiteVo toResource(WebsiteTable websiteTable) {
        WebsiteVo websiteVo = new WebsiteVo();
        BeanUtils.copyNotNullProperties(websiteTable, websiteVo);
        if(websiteTable.getScreenshots()!=null){
            websiteVo.setScreenshots(websiteTable.getScreenshots());
        }
        websiteVo.setThemeId(websiteTable.getThemeTable().getId());
        websiteVo.setThemeName(websiteTable.getThemeTable().getName());
        return websiteVo;
    }

    @Override
    public WebsiteTable toDomain(WebsiteVo websiteVo, WebsiteTable websiteTable) {
        BeanUtils.copyNotNullProperties(websiteVo, websiteTable);
        return websiteTable;
    }
}
