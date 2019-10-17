package cn.itsource.gofishing.service.impl;

import cn.itsource.basic.util.PageList;
import cn.itsource.gofishing.mapper.ProductExtMapper;
import cn.itsource.gofishing.mapper.ProductMapper;
import cn.itsource.gofishing.service.IProductService;
import cn.itsource.product.domain.Product;
import cn.itsource.product.domain.ProductExt;
import cn.itsource.product.query.BrandQuery;
import cn.itsource.product.query.ProductQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductExtMapper productExtMapper;

    @Override
    public boolean save(Product product) {
        //创建时间
        product.setCreateTime(System.currentTimeMillis());
        productMapper.insert(product);
        ProductExt ext = product.getExt();
        //mybatis-plus自动返回生成的主键，保存到对象中
        ext.setProductId(product.getId());
        productExtMapper.insert(ext);
        return true;
    }

    @Override
    public boolean updateById(Product entity) {
        entity.setUpdateTime(System.currentTimeMillis());
        return super.updateById(entity);
    }

    /**
     * 分页
     * @param query
     * @return
     */
    @Override
    public PageList<Product> queryPage(ProductQuery query) {
        IPage<Product> productIPage =
                productMapper.queryPage(new Page(query.getPage(),
                        query.getRows()), query);
        PageList<Product> pageList = new PageList<>
                (productIPage.getTotal(),productIPage.getRecords());
        return pageList;
    }
}
