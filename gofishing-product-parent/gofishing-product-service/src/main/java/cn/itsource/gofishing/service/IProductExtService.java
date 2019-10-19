package cn.itsource.gofishing.service;


import cn.itsource.product.domain.ProductExt;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;

/**
 * <p>
 * 商品扩展 服务类
 * </p>
 *
 * @author BKE
 * @since 2019-10-17
 */
public interface IProductExtService extends IService<ProductExt> {
    ProductExt findByProductId (Long id);
}
