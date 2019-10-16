package cn.itsource.gofishing.service;

import cn.itsource.product.domain.ProductType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品目录 服务类
 * </p>
 *
 * @author BKE
 * @since 2019-10-12
 */
public interface IProductTypeService extends IService<ProductType> {
    List<ProductType> loadProductTypeTree();

    void genHomePage();
}
