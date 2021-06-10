package com.cnscud.betazone.pub.samezone;

import com.cnscud.betazone.pub.LogUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.config.LoadBalancerZoneConfig;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Beta只返回统一区域的实例, 其他区域如果为空则返回所有实例.
 * @see org.springframework.cloud.loadbalancer.core.ZonePreferenceServiceInstanceListSupplier
 * @see org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplierBuilder
 *
 */
public class SameZoneSpecialBetaServiceInstanceListSupplier implements ServiceInstanceListSupplier {

    private final String ZONE = "zone";
    private String ZONE_BETA = "beta";

    private final ServiceInstanceListSupplier delegate;

    private final LoadBalancerZoneConfig zoneConfig;

    private String zone;

    public SameZoneSpecialBetaServiceInstanceListSupplier(ServiceInstanceListSupplier delegate,
                                                          LoadBalancerZoneConfig zoneConfig) {
        this.delegate = delegate;
        this.zoneConfig = zoneConfig;
    }

    @Override
    public String getServiceId() {
        return delegate.getServiceId();
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        return delegate.get().map(this::filteredByZone);
    }

    private List<ServiceInstance> filteredByZone(List<ServiceInstance> serviceInstances) {
        if (zone == null) {
            zone = zoneConfig.getZone();
        }

        if (zone != null) {
            List<ServiceInstance> filteredInstances = new ArrayList<>();
            for (ServiceInstance serviceInstance : serviceInstances) {
                String instanceZone = getZone(serviceInstance);
                if (zone.equalsIgnoreCase(instanceZone)) {
                    filteredInstances.add(serviceInstance);
                }
            }
            //如果没找到就返回空列表，绝不返回其他集群的实例
            LogUtils.warn("find instances size: " + filteredInstances.size());
            if(filteredInstances.size()>0) {
                return filteredInstances;
            }
            else {
                //如果是beta, 则返回空
                if (zone.equalsIgnoreCase(ZONE_BETA)){
                    return filteredInstances;
                }
            }
        }

        //如果没有zone设置, 则返回所有实例
        return serviceInstances;
    }

    private String getZone(ServiceInstance serviceInstance) {
        Map<String, String> metadata = serviceInstance.getMetadata();
        if (metadata != null) {
            return metadata.get(ZONE);
        }
        return null;
    }

}
