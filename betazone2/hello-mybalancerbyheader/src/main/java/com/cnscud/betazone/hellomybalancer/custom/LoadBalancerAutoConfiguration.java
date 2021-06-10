package com.cnscud.betazone.hellomybalancer.custom;

import com.cnscud.betazone.pub.zonebyheader.MyBetaMainByHeaderLoadBalancerConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Configuration;

/**
 * 声明配置类.
 */
@Configuration(proxyBeanMethods = false)
@LoadBalancerClients(defaultConfiguration = MyBetaMainByHeaderLoadBalancerConfiguration.class)
public class LoadBalancerAutoConfiguration {
}
