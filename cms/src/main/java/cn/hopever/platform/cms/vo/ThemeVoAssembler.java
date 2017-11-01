package cn.hopever.platform.cms.vo;

import cn.hopever.platform.cms.domain.ThemeTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class ThemeVoAssembler implements GenericVoAssembler<ThemeVo, ThemeTable> {

    @Override
    public ThemeVo toResource(ThemeTable themeTable) {
        ThemeVo themeVo = new ThemeVo();
        BeanUtils.copyNotNullProperties(themeTable, themeVo);
        if (themeTable.getScreenshots() != null) {
            themeVo.setScreenshots(themeTable.getScreenshots());
        }
        return themeVo;
    }

    @Override
    public ThemeTable toDomain(ThemeVo themeVo, ThemeTable themeTable) {
        BeanUtils.copyNotNullProperties(themeVo, themeTable);
        return themeTable;
    }
}
