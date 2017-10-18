package cn.hopever.platform.crm.repository;

import cn.hopever.platform.crm.domain.ProductPriceHistoryTable;
import cn.hopever.platform.crm.domain.ProductTable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface ProductPriceHistoryTableRepository extends PagingAndSortingRepository<ProductPriceHistoryTable, Long> {

    public List<ProductPriceHistoryTable> findByProductTableOrderByBeginDateAsc(ProductTable productTable);
    public ProductPriceHistoryTable findTopByProductTableOrderByBeginDateDesc(ProductTable productTable);
}
