package cn.itsource.gofishing.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import cn.itsource.basic.util.StrUtils;
import cn.itsource.gofishing.service.IProductService;
import cn.itsource.product.domain.Product;
import cn.itsource.product.domain.Specification;
import cn.itsource.product.query.ProductQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    public IProductService productService;

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
    @PostMapping("/updateViewProperties")
    public AjaxResult updateViewProperties(
            @RequestParam("productId")Long productId,
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
    public List<Specification> getSkuProperties(@PathVariable("productId") Long  productId){
        return productService.getSkuProperties(productId);
    }
}
