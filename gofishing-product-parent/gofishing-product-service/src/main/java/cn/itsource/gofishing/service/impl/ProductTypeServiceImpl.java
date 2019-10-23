package cn.itsource.gofishing.service.impl;

import cn.itsource.gofishing.common.client.RedisClient;
import cn.itsource.gofishing.common.client.StaticPageClient;
import cn.itsource.product.domain.ProductType;
import cn.itsource.gofishing.mapper.ProductTypeMapper;
import cn.itsource.gofishing.service.IProductTypeService;
import cn.itsource.product.vo.ProductTypeCommentVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品目录 服务实现类
 * </p>
 *
 * @author BKE
 * @since 2019-10-12
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {

    @Autowired
    private RedisClient redisClient;
    @Autowired
    private StaticPageClient staticPageClient;

    /**
     * 加载面包屑
     * @param productId
     */
    @Override
    public List<ProductTypeCommentVo> loadCommentTree(Long productId) {
        //当前类型
        ProductType type = baseMapper.selectById(productId);
        //获取path，通过path中的值获得父类
        String path = type.getPath();
        String[] split = path.substring(1).split("\\.");
        List<Long> ids = new ArrayList<>();
        for (String id : split) {
            ids.add(Long.parseLong(id));
        }
        //查询各个级别
        List<ProductType> parents = baseMapper.selectBatchIds(ids);
        //将数据封装到Vo中
        List<ProductTypeCommentVo> productTypeCommentVos= new ArrayList<>();
        ProductTypeCommentVo vo = null;
        for (ProductType parent : parents) {
            vo = new ProductTypeCommentVo();
            vo.setCurrentType(parent);
            //查询同级别类型
            List<ProductType> productTypes =
                    baseMapper.selectList(new QueryWrapper<ProductType>().eq("pid", parent.getPid()));
            vo.setPeerTypes(productTypes);
            productTypeCommentVos.add(vo);
        }
        return productTypeCommentVos;
    }

    /**
     * 加载类型树
     * @return
     */
    @Override
    public List<ProductType>  loadProductTypeTree(){
        List<ProductType> productTypes = null;
        //先查询Redis中的数据
        String productTypesStr = redisClient.get("productTypes");
        //判断redis中是否有数据，没有就去查，有直接返回
        if(productTypesStr == null){
            //防止数据击穿
            synchronized (this){
                if (productTypesStr == null){
                    productTypes = loadTypeTree();//查询数据库
                    productTypesStr = JSON.toJSONString(productTypes);//json转String
                    redisClient.set("productTypes",productTypesStr);
                }else {
                    productTypes = JSONArray.parseArray(productTypesStr,ProductType.class);// json转java对象
                }
                return productTypes;
            }
        }
        //返回给前端 -- json字符串转java对象
        productTypes = JSONArray.parseArray(productTypesStr,ProductType.class);
        return productTypes;
    }

    public List<ProductType> loadTypeTree() {
        //查询所有
        List<ProductType> allProductTypes = baseMapper.selectList(null);
        //创建一个新集合来接收一级
        List<ProductType> firstLevelTypes = new ArrayList<>();
        Map<Long,ProductType> productTypeMap = new HashMap<>();
        //将所有的productType存入map中
        for (ProductType productType : allProductTypes) {
            productTypeMap.put(productType.getId(),productType);
        }
        //再循环组装数据
        for (ProductType productType : allProductTypes) {
            //如果是一级
            if(productType.getPid()==0){
                firstLevelTypes.add(productType);
            }else{
                //不是一级就用他的子级找上级
                ProductType parent = productTypeMap.get(productType.getPid());
                parent.getChildren().add(productType);
            }
        }
        return firstLevelTypes;
    }

    @Override
    public void genHomePage() {
        //先创建product.type.vm.html 因为在创建home.html之前导入的vm中要使用到
        String templatePath = "D:\\eclipse\\eclipse-jee-neon-2-win32-x86_64\\gofishing-parent\\gofishing-product-parent\\gofishing-product-service\\src\\main\\resources\\template\\product.type.vm";
        String targetPath ="D:\\eclipse\\eclipse-jee-neon-2-win32-x86_64\\gofishing-parent\\gofishing-product-parent\\gofishing-product-service\\src\\main\\resources\\template\\product.type.vm.html";
        List<ProductType> productTypes = loadProductTypeTree();
        staticPageClient.getStaticPage(templatePath,targetPath,productTypes);

        //再创建home.html
        templatePath = "D:\\eclipse\\eclipse-jee-neon-2-win32-x86_64\\gofishing-parent\\gofishing-product-parent\\gofishing-product-service\\src\\main\\resources\\template\\home.vm";
        targetPath = "D:\\eclipse\\eclipse-jee-neon-2-win32-x86_64\\gofishing-web-parent\\ecommerce\\home.html";
        Map<String,Object> model = new HashMap<>();
        model.put("staticRoot","D:\\eclipse\\eclipse-jee-neon-2-win32-x86_64\\gofishing-parent\\gofishing-product-parent\\gofishing-product-service\\src\\main\\resources\\");
        staticPageClient.getStaticPage(templatePath,targetPath,model);
    }

    /**
     * 增删改的同步操作
     */
    private void synchronizedOption(){
        List<ProductType> productTypes = loadTypeTree();//获取所有的商品类型
        String productTypesStr = JSON.toJSONString(productTypes);
        redisClient.set("productTypes",productTypesStr);//将productTypesStr存储到内存中去
        genHomePage();
    }
    @Override
    public boolean save(ProductType entity) {
        boolean save = super.save(entity);
        synchronizedOption();
        return save;
    }

    @Override
    public boolean removeById(Serializable id) {
        boolean save = super.removeById(id);
        synchronizedOption();
        return save;
    }

    @Override
    public boolean updateById(ProductType entity) {
        boolean save = super.updateById(entity);
        synchronizedOption();
        return save;
    }
}
