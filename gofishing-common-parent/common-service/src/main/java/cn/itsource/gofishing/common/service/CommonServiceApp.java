package cn.itsource.gofishing.common.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CommonServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(CommonServiceApp.class,args);
    }
}
