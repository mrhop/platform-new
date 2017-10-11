package cn.hopever.platform.crm.vo;

import lombok.Data;

/**
 * Created by Donghui Huo on 2017/8/30.
 * 自动选定打折后总价最低的，节日会有折上折
 */
@Data
public class OrderDiscountVo {

    private Long id;

    private String type;

    private Float quota;

    private Long beginDate;

    private Long endDate;

    private Long clientLevelId;
    private String clientLevelName;

    private Float discount;

    private Float reduce;

    private Long createdDate;

}
