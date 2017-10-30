package cn.hopever.platform.crm.service.impl;

import cn.hopever.platform.crm.domain.ProductCategoryTable;
import cn.hopever.platform.crm.domain.ProductTable;
import cn.hopever.platform.crm.repository.ProductCategoryTableRepository;
import cn.hopever.platform.crm.service.ProductCategoryTableService;
import cn.hopever.platform.crm.vo.ProductCategoryVo;
import cn.hopever.platform.crm.vo.ProductCategoryVoAssembler;
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
public class ProductCategoryTableServiceImpl implements ProductCategoryTableService {
    @Autowired
    private ProductCategoryTableRepository productCategoryTableRepository;

    @Autowired
    private ProductCategoryVoAssembler productCategoryVoAssembler;

    @Override
    public Page<ProductCategoryVo> getList(TableParameters body, Principal principal) {
        PageRequest pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.ASC, "id");
        Page<ProductCategoryTable> page = productCategoryTableRepository.findAll(pageRequest);
        List<ProductCategoryVo> list = new ArrayList<>();
        for (ProductCategoryTable productCategoryTable : page) {
            list.add(productCategoryVoAssembler.toResource(productCategoryTable));
        }
        return new PageImpl<>(list, pageRequest, page.getTotalElements());
    }

    @Override
    public void delete(Long id, Principal principal) {
        ProductCategoryTable productCategoryTable = productCategoryTableRepository.findOne(id);
        for (ProductTable productTable : productCategoryTable.getProductTables()) {
            productTable.setProductCategoryTable(null);
        }
        productCategoryTableRepository.delete(productCategoryTable);
    }

    @Override
    public ProductCategoryVo info(Long id, Principal principal) {
        return productCategoryVoAssembler.toResource(productCategoryTableRepository.findOne(id));
    }

    @Override
    public VueResults.Result update(ProductCategoryVo productCategoryVo, MultipartFile[] files, Principal principal) {
        productCategoryTableRepository.save(productCategoryVoAssembler.toDomain(productCategoryVo, productCategoryTableRepository.findOne(productCategoryVo.getId())));
        return null;
    }

    @Override
    public VueResults.Result save(ProductCategoryVo productCategoryVo, MultipartFile[] files, Principal principal) {
        productCategoryTableRepository.save(productCategoryVoAssembler.toDomain(productCategoryVo, new ProductCategoryTable()));
        return null;
    }

    @Override
    public List<SelectOption> getProductCategoryOptions(Principal principal) {
        Iterable<ProductCategoryTable> productCategoryTables = productCategoryTableRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        List<SelectOption> list = new ArrayList<>();
        for (ProductCategoryTable productCategoryTable : productCategoryTables) {
            list.add(new SelectOption(productCategoryTable.getName(), productCategoryTable.getId()));
        }
        return list;
    }
}
