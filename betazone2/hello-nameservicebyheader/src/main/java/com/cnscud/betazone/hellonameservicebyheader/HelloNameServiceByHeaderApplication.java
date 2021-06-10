package com.cnscud.betazone.hellonameservicebyheader;

import com.cnscud.betazone.hellonameservicebyheader.config.FeignByZoneHeaderConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
//设置缺省报名, 缺省Feign的配置
@EnableFeignClients(basePackages = "com.cnscud.betazone.hellonameservicebyheader", defaultConfiguration = FeignByZoneHeaderConfig.class)
@SpringBootApplication
public class HelloNameServiceByHeaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloNameServiceByHeaderApplication.class, args);
    }

}
