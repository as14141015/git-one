package cn.itsource.gofishing.common.test;

import cn.itsource.gofishing.common.domain.ProductDoc;
import cn.itsource.gofishing.common.service.CommonServiceApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommonServiceApp.class)
public class ProductDocTest {
    @Autowired
    private ElasticsearchTemplate template;
    /**
     * 删库 建库 创建类型映射
     * @throws Exception
     */
    @Test
    public void test() throws Exception{
        template.deleteIndex(ProductDoc.class);
        template.createIndex(ProductDoc.class);
        template.putMapping(ProductDoc.class);
    }
}
