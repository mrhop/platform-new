package cn.hopever.platform.crm.service.impl;

import cn.hopever.platform.crm.service.CountryTableService;
import cn.hopever.platform.crm.vo.CountryVo;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * Created by Donghui Huo on 2017/10/10.
 */
@Service
@Transactional
public class CountryTableServiceImpl implements CountryTableService {
    @Override
    public Page<CountryVo> getList(TableParameters body, Principal principal) {
        return null;
    }

    @Override
    public void delete(Long id, Principal principal) {

    }

    @Override
    public CountryVo info(Long id, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result update(CountryVo countryVo, MultipartFile[] files, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result save(CountryVo countryVo, MultipartFile[] files, Principal principal) {
        return null;
    }
}
