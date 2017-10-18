package cn.hopever.platform.crm.vo;

import lombok.Data;

/**
 * Created by Donghui Huo on 2017/8/30.
 */
@Data
public class RelatedUserVo {
    private Long id;

    private String account;

    private String name;

    private boolean customDiscount;
    private String customDiscountStr;

    private Float lowerLimit;

    private Long createdDate;

}
