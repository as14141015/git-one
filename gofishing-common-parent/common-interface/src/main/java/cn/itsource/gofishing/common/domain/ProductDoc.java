package cn.itsource.gofishing.common.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 用来连接ES存储商品数据
 */
@Document(indexName = "gofishing")
@Data
public class ProductDoc {
    @Id
    private Long id;

    /**
     * all包含：商品名称 副名称 商品类型名称 品牌名称
     * 以空格隔离
     */
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String all;

    /**
     * 商品类型ID
     */
    private Long productTypeId;
    /**
     * 商品ID
     */
    private Long brandId;
    /**
     * 上架时间
     */
    private Long onSaleTime;

    /**
     * 下架时间
     */
    private Long offSaleTime;

    /**
     * 最高价
     */
    private Integer maxPrice;

    /**
     * 最低价
     */
    private Integer minPrice;
    /**
     * 销量
     */
    private Integer saleCount;

    /**
     * 评论数
     */
    private Integer commentCount;
    /**
     * 浏览数量
     */
    private Integer viewCount;
    /**
     * 商品名称
     */
    @Field(store = true,type = FieldType.Text)
    private String name;
    /**
     * 商品副标题
     */
    @Field(store = true,type = FieldType.Text)
    private String subName;
    /**
     * 显示属性摘要
     */
    @Field(store = true,type = FieldType.Text)
    private String viewProperties;

    /**
     * sku属性摘要
     */
    @Field(store = true,type = FieldType.Text)
    private String skuProperties;
    /**
     * 媒体属性摘要
     */
    @Field(store = true,type = FieldType.Text)
    private String medias;

}
