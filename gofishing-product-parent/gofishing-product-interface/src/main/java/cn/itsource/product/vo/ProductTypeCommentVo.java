package cn.itsource.product.vo;

import cn.itsource.product.domain.ProductType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 包含当前商品类型和同级别的商品类型
 */
@Data
public class ProductTypeCommentVo {
    /**
     * 当前商品类型
     */
    private ProductType currentType;
    /**
     * 同级别的商品类型
     */
    private List<ProductType> peerTypes = new ArrayList<>();
}
