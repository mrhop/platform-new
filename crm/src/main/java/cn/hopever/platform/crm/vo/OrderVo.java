package cn.hopever.platform.crm.vo;

import lombok.Data;

/**
 * Created by Donghui Huo on 2017/8/30.
 */

@Data
public class OrderVo {

    private Long id;

    private String code;

    private String status;

    private String discountType;

    private float discount;

    private Long clientId;
    private String clientName;


    private Long countryId;
    private String countryName;

    private Long deliveryMethodId;
    private String deliveryMethodName;

    private String tracingNumber;


    private Long payTypeId;
    private String payTypeName;

    private float freight;

    private float salePrice;

    private float preQuotation;

    private float costPrice;

    private Long createdDate;
    private Long createdUserId;
    private String createdUserName;

    private Long contractSignDate;

    private Long deliveryDate;

    private Long finishedDate;

    private String additionalMsg;
}
