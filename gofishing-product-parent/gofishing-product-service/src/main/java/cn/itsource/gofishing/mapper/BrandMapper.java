package cn.itsource.gofishing.mapper;

import cn.itsource.product.domain.Brand;
import cn.itsource.product.query.BrandQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 品牌信息 Mapper 接口
 * </p>
 *
 * @author BKE
 * @since 2019-10-12
 */
public interface BrandMapper extends BaseMapper<Brand> {
    IPage<Brand> queryPage(Page page, @Param("query") BrandQuery query);

}
