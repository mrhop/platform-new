package cn.hopever.platform.crm.vo;

import lombok.Data;

/**
 * Created by Donghui Huo on 2017/8/30.
 */
@Data
public class ClientTrackVo {
    private Long id;
    private Long clientId;
    private String clientName;
    private String trackMethod;
    private Long trackUserId;
    private String trackUserName;
    private Long trackDate;
    private Short duration;
    private String comment;
    private Short result;
}
