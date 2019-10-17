package cn.itsource.gofishing.controller;

import cn.itsource.basic.util.PageList;
import cn.itsource.gofishing.service.IProductCommentService;
import cn.itsource.product.domain.ProductComment;
import cn.itsource.product.query.ProductCommentQuery;
import cn.itsource.basic.util.AjaxResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/productComment")
public class ProductCommentController {
    @Autowired
    public IProductCommentService productCommentService;

    /**
    * 保存和修改公用的
    * @param productComment  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/add",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody ProductComment productComment){
        try {
            if(productComment.getId()!=null){
                productCommentService.updateById(productComment);
            }else{
                productCommentService.save(productComment);
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
            productCommentService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ProductComment get(@PathVariable("id") Long id)
    {
        return productCommentService.getById(id);
    }


    /**
    * 查看所有
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<ProductComment> list(){

        return productCommentService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<ProductComment> json(@RequestBody ProductCommentQuery query)
    {
        Page<ProductComment> page = new Page<ProductComment>(query.getPage(),query.getRows());
        IPage<ProductComment> ipage = productCommentService.page(page);
        return new PageList<ProductComment>(ipage.getTotal(),ipage.getRecords());
    }
}
