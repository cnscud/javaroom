package com.cnscud.betazone.hellonameservicebyheader.config;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Configuration;


/**
 * 配置声明类, 根据不同的情况配置不同的defaultConfiguration.
 */
@Configuration(proxyBeanMethods = false)
@LoadBalancerClients(defaultConfiguration = CustomLoadBalancerConfiguration.class)
public class SamezoneAutoConfiguration {
}
