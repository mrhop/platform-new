package cn.hopever.platform.crm.service.impl;

import cn.hopever.platform.crm.domain.OrderProductTable;
import cn.hopever.platform.crm.domain.OrderStatusTable;
import cn.hopever.platform.crm.repository.OrderProductTableRepository;
import cn.hopever.platform.crm.repository.OrderTableRepository;
import cn.hopever.platform.crm.repository.ProductTableRepository;
import cn.hopever.platform.crm.service.OrderProductTableService;
import cn.hopever.platform.crm.vo.OrderProductVo;
import cn.hopever.platform.crm.vo.OrderProductVoAssembler;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/10/10.
 */
@Service
@Transactional
public class OrderProductTableServiceImpl implements OrderProductTableService {

    @Autowired
    private OrderProductTableRepository orderProductTableRepository;

    @Autowired
    private OrderTableRepository orderTableRepository;
    @Autowired
    private ProductTableRepository productTableRepository;

    @Autowired
    private OrderProductVoAssembler orderProductVoAssembler;

    @Override
    public List<OrderProductVo> getListByOrderId(TableParameters body, Principal principal, Long orderId) {
        List<OrderProductTable> orderProductTables = orderTableRepository.findOne(orderId).getOrderProductTables();
        List<OrderProductVo> list = new ArrayList<>();
        for (OrderProductTable orderProductTable : orderProductTables) {
            OrderProductVo orderProductVo = orderProductVoAssembler.toResource(orderProductTable);
            list.add(orderProductVo);
        }
        return list;
    }

    @Override
    public VueResults.Result updateNumById(Long id, float num) {
        OrderProductTable orderProductTable = orderProductTableRepository.findOne(id);
        OrderStatusTable orderStatusTable = orderProductTable.getOrderTable().getOrderStatusTable();
        // 创建和报价的时候可以进行商品变更
        if (orderStatusTable.getCode().equals("created") || orderStatusTable.getCode().equals("offer")) {
            // 在更新后需要重新计算预估金额
            // 订单状态只有持续走下去，或者回退的情况，没有跳过的情况
            orderProductTableRepository.updateNum(num, id);
            return null;
        } else {
            return VueResults.generateError("更新错误", "商品已经被锁定");
        }
    }

    @Override
    public Page<OrderProductVo> getList(TableParameters body, Principal principal) {
        return null;
    }

    @Override
    public void delete(Long id, Principal principal) {
    }

    @Override
    public void deleteWithRestrict(Long id, Principal principal) throws Exception {
        OrderProductTable orderProductTable = orderProductTableRepository.findOne(id);
        OrderStatusTable orderStatusTable = orderProductTable.getOrderTable().getOrderStatusTable();
        if (orderStatusTable.getCode().equals("created") || orderStatusTable.getCode().equals("offer") || orderStatusTable.getCode().equals("preContract")) {
            // 在更新后需要重新计算预估金额
            // 订单状态只有持续走下去，或者回退的情况，没有跳过的情况
            orderProductTableRepository.delete(id);
        } else {
            throw new Exception("删除错误，没有权限");
        }
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
        OrderProductTable orderProductTable = new OrderProductTable();
        orderProductTable.setOrderTable(orderTableRepository.findOne(orderProductVo.getOrderId()));
        orderProductTable.setProductTable(productTableRepository.findOne(orderProductVo.getProductId()));
        orderProductTable.setNum(1f);
        orderProductTableRepository.save(orderProductTable);
        return null;
    }
}
