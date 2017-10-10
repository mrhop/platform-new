package cn.hopever.platform.crm.vo;

import lombok.Data;

/**
 * Created by Donghui Huo on 2017/8/30.
 * 注意商品有二维码的生成以及扫码识别方式
 */

@Data
public class ProductVo {
    private Long id;

    private String name;

    private String code;

    private float costPrice;

    private float salePrice;

    private String unit;

    private String specification;

    private String color;

    private String[] pictures;

    private String additionalMsg;

    private Long createdDate;
    private Long createdUserId;
    private String createdUserName;

    private Long productCategoryId;
    private String productCategoryName;
}
