package cn.hopever.platform.crm.service;

import cn.hopever.platform.crm.vo.ClientOriginVo;
import cn.hopever.platform.utils.web.GenericService;
import cn.hopever.platform.utils.web.SelectOption;

import java.security.Principal;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public interface ClientOriginTableService extends GenericService<ClientOriginVo> {

    public List<SelectOption> getClientOriginOptions(Principal principal);
}
