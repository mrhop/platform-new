package cn.hopever.platform.crm.service.impl;

import cn.hopever.platform.crm.domain.OrderTable;
import cn.hopever.platform.crm.domain.PayTypeTable;
import cn.hopever.platform.crm.repository.PayTypeTableRepository;
import cn.hopever.platform.crm.service.PayTypeTableService;
import cn.hopever.platform.crm.vo.PayTypeVo;
import cn.hopever.platform.crm.vo.PayTypeVoAssembler;
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
public class PayTypeTableServiceImpl implements PayTypeTableService {

    @Autowired
    private PayTypeTableRepository payTypeTableRepository;

    @Autowired
    private PayTypeVoAssembler payTypeVoAssembler;

    @Override
    public Page<PayTypeVo> getList(TableParameters body, Principal principal) {
        PageRequest pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.ASC, "id");
        Page<PayTypeTable> page = payTypeTableRepository.findAll(pageRequest);
        List<PayTypeVo> list = new ArrayList<>();
        for (PayTypeTable payTypeTable : page) {
            list.add(payTypeVoAssembler.toResource(payTypeTable));
        }
        return new PageImpl<>(list, pageRequest, page.getTotalElements());
    }

    @Override
    public void delete(Long id, Principal principal) {
        PayTypeTable payTypeTable = payTypeTableRepository.findOne(id);
        for (OrderTable orderTable : payTypeTable.getOrderTables()) {
            orderTable.setPayTypeTable(null);
        }
        payTypeTableRepository.delete(payTypeTable);
    }

    @Override
    public PayTypeVo info(Long id, Principal principal) {
        return payTypeVoAssembler.toResource(payTypeTableRepository.findOne(id));
    }

    @Override
    public VueResults.Result update(PayTypeVo payTypeVo, MultipartFile[] files, Principal principal) {
        payTypeTableRepository.save(payTypeVoAssembler.toDomain(payTypeVo, payTypeTableRepository.findOne(payTypeVo.getId())));
        return null;
    }

    @Override
    public VueResults.Result save(PayTypeVo payTypeVo, MultipartFile[] files, Principal principal) {
        payTypeTableRepository.save(payTypeVoAssembler.toDomain(payTypeVo, new PayTypeTable()));
        return null;
    }

    @Override
    public List<SelectOption> getPayTypeOptions(Principal principal) {
        Iterable<PayTypeTable> payTypeTables = payTypeTableRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        List<SelectOption> list = new ArrayList<>();
        for (PayTypeTable payTypeTable : payTypeTables) {
            list.add(new SelectOption(payTypeTable.getName(), payTypeTable.getId()));
        }
        return list;
    }
}
