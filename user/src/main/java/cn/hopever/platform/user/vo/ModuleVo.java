package cn.hopever.platform.user.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Donghui Huo on 2016/8/31.
 */
@Data
@EqualsAndHashCode(callSuper = false)
//获取角色的一些信息并进行处理，
public class ModuleVo {

    @NotNull
    private long id;

    private String clientName;
    private Long clientId;// list filter  // add select
    private String moduleName; // list   // add text
    private Integer moduleOrder;
    private Long beforeId; // add 联动 parent tree form element
    private String moduleUrl; // add nullable

    private String iconClass; // add nullable
    private Long parentId;// list filter [tree form element] 联动clientId
    private String parentName;

    private List<Long> children;
    private String childrenStr;// list

    private boolean available = true;// add  select
    private boolean activated = true;// list // add  select
    private List<Long> authorities; // add  checkbox 联动clientId
    private String authoritiesStr; // list

}
