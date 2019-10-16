package cn.itsource.gofishing.common.client;

import cn.itsource.gofishing.common.client.impl.RedisClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="GOFISHING-COMMON",fallback = RedisClientImpl.class)
public interface RedisClient {
    @GetMapping("/redis")
    String get(@RequestParam("key") String key);
    @PostMapping("/redis")
    void set(@RequestParam("key") String key,@RequestParam("value") String value);
}
