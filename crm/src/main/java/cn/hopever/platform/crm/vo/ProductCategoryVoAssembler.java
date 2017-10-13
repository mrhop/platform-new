package cn.hopever.platform.crm.vo;

import cn.hopever.platform.crm.domain.ProductCategoryTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class ProductCategoryVoAssembler implements GenericVoAssembler<ProductCategoryVo, ProductCategoryTable> {


    @Override
    public ProductCategoryVo toResource(ProductCategoryTable productCategoryTable) {
        ProductCategoryVo productCategoryVo = new ProductCategoryVo();
        BeanUtils.copyNotNullProperties(productCategoryTable, productCategoryVo);
        return productCategoryVo;
    }

    @Override
    public ProductCategoryTable toDomain(ProductCategoryVo productCategoryVo, ProductCategoryTable productCategoryTable) {
        BeanUtils.copyNotNullProperties(productCategoryVo, productCategoryTable,"id");
        return productCategoryTable;
    }
}
