package cn.itsource.gofishing.service.impl;

import cn.itsource.basic.util.LetterUtil;
import cn.itsource.basic.util.PageList;
import cn.itsource.gofishing.mapper.BrandMapper;
import cn.itsource.gofishing.service.IBrandService;
import cn.itsource.product.domain.Brand;
import cn.itsource.product.query.BrandQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 品牌信息 服务实现类
 * </p>
 *
 * @author BKE
 * @since 2019-10-12
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {
    @Override
    public PageList<Brand> queryPage(BrandQuery query) {
        IPage<Brand> brandIPage =
                baseMapper.queryPage(new Page(query.getPage(),
                        query.getRows()), query);
        PageList<Brand> pageList = new PageList<>
                (brandIPage.getTotal(),brandIPage.getRecords());
        return pageList;
    }

    @Override
    public boolean save(Brand brand) {
        brand.setCreateTime(System.currentTimeMillis());
        brand.setFirstLetter(LetterUtil.getFirstLetter(brand.getName()).toLowerCase());
        return super.save(brand);
    }

    @Override
    public boolean updateById(Brand brand) {
        brand.setUpdateTime(System.currentTimeMillis());
        brand.setFirstLetter(LetterUtil.getFirstLetter(brand.getName()).toLowerCase());
        return super.updateById(brand);
    }
}
