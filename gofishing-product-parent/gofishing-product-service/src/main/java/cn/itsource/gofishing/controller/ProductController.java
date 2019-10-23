package cn.itsource.gofishing.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import cn.itsource.basic.util.StrUtils;
import cn.itsource.gofishing.common.domain.ProductDoc;
import cn.itsource.gofishing.service.IProductService;
import cn.itsource.gofishing.service.ISkuService;
import cn.itsource.product.domain.Product;
import cn.itsource.product.domain.Sku;
import cn.itsource.product.domain.Specification;
import cn.itsource.product.query.ProductQuery;
import cn.itsource.gofishing.common.domain.ProductParamVo;
import cn.itsource.product.vo.SkuPropertiesAndSkuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    public IProductService productService;
    @Autowired
    public ISkuService skuService;
    /**
    * 保存和修改公用的
    * @param product  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Product product){
        try {
            if(product.getId()!=null){
                productService.updateById(product);
            }else{
                productService.save(product);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！"+e.getMessage());
        }
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Integer id){
        try {
            productService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value="/deleteBatch",method=RequestMethod.DELETE)
    public AjaxResult deleteBatch(@RequestParam("ids") String ids){
        try{
            List<Long> longs = StrUtils.splitStr2LongArr(ids);
            productService.removeByIds(longs);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除对象失败！"+e.getMessage());
        }
    }
    //获取
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Product get(@PathVariable("id") Long id)
    {
        return productService.getById(id);
    }

    /**
    * 查看所有
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Product> list(){

        return productService.list(null);
    }

    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Product> json(@RequestBody ProductQuery query){
        return productService.queryPage(query);
    }


    /**
     * 根据商品ID查询商品的显示属性
     * @param productId
     * @return
     */
    @GetMapping("/viewProperties/{productId}")
    public List<Specification> getViewProperties(@PathVariable("productId") Long  productId){
        return productService.getViewProperties(productId);
    }

    /**
     * 保存修改商品的显示属性
     * @param productId
     * @param specifications
     * @return
     */
    @PostMapping("/updateViewProperties")
    public AjaxResult updateViewProperties(@RequestParam("productId")Long productId,
                                           @RequestBody List<Specification> specifications){
        try {
            productService.updateViewProperties(productId,specifications);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("保存失败！"+e.getMessage());
        }
    }


    /**
     * 根据商品ID查询商品的sku属性
     * @param productId
     * @return
     */
    @GetMapping("/skuProperties/{productId}")
    public Map<String,Object> getSkuProperties(@PathVariable("productId") Long  productId){
        List<Specification> skuProperties = productService.getSkuProperties(productId);
        List<Sku> skus = skuService.getSkusByProductId(productId);
        Map<String, Object> map = new HashMap<>();
        map.put("skuProperties",skuProperties);
        map.put("skus",skus);
        return map;
    }

    /**
     * 保存修改商品的Sku属性
     * @param productId
     * @param skuPropertiesAndSkuVo
     * @return
     */
    @PostMapping("/updateSkuProperties")
    public AjaxResult updateSkuProperties(@RequestParam("productId")Long productId,
                                          @RequestBody SkuPropertiesAndSkuVo skuPropertiesAndSkuVo){

        try {
            productService.updateSkuProperties(productId,skuPropertiesAndSkuVo.getSkuProperties(),skuPropertiesAndSkuVo.getSkus());
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("保存失败！"+e.getMessage());
        }
    }
    /**
     * 批量上架
     * @param ids
     * @return
     */
    @PostMapping("/onSale")
    public AjaxResult onSale(@RequestParam("ids")String ids){
        try {
            //把逗号分隔字符串转换List的Long StrUtils自定义工具类
            List<Long> idsList = StrUtils.splitStr2LongArr(ids);
            productService.onSale(idsList);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("上架失败！"+e.getMessage());
        }
    }

    /**
     * 批量下架
     * @param ids
     * @return
     */
    @PostMapping("/offSale")
    public AjaxResult offSale(@RequestParam("ids")String ids){

        try {
            List<Long> idsList = StrUtils.splitStr2LongArr(ids);
            productService.offSale(idsList);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("下架失败！"+e.getMessage());
        }
    }
    @PostMapping("/queryOnSale")
    public PageList<Product> queryOnSale(@RequestBody ProductParamVo productParamVo){
        return productService.queryOnSale(productParamVo);
    }
}
