package cn.hopever.platform.crm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Donghui Huo on 2017/8/30.
 */
@Entity
@Table(name = "platform_crm_product_price_history")
@Data
@EqualsAndHashCode(of = {"id"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.crm.product_price_history")
public class ProductPriceHistoryTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private ProductTable productTable;

    // 商品价格有效起始日期
    @Column(name = "begin_date")
    private Date beginDate;
    // 商品价格有效截止日期，之前，但是不包过 <，当没有截止日期时，为当前价格
    @Column(name = "end_date")
    private Date endDate;

    // 销售价
    @Column(name = "sale_price")
    private Float salePrice;

    // 成本价
    @Column(name = "cost_price")
    private Float costPrice;


}
