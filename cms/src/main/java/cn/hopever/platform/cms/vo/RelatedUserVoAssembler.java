package cn.hopever.platform.cms.vo;

import cn.hopever.platform.cms.domain.RelatedUserTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class RelatedUserVoAssembler implements GenericVoAssembler<RelatedUserVo, RelatedUserTable> {

    @Override
    public RelatedUserVo toResource(RelatedUserTable relatedUserTable) {
        RelatedUserVo relatedUserVo = new RelatedUserVo();
        BeanUtils.copyNotNullProperties(relatedUserTable, relatedUserVo);
        relatedUserVo.setCreatedDate(relatedUserTable.getCreatedDate().getTime());
        return relatedUserVo;
    }

    @Override
    public RelatedUserTable toDomain(RelatedUserVo relatedUserVo, RelatedUserTable relatedUserTable) {
        BeanUtils.copyNotNullProperties(relatedUserVo, relatedUserTable, "id", "account");
        return relatedUserTable;
    }
}
