package cn.itsource.gofishing.service.impl;

import cn.itsource.gofishing.mapper.ProductMapper;
import cn.itsource.gofishing.service.IProductService;
import cn.itsource.product.domain.Product;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author BKE
 * @since 2019-10-12
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
