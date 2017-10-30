package cn.hopever.platform.crm.vo;

import cn.hopever.platform.crm.domain.ProductTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class ProductVoAssembler implements GenericVoAssembler<ProductVo, ProductTable> {

    @Override
    public ProductVo toResource(ProductTable productTable) {
        ProductVo productVo = new ProductVo();
        BeanUtils.copyNotNullProperties(productTable, productVo);
        productVo.setPictures(productTable.getPictures());
        productVo.setCreatedUserId(productTable.getCreatedUser().getId());
        productVo.setCreatedUserName(productTable.getCreatedUser().getAccount());
        productVo.setCreatedDate(productTable.getCreatedDate().getTime());
        if (productTable.getProductCategoryTable() != null) {
            productVo.setProductCategoryId(productTable.getProductCategoryTable().getId());
            productVo.setProductCategoryName(productTable.getProductCategoryTable().getName());
        }
        return productVo;
    }

    @Override
    public ProductTable toDomain(ProductVo productVo, ProductTable productTable) {
        BeanUtils.copyNotNullProperties(productVo, productTable, "id", "code");
        return productTable;
    }
}
