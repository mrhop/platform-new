package cn.hopever.platform.crm.domain;

import cn.hopever.platform.utils.json.JacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/30.
 * 注意商品有二维码的生成以及扫码识别方式
 */
@Entity
@Table(name = "platform_crm_product")
@Data
@EqualsAndHashCode(of = {"id"})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hopever.crm.product")
public class ProductTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    // 自动生成的商品码
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "cost_price", nullable = false)
    private Float costPrice;

    // 销售价
    @Column(name = "sale_price", nullable = false)
    private Float salePrice;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "specification")
    private String specification;

    @Column(name = "color")
    private String color;

    @Column(name = "pictures", length = 2000)
    private String pictures;

    @Column(name = "additional_msg", length = 2000)
    private String additionalMsg;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "productTable")
    private List<OrderProductTable> orderProductTable;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "productTable")
    private List<ProductPriceHistoryTable> productPriceHistoryTables;

    // 创建日期
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "created_user_id")
    private RelatedUserTable createdUser;

    @ManyToOne
    @JoinColumn(name = "product_category_id")
    private ProductCategoryTable productCategoryTable;


    public List<String> getPictures() {
        if (this.pictures != null) {
            try {
                return JacksonUtil.mapper.readValue(this.pictures, List.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void setPictures(List<String> screenshots) {
        if (screenshots != null && screenshots.size() > 0) {
            try {
                this.pictures = JacksonUtil.mapper.writeValueAsString(screenshots);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            this.pictures = null;
        }
    }
}

