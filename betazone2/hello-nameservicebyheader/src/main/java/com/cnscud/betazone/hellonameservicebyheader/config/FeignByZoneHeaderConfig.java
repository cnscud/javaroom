package com.cnscud.betazone.hellonameservicebyheader.config;

import com.cnscud.betazone.pub.zonebyheader.FeignRequest4ZoneHeaderInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * Interceptor configuration 声明拦截器配置.
 *
 * @author Felix Zhang 2021-06-10 11:09
 * @version 1.0.0
 */
public class FeignByZoneHeaderConfig {
    @Bean("myInterceptor")
    public RequestInterceptor getRequestInterceptor() {
        return new FeignRequest4ZoneHeaderInterceptor();
    }
}
