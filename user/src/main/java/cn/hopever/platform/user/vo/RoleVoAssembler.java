package cn.hopever.platform.user.vo;

import cn.hopever.platform.user.domain.RoleTable;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class RoleVoAssembler  {

    private ModelMapper modelMapper;

    public RoleVoAssembler() {
        modelMapper = new ModelMapper();
        PropertyMap<RoleTable, RoleVo> map = new PropertyMap<RoleTable, RoleVo>() {
            protected void configure() {
                skip().setUsers(null);
            }
        };
        modelMapper.addMappings(map);
    }


    public RoleVo toResource(RoleTable roleTable) {
        RoleVo resource = createResource(roleTable);
        //关联其他资源,暂时不关联其他资源
        return resource;
    }

    private RoleVo createResource(RoleTable roleTable) {
        RoleVo RoleVo = null;
        if (roleTable != null) {
            RoleVo = modelMapper.map(roleTable,RoleVo.class);
        }
        return RoleVo;
    }
}
