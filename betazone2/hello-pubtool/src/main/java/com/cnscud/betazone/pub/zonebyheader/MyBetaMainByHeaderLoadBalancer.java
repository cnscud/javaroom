package com.cnscud.betazone.pub.zonebyheader;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.RequestDataContext;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 根据header里面的workzone选择合适的实例, 如果没有发现, 则返回所有实例.
 *
 * A Round-Robin-based implementation of {@link ReactorServiceInstanceLoadBalancer}.
 *
 * @author Spencer Gibb
 * @author Olga Maciaszek-Sharma
 */
public class MyBetaMainByHeaderLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    private static final Log log = LogFactory.getLog(MyBetaMainByHeaderLoadBalancer.class);
    final String defaultKey = "default";

    final AtomicInteger position;
    final Map<String, AtomicInteger> postionMap = new HashMap<>();

    final String serviceId;

    ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    /**
     * @param serviceInstanceListSupplierProvider a provider of
     * {@link ServiceInstanceListSupplier} that will be used to get available instances
     * @param serviceId id of the service for which to choose an instance
     */
    public MyBetaMainByHeaderLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
                                          String serviceId) {
        this(serviceInstanceListSupplierProvider, serviceId, new Random().nextInt(1000));
    }

    /**
     * @param serviceInstanceListSupplierProvider a provider of
     * {@link ServiceInstanceListSupplier} that will be used to get available instances
     * @param serviceId id of the service for which to choose an instance
     * @param seedPosition Round Robin element position marker
     */
    public MyBetaMainByHeaderLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
                                          String serviceId, int seedPosition) {
        this.serviceId = serviceId;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
        this.position = new AtomicInteger(seedPosition);
        postionMap.put(defaultKey, this.position);
    }

    @SuppressWarnings("rawtypes")
    @Override
    // see original
    // https://github.com/Netflix/ocelli/blob/master/ocelli-core/
    // src/main/java/netflix/ocelli/loadbalancer/RoundRobinLoadBalancer.java
    public Mono<Response<ServiceInstance>> choose(Request request) {
        //read headers
        HttpHeaders headers = ((RequestDataContext) request.getContext()).getClientRequest().getHeaders();

        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
                .getIfAvailable(NoopServiceInstanceListSupplier::new);
        return supplier.get(request).next()
                .map(serviceInstances -> processInstanceResponse(supplier, serviceInstances, headers));
    }

    private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier,
                                                              List<ServiceInstance> serviceInstances,
                                                              HttpHeaders headers ) {
        Response<ServiceInstance> serviceInstanceResponse = getInstanceResponse(serviceInstances, headers);
        if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
            ((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
        }
        return serviceInstanceResponse;
    }

    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances,
                                                          HttpHeaders headers) {
        if (instances.isEmpty()) {
            if (log.isWarnEnabled()) {
                log.warn("No servers available for service: " + serviceId);
            }
            return new EmptyResponse();
        }

        String workzone = headers.getFirst("workzone");
        log.info("getInstanceResponse: workzone-> " + workzone);
        String positionKey = defaultKey;

        Map<String,String> zoneMap = new HashMap<>();
        zoneMap.put("zone",workzone);
        final Set<Map.Entry<String,String>> attributes =
                Collections.unmodifiableSet(zoneMap.entrySet());

        List<ServiceInstance> lastInstanceList = instances;
        if(StringUtils.isNotBlank(workzone)) {
            lastInstanceList = new ArrayList<>();
            for (ServiceInstance instance : instances) {
                Map<String, String> metadata = instance.getMetadata();
                //根据zone头部判断
                if (metadata.entrySet().containsAll(attributes)) {
                    lastInstanceList.add(instance);
                }
            }

            //此处如果没有发现任何一个instance, 返回所有instance: 请根据自己情况定义
            if(lastInstanceList.size() <=0){
                lastInstanceList = instances;
            }
            else {
                positionKey = workzone;
            }
        }

        AtomicInteger mypos = postionMap.get(positionKey);
        if( mypos == null) {
            mypos = new AtomicInteger(new Random().nextInt(1000));
            postionMap.put(positionKey, mypos);
        }

        int pos = Math.abs(mypos.incrementAndGet());

        ServiceInstance instance = lastInstanceList.get(pos % lastInstanceList.size());

        return new DefaultResponse(instance);
    }

}
