package cn.hopever.platform.crm.service.impl;

import cn.hopever.platform.crm.service.ProductCategoryTableService;
import cn.hopever.platform.crm.vo.ProductCategoryVo;
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
public class ProductCategoryTableServiceImpl implements ProductCategoryTableService {
    @Override
    public Page<ProductCategoryVo> getList(TableParameters body, Principal principal) {
        return null;
    }

    @Override
    public void delete(Long id, Principal principal) {

    }

    @Override
    public ProductCategoryVo info(Long id, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result update(ProductCategoryVo productCategoryVo, MultipartFile[] files, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result save(ProductCategoryVo productCategoryVo, MultipartFile[] files, Principal principal) {
        return null;
    }
}
