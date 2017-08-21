package cn.hopever.platform.user.vo;

import cn.hopever.platform.user.domain.ResourceScopeTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class ResourceScopeVoAssembler {


    public ResourceScopeVoAssembler() {
    }

    public ResourceScopeVo toResource(ResourceScopeTable resourceScopeTable) {
        ResourceScopeVo resource = new ResourceScopeVo();
        BeanUtils.copyNotNullProperties(resourceScopeTable, resource);
        return resource;
    }

    public ResourceScopeTable toDomain(ResourceScopeVo resourceScopeVo, ResourceScopeTable resourceScopeTable) {
        BeanUtils.copyNotNullProperties(resourceScopeVo, resourceScopeTable, "scopeId");
        return resourceScopeTable;
    }
}
