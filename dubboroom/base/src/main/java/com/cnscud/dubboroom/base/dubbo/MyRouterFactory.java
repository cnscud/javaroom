package com.cnscud.dubboroom.base.dubbo;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.cluster.CacheableRouterFactory;
import org.apache.dubbo.rpc.cluster.Router;

/**
 * My Router Factory.
 *
 * @author Felix Zhang 2021-06-21 10:48
 * @version 1.0.0
 */
public class MyRouterFactory extends CacheableRouterFactory {
    @Override
    protected Router createRouter(URL url) {
        return new MyRouter();
    }
}
