package cn.hopever.platform.crm.service.impl;

import cn.hopever.platform.crm.service.ClientTableService;
import cn.hopever.platform.crm.vo.ClientVo;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
@Service
@Transactional
public class ClientTableServiceImpl implements ClientTableService {


    @Override
    public Page<ClientVo> getList(TableParameters body, Principal principal) {
        return null;
    }

    @Override
    public void delete(Long id, Principal principal) {

    }

    @Override
    public ClientVo info(Long id, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result update(ClientVo clientVo, MultipartFile[] files, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result save(ClientVo clientVo, MultipartFile[] files, Principal principal) {
        return null;
    }
}
