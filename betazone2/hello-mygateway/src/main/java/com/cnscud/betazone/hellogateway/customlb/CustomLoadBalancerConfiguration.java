package com.cnscud.betazone.hellogateway.customlb;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Configuration;

/**
 * 声明配置类.
 */
@Configuration(proxyBeanMethods = false)
//@LoadBalancerClients(defaultConfiguration = SameZoneOnlyCustomLoadBalancerConfiguration.class)
@LoadBalancerClients(defaultConfiguration = SameZoneSpecialBetaCustomLoadBalancerConfiguration.class)
public class CustomLoadBalancerConfiguration {
}
