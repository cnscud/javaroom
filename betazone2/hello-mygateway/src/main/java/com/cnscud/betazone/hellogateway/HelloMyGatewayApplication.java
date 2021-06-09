package com.cnscud.betazone.hellogateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class HelloMyGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloMyGatewayApplication.class, args);
    }

}
