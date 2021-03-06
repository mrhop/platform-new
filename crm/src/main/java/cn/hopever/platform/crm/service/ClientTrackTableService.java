package cn.hopever.platform.crm.service;

import cn.hopever.platform.crm.vo.ClientTrackVo;
import cn.hopever.platform.utils.web.GenericService;
import cn.hopever.platform.utils.web.TableParameters;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.Date;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public interface ClientTrackTableService extends GenericService<ClientTrackVo> {

    public Page<ClientTrackVo> getList(TableParameters body, Principal principal, Long clientId);

    public List<Object[]> analyzeClientTrackFromTrackUser(Date beginDate, Date endDate, Long clientId, Long userId);

}
