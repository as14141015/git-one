package cn.itsource.gofishing.common.service.controller;

import cn.itsource.basic.util.PageList;
import cn.itsource.gofishing.common.domain.ProductDoc;
import cn.itsource.gofishing.common.domain.ProductParamVo;
import cn.itsource.gofishing.common.service.repository.ProductDocRepository;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
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


    @PostMapping("/es/search")
    public PageList<ProductDoc> search(@RequestBody ProductParamVo vo){
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        //关键字查询，有可能为null
        if (StringUtils.isNotEmpty(vo.getKeyword())){
            boolQueryBuilder.must(new MatchQueryBuilder("all",vo.getKeyword()));
        }
        //商品类型id
        if (vo.getProductTypeId()!=null){
            boolQueryBuilder.must(new TermQueryBuilder("productTypeId",vo.getProductTypeId()));
        }
        //品牌id
        if (vo.getBrandId()!=null){
            boolQueryBuilder.must(new TermQueryBuilder("brandId",vo.getBrandId()));
        }

        /*
         * 最高价格和最低价格
         * 这里我们使用的是一个反向思维，将前端输入的最大值<minPrice,输入的最小值>maxPrice
         * 就可以控制在前端可选价格范围进行查询
         * 这里我们前端设置的最小值
         */
        if (vo.getMinPrice()!=null){
            boolQueryBuilder.must(new RangeQueryBuilder("maxPrice").gte(vo.getMinPrice()));
        }
        if (vo.getMinPrice()!=null){
            boolQueryBuilder.must(new RangeQueryBuilder("minPrice").lte(vo.getMaxPrice()));
        }

        builder.withQuery(boolQueryBuilder);
        //排序列
        String sortColumn = "saleCount";
        if(StringUtils.isNotEmpty(vo.getSortField())){
            sortColumn = vo.getSortField();
        }
        //排序方式
        SortOrder sortOrder = SortOrder.DESC;
        if("asc".equals(vo.getSortType())){
            sortOrder = SortOrder.ASC;
        }
        builder.withSort(new FieldSortBuilder(sortColumn).order(sortOrder));
        //分页 前端是从1开始，所以我们要-1
        builder.withPageable(PageRequest.of(vo.getPage()-1,vo.getRows()));
        Page<ProductDoc> page = productDocRepository.search(builder.build());
        return new PageList<>(page.getTotalElements(),page.getContent());
    }
}
