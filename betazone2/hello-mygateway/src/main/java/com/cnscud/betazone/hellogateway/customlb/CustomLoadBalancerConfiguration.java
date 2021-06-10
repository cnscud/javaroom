package com.cnscud.betazone.hellogateway.customlb;

import com.cnscud.betazone.pub.samezone.SameZoneSpecialBetaCustomLoadBalancerConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Configuration;

/**
 * 声明配置类, 请根据实际情况切换标注.
 */
@Configuration(proxyBeanMethods = false)
//@LoadBalancerClients(defaultConfiguration = SameZoneOnlyCustomLoadBalancerConfiguration.class)
@LoadBalancerClients(defaultConfiguration = SameZoneSpecialBetaCustomLoadBalancerConfiguration.class)
public class CustomLoadBalancerConfiguration {
}
