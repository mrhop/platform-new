package cn.hopever.platform.crm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/30
 * 已创建created，报价中(quoting)，预签合同(pre-contract)，合同签订(contracted)，已收款(payed)，备货中(goods-preparing)，，已发货(shipped)，已收货(received)，已完成并归档(finished).
 */
@Entity
@Table(name = "platform_crm_order_status")
@Data
@EqualsAndHashCode(of = {"id"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.crm.order_status")
public class OrderStatusTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", length = 50, nullable = false)
    private String code;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @OneToMany(mappedBy = "orderStatusTable")
    private List<OrderTable> orderTables;
}
