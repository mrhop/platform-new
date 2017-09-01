package cn.hopever.platform.user.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Donghui Huo on 2016/8/31.
 */
@Data
@EqualsAndHashCode(callSuper = false)
//获取角色的一些信息并进行处理，
public class ModuleVo {

    private long id;

    private String clientName;
    private Long clientId;// list filter  // add select
    private String moduleName; // list   // add text
    private String moduleId; // list   // add text
    private Long beforeId; // add 联动 parent tree form element
    private String moduleUrl; // add nullable

    private String iconClass; // add nullable
    private Long parentId;// list filter [tree form element] 联动clientId ,不需要parentId在add因为使用beforeId就能完备的体现出其从属的parent
    private String parentName;

    private boolean available = true;// add  select
    private boolean activated = true;// list // add  select
    //private List<Long> authorities; // add  checkbox 联动clientId
    private String authoritiesStr; // add  checkbox 联动clientId
    private Long authorityId; // list 用于过滤模块权限

}
