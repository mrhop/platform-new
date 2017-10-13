package cn.hopever.platform.crm.service;

import cn.hopever.platform.crm.vo.OrderProductVo;
import cn.hopever.platform.utils.web.GenericService;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;

import java.security.Principal;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public interface OrderProductTableService extends GenericService<OrderProductVo> {
    List<OrderProductVo> getListByOrderId(TableParameters body, Principal principal, Long orderId);

    public VueResults.Result updateNumById(Long id, float num);

    public void deleteWithRestrict(Long id, Principal principal) throws Exception;
}
