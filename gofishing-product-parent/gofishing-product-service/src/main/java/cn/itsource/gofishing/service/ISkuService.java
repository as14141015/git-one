package cn.itsource.gofishing.service;


import cn.itsource.product.domain.Sku;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * SKU 服务类
 * </p>
 *
 * @author BKE
 * @since 2019-10-17
 */
public interface ISkuService extends IService<Sku> {

    List<Sku> getSkusByProductId(Long productId);
}
