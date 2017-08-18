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
    private Long clientId;
    private String moduleName;
    private Integer moduleOrder;
    private Long beforeId;
    private String moduleUrl;

    private String iconClass;
    private Long parentId;
    private String parentName;

    private List<Long> children;

    private boolean available = true;
    private boolean activated = true;
    private List<Long> authorities;

}
