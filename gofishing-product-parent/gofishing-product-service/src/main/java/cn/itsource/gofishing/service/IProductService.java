package cn.itsource.gofishing.service;

import cn.itsource.basic.util.PageList;
import cn.itsource.product.domain.Product;
import cn.itsource.product.domain.Sku;
import cn.itsource.product.domain.Specification;
import cn.itsource.product.query.BrandQuery;
import cn.itsource.product.query.ProductQuery;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author BKE
 * @since 2019-10-12
 */
public interface IProductService extends IService<Product> {
    PageList<Product> queryPage(ProductQuery query);
    //显示属性
    List<Specification> getViewProperties(Long productId);
    //保存显示属性
    void updateViewProperties(Long productId, List<Specification> viewProperties);

    List<Specification> getSkuProperties(Long productId);

    void updateSkuProperties(Long productId, List<Specification> skuProperties, List<Map<String, String>> skus);

    /**
     * 批量下架
     * @param idsList
     */
    void offSale(List<Long> idsList);
    /**
     * 批量上架
     * @param idsList
     */
    void onSale(List<Long> idsList);
}
