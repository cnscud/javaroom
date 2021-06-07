package com.cnscud.betazone.hellonameservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.cnscud.betazone.hellonameservice")
@SpringBootApplication
public class HelloNameServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloNameServiceApplication.class, args);
    }

}
