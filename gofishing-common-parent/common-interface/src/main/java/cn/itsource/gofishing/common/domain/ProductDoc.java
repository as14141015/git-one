package cn.itsource.gofishing.common.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 用来连接ES存储商品数据
 */
@Document(indexName = "gofishing")
public class ProductDoc {
    @Id
    private Long id;

    /**
     * 商品名称
     */
    private String name;
    /**
     * 副名称
     */
    private String subName;

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
     * 显示属性摘要
     */
    private String viewProperties;

    /**
     * sku属性摘要
     */
    private String skuProperties;
    /**
     * 媒体属性摘要
     */
    private String medias;
    /**
     * 显示属性摘要
     */
//    private String skuProperties;
}
