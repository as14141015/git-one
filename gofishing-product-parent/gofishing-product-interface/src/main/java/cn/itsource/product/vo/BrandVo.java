package cn.itsource.product.vo;

import cn.itsource.product.domain.Brand;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 品牌保存
 */
@Data
public class BrandVo {
    /**
     * 所有品牌
     */
    List<Brand> brands = new ArrayList<>();
    /**
     * 所有品牌首字母 不可重复
     */
    Set<String> firstLetter = new TreeSet<>();
}
