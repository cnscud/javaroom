package com.cnscud.betazone.hellonameservicebyheader.config;

import com.cnscud.betazone.pub.zonebyheader.MyBetaMainByHeaderLoadBalancerConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Configuration;


/**
 * 配置声明类: .
 */
@Configuration(proxyBeanMethods = false)
@LoadBalancerClients(defaultConfiguration = MyBetaMainByHeaderLoadBalancerConfiguration.class)
public class LoadBalancerByHeaderAutoConfiguration {
}
