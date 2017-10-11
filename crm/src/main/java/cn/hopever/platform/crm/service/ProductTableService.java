package cn.hopever.platform.crm.service;

import cn.hopever.platform.crm.vo.ProductPriceHistoryVo;
import cn.hopever.platform.crm.vo.ProductVo;
import cn.hopever.platform.utils.web.GenericService;
import cn.hopever.platform.utils.web.TableParameters;

import java.security.Principal;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public interface ProductTableService extends GenericService<ProductVo> {
    public List<ProductPriceHistoryVo> getHistoryListByProductId(TableParameters body, Principal principal, Long productId);
}
