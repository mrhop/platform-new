package cn.hopever.platform.crm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * Created by Donghui Huo on 2017/10/9.
 */
@Entity
@Table(name = "platform_crm_order_product")
@Data
@EqualsAndHashCode(of = {"id"})
public class OrderProductTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private OrderTable orderTable;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private ProductTable productTable;

    // 产品量
    @Column(name = "num")
    private Float num;
}
