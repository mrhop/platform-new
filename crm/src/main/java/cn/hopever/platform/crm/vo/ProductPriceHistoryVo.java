package cn.hopever.platform.crm.vo;

import lombok.Data;

/**
 * Created by Donghui Huo on 2017/8/30.
 */
@Data
public class ProductPriceHistoryVo {

    private Long id;
    private Long productId;
    private String productName;

    private Long endDate;

    private Float salePrice;

    private Float costPrice;

}
