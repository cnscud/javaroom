package com.cnscud.dubboroom.base.dubbo;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.router.AbstractRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * My Router.
 *
 * @author Felix Zhang 2021-06-21 10:38
 * @version 1.0.0
 */
public class MyRouter extends AbstractRouter {

    private static Logger logger = LoggerFactory.getLogger(MyRouter.class);
    protected static String ZONE_KEY = "workzone";

    @Override
    public <T> List<Invoker<T>> route(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException {

        //自己的逻辑
        String workzone = invocation.getAttachment("workzone");

        List<Invoker<T>> newInvokerList = new ArrayList<>();

        //选择特定服务器
        for (Invoker<T> invoker : invokers) {
            URL serviceUrl = invoker.getUrl();
            logger.info("router serviceUrl: " + serviceUrl.toIdentityString() + " " + serviceUrl.getParameters() + ", port: " + serviceUrl.getPort());

            if (serviceUrl.hasParameter(ZONE_KEY) && workzone != null && workzone.equalsIgnoreCase(serviceUrl.getParameter(ZONE_KEY))) {
                logger.info("find match invoker for workzone: " + workzone + " ip: " + serviceUrl.getIp() + " port: " + serviceUrl.getPort());
                newInvokerList.add(invoker);
            }
        }

        if (newInvokerList.size() > 0) {
            return newInvokerList;
        }

        return invokers;
    }

}
