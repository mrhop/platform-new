package cn.hopever.platform.cms.vo;

import cn.hopever.platform.cms.domain.TemplateTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class TemplateVoAssembler implements GenericVoAssembler<TemplateVo, TemplateTable> {

    @Override
    public TemplateVo toResource(TemplateTable templateTable) {
        TemplateVo templateVo = new TemplateVo();
        BeanUtils.copyNotNullProperties(templateTable, templateVo);
        templateVo.setThemeId(templateTable.getThemeTable().getId());
        templateVo.setThemeName(templateTable.getThemeTable().getName());
        return templateVo;
    }

    @Override
    public TemplateTable toDomain(TemplateVo templateVo, TemplateTable templateTable) {
        BeanUtils.copyNotNullProperties(templateVo, templateTable);
        return templateTable;
    }
}
