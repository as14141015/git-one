package cn.itsource.gofishing.common.domain;

import lombok.Data;

/**
 * 商品查询条件的参数封装类
 */
@Data
public class ProductParamVo {
    private String keyword;
    private Long productTypeId;
    private Long brandId;
    private Integer minPrice;
    private Integer maxPrice;
    private String sortField;
    private String sortType;
    private Integer page;
    private Integer rows;
}
