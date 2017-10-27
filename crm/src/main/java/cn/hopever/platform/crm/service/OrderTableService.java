package cn.hopever.platform.crm.service;

import cn.hopever.platform.crm.vo.OrderVo;
import cn.hopever.platform.utils.web.GenericService;

import java.util.Date;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public interface OrderTableService extends GenericService<OrderVo> {
    public Float estimatePrice(float prePrice, Long clientId);

    public String getStatusCode(long id);

    public List<Object[]> analyzeOrderFromCountry(Date beginDate, Date endDate);

    public List<Object[]> analyzeOrderAmountFromUser(Date beginDate, Date endDate);

    public List<Object[]> analyzeOrderFromClient(Date beginDate, Date endDate, Long clientId);

    public List<Object[]> analyzeOrderFromCreatedUser(Date beginDate, Date endDate, Long userId);
}
