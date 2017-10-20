package cn.hopever.platform.crm.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/30.
 */

@Data
public class OrderVo {

    private Long id;
    private String code;

    private Long orderStatusId;
    private String orderStatusName;
    private String orderStatusCode;

    private String discountType;

    private Float discount;

    private Long clientId;
    private String clientName;

    private Long countryId;
    private String countryName;

    private Long deliveryMethodId;
    private String deliveryMethodName;

    private String tracingNumber;


    private Long payTypeId;
    private String payTypeName;

    private Float freight;

    private Float salePrice;

    private Float preQuotation;

    private Float costPrice;

    private Long createdDate;
    private Long createdUserId;
    private String createdUserName;

    private Long contractSignDate;

    private Long deliveryDate;

    private Long finishedDate;

    private String additionalMsg;

    private List<OrderProductVo> orderProducts;
}
