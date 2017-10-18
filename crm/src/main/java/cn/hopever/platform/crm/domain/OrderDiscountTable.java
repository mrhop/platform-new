package cn.hopever.platform.crm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Donghui Huo on 2017/8/30.
 * 自动选定打折后总价最低的，节日会有折上折
 */
@Entity
@Table(name = "platform_crm_order_discount")
@Data
@EqualsAndHashCode(of = {"id"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.crm.order_discount")
public class OrderDiscountTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    // 折扣类型 打折还是减免
    @Column(name = "type", length = 50, nullable = false)
    private String type;

    //满额指定
    @Column(name = "quota")
    private Float quota = 0.0f;
    //特殊日期折扣，起始日
    @Column(name = "begin_date")
    private Date beginDate;
    //特殊日期折扣，终止日
    @Column(name = "end_date")
    private Date endDate;

    //客户级别折扣
    @ManyToOne
    @JoinColumn(name = "client_level_id")
    private ClientLevelTable clientLevelTable;

    // 满额时打折
    @Column(name = "discount")
    private Float discount;

    // 满额的减免
    @Column(name = "reduce")
    private Float reduce;

    // 折扣建立日期,用于标明折扣的有效日期
    @Column(name = "created_date")
    private Date createdDate;

}
