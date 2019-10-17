package cn.itsource.product.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * SKU
 * </p>
 *
 * @author BKE
 * @since 2019-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_sku")
public class Sku implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("createTime")
    private Long createTime;

    @TableField("updateTime")
    private Long updateTime;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * SKU编码
     */
    @TableField("skuCode")
    private String skuCode;

    @TableField("skuName")
    private String skuName;

    /**
     * 市场价
     */
    @TableField("marketPrice")
    private Integer marketPrice;

    /**
     * 优惠价
     */
    private Integer price;

    /**
     * 成本价
     */
    @TableField("costPrice")
    private Integer costPrice;

    /**
     * 销量
     */
    @TableField("saleCount")
    private Integer saleCount;

    /**
     * 排序
     */
    @TableField("sortIndex")
    private Integer sortIndex;

    /**
     * 可用库存
     */
    @TableField("availableStock")
    private Integer availableStock;

    /**
     * 锁定库存
     */
    @TableField("frozenStock")
    private Integer frozenStock;

    /**
     * SKU属性摘要
     */
    @TableField("skuProperties")
    private String skuProperties;

    /**
     * 预览图
     */
    @TableField("skuMainPic")
    private String skuMainPic;

    private String indexs;


}
