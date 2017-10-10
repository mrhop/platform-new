package cn.hopever.platform.crm.vo;

import lombok.Data;

/**
 * Created by Donghui Huo on 2017/10/9.
 */

@Data
public class OrderProductVo {

    private Long id;

    private Long orderId;
    private String orderName;


    private Long productId;
    private String productName;
    private String productCode;

    private float num;
}
