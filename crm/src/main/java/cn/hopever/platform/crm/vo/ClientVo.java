package cn.hopever.platform.crm.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/30.
 */

@Data
public class ClientVo {
    private Long id;

    private String code;

    private String name;

    private boolean traded;

    private String contact;

    private String address;

    private String telephone;

    private String cellphone;

    private String email;

    private String wechat;

    private String skype;

    private String additionalMsg;

    private Long clientOriginId;

    private String clientOriginName;

    private Long clientLevelId;

    private String clientLevelName;

    private Long countryId;
    private String countryName;

    private List<String> relatedUserAccounts;
    private List<Long> relatedUserIds;


    private Long createdDate;

    private Long createdUserId;
    private String createdUserName;

    // 总成交额
    private Float orderSum;

}
