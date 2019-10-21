package cn.itsource.gofishing.common.client;

import cn.itsource.gofishing.common.domain.ProductDoc;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "GOFISHING-COMMON")
public interface ProductESClient {
    @PostMapping("/es/saveBatch")
    void saveBatch(@RequestBody List<ProductDoc> productDocs);
    @PostMapping("/es/deleteBatch")
    void deleteBatch(@RequestBody List<Long> idList);
}
