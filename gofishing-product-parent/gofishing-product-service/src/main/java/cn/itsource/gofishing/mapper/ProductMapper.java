package cn.itsource.gofishing.mapper;

import cn.itsource.product.domain.Product;
import cn.itsource.product.query.ProductQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商品 Mapper 接口
 * </p>
 *
 * @author BKE
 * @since 2019-10-12
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
    IPage<Product> queryPage(Page page, @Param("query") ProductQuery query);
}
