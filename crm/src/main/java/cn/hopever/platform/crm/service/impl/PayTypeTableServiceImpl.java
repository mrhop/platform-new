package cn.hopever.platform.crm.service.impl;

import cn.hopever.platform.crm.service.PayTypeTableService;
import cn.hopever.platform.crm.vo.PayTypeVo;
import cn.hopever.platform.utils.web.SelectOption;
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
public class PayTypeTableServiceImpl implements PayTypeTableService {
    @Override
    public Page<PayTypeVo> getList(TableParameters body, Principal principal) {
        return null;
    }

    @Override
    public void delete(Long id, Principal principal) {

    }

    @Override
    public PayTypeVo info(Long id, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result update(PayTypeVo payTypeVo, MultipartFile[] files, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result save(PayTypeVo payTypeVo, MultipartFile[] files, Principal principal) {
        return null;
    }

    @Override
    public List<SelectOption> getPayTypeOptions(Principal principal) {
        return null;
    }
}
