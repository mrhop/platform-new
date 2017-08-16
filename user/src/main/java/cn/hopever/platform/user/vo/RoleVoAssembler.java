package cn.hopever.platform.user.vo;

import cn.hopever.platform.user.domain.RoleTable;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class RoleVoAssembler  {

    public RoleVo toResource(RoleTable roleTable) {
        RoleVo resource = createResource(roleTable);
        //关联其他资源,暂时不关联其他资源
        return resource;
    }

    private RoleVo createResource(RoleTable roleTable) {
        RoleVo RoleVo = null;
        return RoleVo;
    }
}
