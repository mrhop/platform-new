package cn.hopever.platform.crm.service.impl;

import cn.hopever.platform.crm.domain.DeliveryMethodTable;
import cn.hopever.platform.crm.domain.OrderTable;
import cn.hopever.platform.crm.repository.DeliveryMethodTableRepository;
import cn.hopever.platform.crm.service.DeliveryMethodTableService;
import cn.hopever.platform.crm.vo.DeliveryMethodVo;
import cn.hopever.platform.crm.vo.DeliveryMethodVoAssembler;
import cn.hopever.platform.utils.web.SelectOption;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
public class DeliveryMethodTableServiceImpl implements DeliveryMethodTableService {

    @Autowired
    private DeliveryMethodTableRepository deliveryMethodTableRepository;

    @Autowired
    private DeliveryMethodVoAssembler deliveryMethodVoAssembler;

    @Override
    public Page<DeliveryMethodVo> getList(TableParameters body, Principal principal) {
        PageRequest pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.ASC, "id");
        Page<DeliveryMethodTable> page = deliveryMethodTableRepository.findAll(pageRequest);
        List<DeliveryMethodVo> list = new ArrayList<>();
        for (DeliveryMethodTable deliveryMethodTable : page) {
            list.add(deliveryMethodVoAssembler.toResource(deliveryMethodTable));
        }
        return new PageImpl<>(list, pageRequest, page.getTotalElements());
    }

    @Override
    public void delete(Long id, Principal principal) {
        DeliveryMethodTable deliveryMethodTable = deliveryMethodTableRepository.findOne(id);
        for(OrderTable orderTable:deliveryMethodTable.getOrderTables()){
            orderTable.setDeliveryMethodTable(null);
        }
        deliveryMethodTableRepository.delete(deliveryMethodTable);
    }

    @Override
    public DeliveryMethodVo info(Long id, Principal principal) {
        return deliveryMethodVoAssembler.toResource(deliveryMethodTableRepository.findOne(id));
    }

    @Override
    public VueResults.Result update(DeliveryMethodVo deliveryMethodVo, MultipartFile[] files, Principal principal) {
        deliveryMethodTableRepository.save(deliveryMethodVoAssembler.toDomain(deliveryMethodVo, deliveryMethodTableRepository.findOne(deliveryMethodVo.getId())));
        return null;
    }

    @Override
    public VueResults.Result save(DeliveryMethodVo deliveryMethodVo, MultipartFile[] files, Principal principal) {
        DeliveryMethodTable deliveryMethodTable = new DeliveryMethodTable();
        deliveryMethodTableRepository.save(deliveryMethodVoAssembler.toDomain(deliveryMethodVo, deliveryMethodTable));
        return null;
    }

    @Override
    public List<SelectOption> getDeliveryMethodOptions(Principal principal) {
        Iterable<DeliveryMethodTable> deliveryMethodTables = deliveryMethodTableRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        List<SelectOption> list = new ArrayList<>();
        for (DeliveryMethodTable deliveryMethodTable : deliveryMethodTables) {
            list.add(new SelectOption(deliveryMethodTable.getName(), deliveryMethodTable.getId()));
        }
        return list;
    }
}
