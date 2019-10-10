package cn.itsource.gofishing.plat.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PlatServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(PlatServiceApp.class,args);
    }
}
