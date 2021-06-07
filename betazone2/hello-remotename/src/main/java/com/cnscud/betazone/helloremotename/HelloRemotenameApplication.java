package com.cnscud.betazone.helloremotename;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class HelloRemotenameApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloRemotenameApplication.class, args);
    }

}
