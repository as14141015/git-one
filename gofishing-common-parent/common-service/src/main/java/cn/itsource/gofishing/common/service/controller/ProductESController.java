package cn.itsource.gofishing.common.service.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.gofishing.common.domain.ProductDoc;
import cn.itsource.gofishing.common.service.repository.ProductDocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 将数据存储到ES中
 */
@RestController
public class ProductESController {
    @Autowired
    private ProductDocRepository productDocRepository;
    /**
     *批量保存
     */
    @PostMapping("/es/saveBatch")
    public void saveBatch(@RequestBody List<ProductDoc> productDocs){
        productDocRepository.saveAll(productDocs);
    }

    /**
     *批量删除
     */
    @PostMapping("/es/deleteBatch")
    public void deleteBatch(@RequestBody List<Long> idList){
        for (Long id : idList) {
            productDocRepository.deleteById(id);
        }
    }
}
