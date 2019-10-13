package cn.itsource.product.service;

import cn.itsource.basic.util.PageList;
import cn.itsource.product.domain.Brand;
import cn.itsource.product.query.BrandQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 品牌信息 服务类
 * </p>
 *
 * @author BKE
 * @since 2019-10-12
 */
public interface IBrandService extends IService<Brand> {
    PageList<Brand> queryPage(BrandQuery query);
}
