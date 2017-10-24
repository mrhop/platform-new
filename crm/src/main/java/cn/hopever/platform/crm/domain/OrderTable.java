package cn.hopever.platform.crm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/30.
 */
@Entity
@Table(name = "platform_crm_order")
@Data
@EqualsAndHashCode(of = {"id"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.crm.order")
public class OrderTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 自动生成的订单码
    @Column(name = "code", nullable = false)
    private String code;

    // 已创建，报价中，预签合同，合同签订，备货中，已打款，已发货，已收货，已完成并归档
    // 不同的用户看到不同的状态和处理不同的状态，
    @ManyToOne
    @JoinColumn(name = "order_status_id")
    private OrderStatusTable orderStatusTable;

    // 自动折扣和手动折扣
    @Column(name = "discount_type", nullable = false)
    private String discountType;

    // 手动折扣时，提供的折扣值
    @Column(name = "discount")
    private Float discount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private ClientTable clientTable;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "orderTable")
    private List<OrderProductTable> orderProductTables;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryTable countryTable;

    @ManyToOne
    @JoinColumn(name = "delivery_method_id")
    private DeliveryMethodTable deliveryMethodTable;

    // 发货后的追踪号
    @Column(name = "tracing_number")
    private String tracingNumber;

    @ManyToOne
    @JoinColumn(name = "pay_type_id")
    private PayTypeTable payTypeTable;

    // 运费
    @Column(name = "freight")
    private Float freight;

    // 实际销售总价
    @Column(name = "sale_price")
    private Float salePrice;

    // 自动计算预估报价
    @Column(name = "pre_quotation")
    private Float preQuotation;

    // 自动计算打折前价格
    @Column(name = "pre_sale_price")
    private Float preSalePrice;

    // 自动计算成本价
    @Column(name = "cost_price")
    private Float costPrice;

    // 创建日期 也可以叫做预报下单日期
    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "created_user_id")
    private RelatedUserTable createdUser;

    // 合同签订日期 这个日期来算用户的时间效果
    @Column(name = "contract_sign_date")
    private Date contractSignDate;

    // 发货日期
    @Column(name = "delivery_date")
    private Date deliveryDate;

    // 订单完成归档日期
    @Column(name = "finished_date")
    private Date finishedDate;

    @Column(name = "additional_msg", length = 2000)
    private String additionalMsg;
}
