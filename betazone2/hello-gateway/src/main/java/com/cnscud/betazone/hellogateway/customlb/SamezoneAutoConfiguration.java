package com.cnscud.betazone.hellogateway.customlb;

import com.cnscud.betazone.hellogateway.customlb.CustomLoadBalancerConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Configuration;

/**
 * 声明配置类.
 */
@Configuration(proxyBeanMethods = false)
@LoadBalancerClients(defaultConfiguration = CustomLoadBalancerConfiguration.class)
public class SamezoneAutoConfiguration {
}
