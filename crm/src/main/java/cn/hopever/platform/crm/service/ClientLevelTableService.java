package cn.hopever.platform.crm.service;

import cn.hopever.platform.crm.vo.ClientLevelVo;
import cn.hopever.platform.utils.web.GenericService;
import cn.hopever.platform.utils.web.SelectOption;

import java.security.Principal;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public interface ClientLevelTableService extends GenericService<ClientLevelVo> {

    // 给出全部options
    public List<SelectOption> getClientLevelOptions(Principal principal);

    // 给出未交易的options
    public List<SelectOption> getClientLevelNoOrderAmountOptions(Principal principal);

}
