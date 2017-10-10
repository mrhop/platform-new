package cn.hopever.platform.crm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/30.
 * 目前不考虑产品多分类处理【用到tree】
 */
@Entity
@Table(name = "platform_crm_product_category")
@Data
@EqualsAndHashCode(of = {"id"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.crm.product_category")
public class ProductCategoryTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @OneToMany(mappedBy = "productCategoryTable")
    private List<ProductTable> productTables;
}
