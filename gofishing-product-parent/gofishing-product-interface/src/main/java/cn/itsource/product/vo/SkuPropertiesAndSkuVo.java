package cn.itsource.product.vo;

import cn.itsource.product.domain.Product;
import cn.itsource.product.domain.Specification;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SkuPropertiesAndSkuVo {
    List<Specification>  skuProperties;
    List<Map<String,String>> skus;
}
