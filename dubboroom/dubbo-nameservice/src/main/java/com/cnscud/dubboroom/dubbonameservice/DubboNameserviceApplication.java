package com.cnscud.dubboroom.dubbonameservice;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableDubbo
public class DubboNameserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboNameserviceApplication.class, args);
    }



}
