package cn.hopever.platform.crm.vo;

import cn.hopever.platform.crm.domain.ProductPriceHistoryTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class ProductPriceHistoryVoAssembler implements GenericVoAssembler<ProductPriceHistoryVo, ProductPriceHistoryTable> {

    @Override
    public ProductPriceHistoryVo toResource(ProductPriceHistoryTable productPriceHistoryTable) {
        ProductPriceHistoryVo productPriceHistoryVo = new ProductPriceHistoryVo();
        BeanUtils.copyNotNullProperties(productPriceHistoryTable, productPriceHistoryVo);
        productPriceHistoryVo.setEndDate(productPriceHistoryTable.getEndDate().getTime());
        productPriceHistoryVo.setProductId(productPriceHistoryTable.getProductTable().getId());
        productPriceHistoryVo.setProductName(productPriceHistoryTable.getProductTable().getName());
        return productPriceHistoryVo;
    }

    @Override
    public ProductPriceHistoryTable toDomain(ProductPriceHistoryVo productPriceHistoryVo, ProductPriceHistoryTable productPriceHistoryTable) {
        BeanUtils.copyNotNullProperties(productPriceHistoryVo, productPriceHistoryTable, "id");
        return productPriceHistoryTable;
    }
}
