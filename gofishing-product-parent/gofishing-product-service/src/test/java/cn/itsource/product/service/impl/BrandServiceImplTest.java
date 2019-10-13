package cn.itsource.product.service.impl;

import cn.itsource.product.ProductServiceApp;
import cn.itsource.product.domain.Brand;
import cn.itsource.product.mapper.BrandMapper;
import cn.itsource.product.query.BrandQuery;
import cn.itsource.product.service.IBrandService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductServiceApp.class)
public class BrandServiceImplTest {

    @Autowired
    private BrandMapper brandMapper;
    @Test
    public void queryPage() {
        BrandQuery brandQuery = new BrandQuery();
        brandQuery.setPage(1);
        brandQuery.setRows(10);
        IPage<Brand> brandIPage = brandMapper.queryPage(new Page(brandQuery.getPage(), brandQuery.getRows()), brandQuery);
        System.out.println(brandIPage.getTotal());
        System.out.println(brandIPage.getRecords());
        for (Brand record : brandIPage.getRecords()) {
            System.out.println(record);
            System.out.println(11111);
        }

    }
}