package com.cnscud.betazone.pub.zonebyheader;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Feign Interceptor for transfer header.
 *
 * @author Felix Zhang 2021-06-10 10:04
 * @version 1.0.0
 */
public class FeignRequest4ZoneHeaderInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        //获取之前设置的header
        String workzone = RequestHeaderHolder.get();

        if(workzone !=null){
            template.header("workzone", workzone);
        }
    }
}
