package com.cnscud.betazone.hellonameservice.config;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Configuration;


/**
 * 配置声明类.
 */
@Configuration(proxyBeanMethods = false)
@LoadBalancerClients(defaultConfiguration = CustomLoadBalancerConfiguration.class)
public class SamezoneAutoConfiguration {
}
