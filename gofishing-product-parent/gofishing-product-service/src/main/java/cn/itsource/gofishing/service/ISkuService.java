package cn.itsource.gofishing.service;


import cn.itsource.product.domain.Sku;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

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
    Map<String, Object> skuChange(Long productId, String indexs);
}
