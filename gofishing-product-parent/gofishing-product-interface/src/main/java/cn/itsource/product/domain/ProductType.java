package cn.itsource.product.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品目录
 * </p>
 *
 * @author BKE
 * @since 2019-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_product_type")
public class ProductType implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("createTime")
    private Long createTime;

    @TableField("updateTime")
    private Long updateTime;

    /**
     * 类型名
     */
    private String name;

    /**
     * 父ID
     */
    private Long pid;

    /**
     * 图标
     */
    private String logo;

    /**
     * 描述
     */
    private String description;

    @TableField("sortIndex")
    private Integer sortIndex;

    /**
     * 路径
     */
    private String path;

    /**
     * 商品数量
     */
    @TableField("totalCount")
    private Integer totalCount;

    /**
     * 分类标题（SEO）
     */
    @TableField("seoTitle")
    private String seoTitle;

    /**
     * 分类关键字（SEO）
     */
    @TableField("seoKeywords")
    private String seoKeywords;

    private Long typeTemplateId;
    /**
     * 专门用来装父类下面的子类
     */
    @TableField(exist = false)
    private List<ProductType> children = new ArrayList<>();

}
