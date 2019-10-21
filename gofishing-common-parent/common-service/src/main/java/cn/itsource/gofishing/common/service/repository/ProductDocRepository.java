package cn.itsource.gofishing.common.service.repository;

import cn.itsource.gofishing.common.domain.ProductDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductDocRepository extends ElasticsearchRepository<ProductDoc,Long> {
}
