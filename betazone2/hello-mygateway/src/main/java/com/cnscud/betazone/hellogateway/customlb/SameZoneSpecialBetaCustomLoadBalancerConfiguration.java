package com.cnscud.betazone.hellogateway.customlb;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.loadbalancer.cache.LoadBalancerCacheManager;
import org.springframework.cloud.loadbalancer.config.LoadBalancerZoneConfig;
import org.springframework.cloud.loadbalancer.core.CachingServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.DiscoveryClientServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;


/**
 * 自定义 Instance List Supplier: 根据默认Zone划分, 并且beta zone绝对隔离.
 */
public class SameZoneSpecialBetaCustomLoadBalancerConfiguration {

    @Bean
    public ServiceInstanceListSupplier discoveryClientServiceInstanceListSupplier(
            DiscoveryClient discoveryClient, Environment env,
            LoadBalancerZoneConfig zoneConfig,
            ApplicationContext context) {

        ServiceInstanceListSupplier delegate = new SameZoneSpecialBetaServiceInstanceListSupplier(
                new DiscoveryClientServiceInstanceListSupplier(discoveryClient, env),
                zoneConfig
        );
        ObjectProvider<LoadBalancerCacheManager> cacheManagerProvider = context
                .getBeanProvider(LoadBalancerCacheManager.class);
        if (cacheManagerProvider.getIfAvailable() != null) {
            return new CachingServiceInstanceListSupplier(
                    delegate,
                    cacheManagerProvider.getIfAvailable()
            );
        }
        return delegate;

    }



}
