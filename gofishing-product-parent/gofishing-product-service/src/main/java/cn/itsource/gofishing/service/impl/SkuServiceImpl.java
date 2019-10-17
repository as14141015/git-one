package cn.itsource.gofishing.service.impl;

import cn.itsource.gofishing.mapper.SkuMapper;
import cn.itsource.gofishing.service.ISkuService;
import cn.itsource.product.domain.Sku;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
