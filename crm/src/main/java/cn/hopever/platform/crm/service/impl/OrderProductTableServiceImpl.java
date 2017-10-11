package cn.hopever.platform.crm.service.impl;

import cn.hopever.platform.crm.service.OrderProductTableService;
import cn.hopever.platform.crm.vo.OrderProductVo;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/10/10.
 */
@Service
@Transactional
public class OrderProductTableServiceImpl implements OrderProductTableService {


    @Override
    public List<OrderProductVo> getListByOrderId(TableParameters body, Principal principal, Long orderId) {
        return null;
    }

    @Override
    public Page<OrderProductVo> getList(TableParameters body, Principal principal) {
        return null;
    }

    @Override
    public void delete(Long id, Principal principal) {

    }

    @Override
    public OrderProductVo info(Long id, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result update(OrderProductVo orderProductVo, MultipartFile[] files, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result save(OrderProductVo orderProductVo, MultipartFile[] files, Principal principal) {
        return null;
    }
}
