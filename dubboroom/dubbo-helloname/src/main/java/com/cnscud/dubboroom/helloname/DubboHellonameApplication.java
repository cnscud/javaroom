package com.cnscud.dubboroom.helloname;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableDubbo
public class DubboHellonameApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboHellonameApplication.class, args);
    }

}
