package cn.hopever.platform.crm.service;

import cn.hopever.platform.crm.vo.ClientVo;
import cn.hopever.platform.utils.web.GenericService;
import cn.hopever.platform.utils.web.SelectOption;

import java.security.Principal;
import java.util.Date;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public interface ClientTableService extends GenericService<ClientVo> {
    public List<SelectOption> getClientOptions(Principal principal);

    public List<Object[]> analyzeClientOrigin(Float orderAmount);

    public List<Object[]> analyzeClientFromCountry(Date beginDate, Date endDate);

    public List<Object[]> analyzeOrderAmountFromClient(Date beginDate, Date endDate);

    public List<Object[]> analyzeClientFromCreatedUser(Date beginDate, Date endDate, Long clientLevelId, Long userId);

}
