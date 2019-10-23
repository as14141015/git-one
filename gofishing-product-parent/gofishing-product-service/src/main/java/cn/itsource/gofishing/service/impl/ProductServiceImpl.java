package cn.itsource.gofishing.service.impl;

import cn.itsource.basic.util.PageList;
import cn.itsource.gofishing.common.client.ProductESClient;
import cn.itsource.gofishing.common.domain.ProductDoc;
import cn.itsource.gofishing.mapper.*;
import cn.itsource.gofishing.service.IProductService;
import cn.itsource.product.domain.*;
import cn.itsource.product.query.ProductQuery;
import cn.itsource.gofishing.common.domain.ProductParamVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private ProductExtMapper productExtMapper;
    @Autowired
    private SpecificationMapper specificationMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private ProductESClient productESClient;
    @Autowired
    private ProductTypeMapper productTypeMapper;
    @Autowired
    private BrandMapper brandMapper;

    /**
     * 商品搜索
     * @param productParamVo
     * @return
     */
    @Override
    public PageList<Product> queryOnSale(ProductParamVo productParamVo) {
        //ES查询出来的参数
        PageList<ProductDoc> pageList = productESClient.search(productParamVo);

        List<Product> products = new ArrayList<>();
        Product product = null;
        for (ProductDoc productDoc : pageList.getRows()){
            product = new Product();
            product.setId(productDoc.getId());
            product.setName(productDoc.getName());
            product.setMedias(productDoc.getMedias());
            product.setSaleCount(productDoc.getSaleCount());
            product.setSubName(productDoc.getSubName());
            product.setMaxPrice(productDoc.getMaxPrice());
            product.setMinPrice(productDoc.getMinPrice());
            products.add(product);
        }

        return new PageList<Product>(pageList.getTotal(),products);
    }

    /**
     * 批量下架
     * @param idsList
     */
    @Override
    public void offSale(List<Long> idsList) {
        baseMapper.offSale(idsList,System.currentTimeMillis());
        productESClient.deleteBatch(idsList);
    }
    /**
     * 批量上架
     * @param idsList
     */
    @Override
    public void onSale(List<Long> idsList) {
        //修改数据库中的state
        baseMapper.onSale(idsList,System.currentTimeMillis());
        //查询数据库中的数据
        List<Product> products = baseMapper.selectBatchIds(idsList);
        //遍历并赋值给ProductDoc
        List<ProductDoc> productDocs = productsToProductDocs(products);
        productESClient.saveBatch(productDocs);
    }

    /**
     * 将Product集合转为ProductDoc集合
     * @param products
     * @return
     */
    private List<ProductDoc> productsToProductDocs(List<Product> products) {
        List<ProductDoc> productDocs = new ArrayList<>();
        ProductDoc productDoc = null;
        for (Product product : products) {
            productDoc = productToProductDoc(product);
            productDocs.add(productDoc);
        }

        return productDocs;
    }
    /**
     * 将Product对象转为ProductDoc对象
     * @param product
     * @return
     */
    private ProductDoc productToProductDoc(Product product) {
        System.out.println(product);
        ProductDoc productDoc = new ProductDoc();
        //通过product.getBrandId()查询对应brand
        Brand brand = brandMapper.selectById(product.getBrandId());
        //通过product.getProductTypeId()查询对应商品类型
        ProductType productType = productTypeMapper.selectById(product.getProductTypeId());

        productDoc.setId(product.getId());
        //使用一个StringBuilder拼接字符串
        StringBuilder all = new StringBuilder();
        //all包含：商品名称 副名称 商品类型名称 品牌名称
        all.append(product.getName()).append(" ")
                .append(product.getSubName()).append(" ")
                .append(productType.getName()).append(" ")
                .append(brand.getName());
        productDoc.setAll(all.toString());
        productDoc.setProductTypeId(product.getProductTypeId());
        productDoc.setBrandId(product.getBrandId());
        productDoc.setOnSaleTime(product.getOnSaleTime());
        productDoc.setOffSaleTime(product.getOffSaleTime());
        //根据productId查询相应的sku属性数据
        List<Sku> skus = skuMapper.selectList(new QueryWrapper<Sku>().eq("product_id", product.getId()));
        //创建最大价格和最小价格
        Integer maxPrice = 0;
        Integer minPrice = 0;
        if (skus!=null&&skus.size()>0){
            minPrice = skus.get(0).getPrice();
        }
        for (Sku sku : skus) {
            if (maxPrice<sku.getPrice()){
                maxPrice = sku.getPrice();
            }
            if (minPrice>sku.getPrice()){
                minPrice = sku.getPrice();
            }
        }
        productDoc.setMaxPrice(maxPrice);
        productDoc.setMinPrice(minPrice);
        productDoc.setSaleCount(product.getSaleCount());
        productDoc.setCommentCount(product.getCommentCount());
        productDoc.setViewCount(product.getViewCount());
        productDoc.setName(product.getName());
        productDoc.setSubName(product.getSubName());
        productDoc.setViewProperties(product.getViewProperties());
        productDoc.setSkuProperties(product.getSkuProperties());
        productDoc.setMedias(product.getMedias());
        System.out.println("productDoc========"+productDoc);
        return productDoc;
    }

    /**
     * 获取当前商品的显示属性
     * @param productId
     * @return
     */
    @Override
    public List<Specification> getViewProperties(Long productId) {
        List<Specification> specifications = null;
        //根据id查询出商品，并判断里面的viewProperties是否为空
        Product product = baseMapper.selectById(productId);
        //这里获取的数据是一个json格式的字符串
        String viewProperties = product.getViewProperties();
        //不为空就获取所有显示属性
        if (StringUtils.isEmpty(viewProperties)){
            Long productTypeId = product.getProductTypeId();
            specifications = specificationMapper.selectList(new QueryWrapper<Specification>()
                    .eq("product_type_id", productTypeId).eq("isSku", 0));
        }else {
            //将json格式的字符串转成List<Specification>
            specifications = JSONArray.parseArray(viewProperties,Specification.class);
        }
        return specifications;
    }
    /**
     * 保存修改商品的显示属性
     * @param productId
     * @param specifications
     * @return
     */
    @Override
    public void updateViewProperties(Long productId, List<Specification> specifications) {
        String viewProperties = JSON.toJSONString(specifications);
        baseMapper.updateViewProperties(productId,viewProperties);
    }
    /**
     * 获取当前商品的sku属性
     * @param productId
     * @return
     */
    @Override
    public List<Specification> getSkuProperties(Long productId) {
        List<Specification> specifications = null;
        Product product = baseMapper.selectById(productId);
        String skuProperties = product.getSkuProperties();
        //不为空就获取所有显示属性
        if (StringUtils.isEmpty(skuProperties)){
            Long productTypeId = product.getProductTypeId();
            specifications = specificationMapper.selectList(new QueryWrapper<Specification>()
                    .eq("product_type_id", productTypeId).eq("isSku", 1));
        }else {
            //将json格式的字符串转成List<Specification>
            specifications = JSONArray.parseArray(skuProperties,Specification.class);
        }
        return specifications;
    }
    /**
     * 保存修改商品的Sku属性
     * @param productId
     * @return
     */
    @Override
    @Transactional //保持事务的一致性
    public void updateSkuProperties(Long productId, List<Specification> skuProperties, List<Map<String, String>> skus) {
        //将skuProperties存如t_product表中
        String skuPropertiesTOJson = JSON.toJSONString(skuProperties);
        System.out.println(skuPropertiesTOJson);
        baseMapper.updateSkuProperties(productId,skuPropertiesTOJson);
        //将数据存到t_sku表中
        //先将之前的数据删除，因为修改更加麻烦，删掉重新添加要方便许多
        skuMapper.delete(new QueryWrapper<Sku>().eq("product_id", productId));
        Sku sku = null;
        for (Map<String, String> mapSku : skus) {
            sku = new Sku();
            //设置一般参数
            sku.setCreateTime(System.currentTimeMillis());
            sku.setProductId(productId);
            //给skuName赋值
            StringBuilder skuNames = new StringBuilder();
            for (Map.Entry<String, String> entry : mapSku.entrySet()) {
                if(!"price".equals(entry.getKey()) && !"store".equals(entry.getKey()) && !"indexs".equals(entry.getKey())){
                    /*
                        前端传来的值是//{"name":"xx","age":"xx","price":0,"store":0,"indexs":"xxx_0_1"}
                        所以我们要排除掉后三个key才能拿到其他的条件
                     */
                    skuNames.append(entry.getValue());
                }
            }
            sku.setSkuName(skuNames.toString());
            sku.setPrice(Integer.parseInt(mapSku.get("price")));
            sku.setAvailableStock(Integer.parseInt(mapSku.get("store")));
            sku.setIndexs(mapSku.get("indexs"));
            skuMapper.insert(sku);
        }

    }

    @Override
    public boolean save(Product product) {
        //创建时间
        product.setCreateTime(System.currentTimeMillis());
        baseMapper.insert(product);
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
                baseMapper.queryPage(new Page(query.getPage(),
                        query.getRows()), query);
        PageList<Product> pageList = new PageList<>
                (productIPage.getTotal(),productIPage.getRecords());
        return pageList;
    }
}
