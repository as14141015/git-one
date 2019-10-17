package cn.itsource.gofishing.service.impl;

import cn.itsource.gofishing.mapper.SpecificationMapper;
import cn.itsource.gofishing.service.ISpecificationService;
import cn.itsource.product.domain.Specification;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品属性 服务实现类
 * </p>
 *
 * @author BKE
 * @since 2019-10-17
 */
@Service
public class SpecificationServiceImpl extends ServiceImpl<SpecificationMapper, Specification> implements ISpecificationService {

}
