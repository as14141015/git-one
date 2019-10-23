package cn.itsource.gofishing.service.impl;

import cn.itsource.basic.util.LetterUtil;
import cn.itsource.basic.util.PageList;
import cn.itsource.gofishing.mapper.BrandMapper;
import cn.itsource.gofishing.service.IBrandService;
import cn.itsource.product.domain.Brand;
import cn.itsource.product.query.BrandQuery;
import cn.itsource.product.vo.BrandVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TreeSet;

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
    /**
     * 通过productTypeId获取到当前品牌和首字母
     * @param productTypeId
     * @return
     */
    @Override
    public BrandVo getBrandVo(Long productTypeId) {
        BrandVo vo = new BrandVo();
        List<Brand> brands = baseMapper.selectList(new QueryWrapper<Brand>().eq("product_type_id", productTypeId));
        vo.setBrands(brands);
        TreeSet<String> treeSet = new TreeSet<>();
        for (Brand brand : brands) {
            treeSet.add(brand.getFirstLetter());
        }
        vo.setFirstLetter(treeSet);
        return vo;
    }

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
