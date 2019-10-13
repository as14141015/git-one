package cn.itsource.product.service.impl;

import cn.itsource.product.domain.ProductType;
import cn.itsource.product.mapper.ProductTypeMapper;
import cn.itsource.product.service.IProductTypeService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    public List<ProductType> loadProductTypeTree() {
        //先查询所有
        List<ProductType> allProductTypes = baseMapper.selectList(null);
        //1000
        //再组装数据
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
                //如果不是一级
                ProductType parent =
                        productTypeMap.get(productType.getPid());
                parent.getChildren().add(productType);
            }
        }
        return firstLevelTypes;
    }
}
