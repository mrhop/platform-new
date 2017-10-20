package cn.hopever.platform.crm.service;

import cn.hopever.platform.crm.vo.OrderVo;
import cn.hopever.platform.utils.web.GenericService;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public interface OrderTableService extends GenericService<OrderVo> {
    public Float estimatePrice(float prePrice, Long clientId);
    public String getStatusCode(long id);
}
