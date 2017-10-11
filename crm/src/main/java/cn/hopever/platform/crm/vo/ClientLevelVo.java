package cn.hopever.platform.crm.vo;

import lombok.Data;

/**
 * Created by Donghui Huo on 2017/8/30.
 */

@Data
public class ClientLevelVo {
    private Long id;

    private String name;

    private Float orderAmount;

    private String additionalMsg;
}
