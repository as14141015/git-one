package cn.itsource.gofishing.service.impl;

import cn.itsource.gofishing.mapper.SkuMapper;
import cn.itsource.gofishing.service.ISkuService;
import cn.itsource.product.domain.Sku;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * SKU 服务实现类
 * </p>
 *
 * @author BKE
 * @since 2019-10-17
 */
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements ISkuService {
    @Override
    public List<Sku> getSkusByProductId(Long productId) {

        return baseMapper.getSkusByProductId(productId);
    }
    @Override
    public Map<String, Object> skuChange(Long productId, String indexs) {
        Sku sku = baseMapper.selectOne(new QueryWrapper<Sku>()
                .eq("product_id",productId)
                .eq("indexs", indexs));
        Map<String, Object> map = new HashMap<>();
        map.put("price",sku.getPrice());
        map.put("store",sku.getAvailableStock());
        return map;
    }
}

