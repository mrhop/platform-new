package cn.hopever.platform.crm.vo;

import cn.hopever.platform.crm.domain.ClientTable;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class ProductCategoryVoAssembler implements GenericVoAssembler<ProductCategoryVo, ClientTable> {


    @Override
    public ProductCategoryVo toResource(ClientTable clientTable) {
        return null;
    }

    @Override
    public ClientTable toDomain(ProductCategoryVo productCategoryVo, ClientTable clientTable) {
        return null;
    }
}
