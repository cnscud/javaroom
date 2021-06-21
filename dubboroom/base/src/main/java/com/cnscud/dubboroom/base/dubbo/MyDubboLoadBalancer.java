package com.cnscud.dubboroom.base.dubbo;

import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance;
import org.apache.dubbo.rpc.cluster.loadbalance.RandomLoadBalance;
import org.apache.dubbo.rpc.cluster.loadbalance.RoundRobinLoadBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * My Round robin load balance by workzone.
 */
public class MyDubboLoadBalancer extends AbstractLoadBalance {
    private static Logger logger = LoggerFactory.getLogger(MyDubboLoadBalancer.class);

    protected static RoundRobinLoadBalance roundRobinLoadBalance = new RoundRobinLoadBalance();
    protected static RandomLoadBalance randomLoadBalance = new RandomLoadBalance();

    public static final String NAME = "mylbl";
    protected static String ZONE_KEY = "workzone";

    protected RoundRobinLoadBalance getRoundRobinLoadBalance() {
        return roundRobinLoadBalance;
    }

    protected RandomLoadBalance getRandomLoadBalance() {
        return randomLoadBalance;
    }



    public MyDubboLoadBalancer() {
        logger.info("MyDubboLoadBalancer 已启动...");
    }

    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {

        //Object[] args = invocation.getArguments();
        String workzone = invocation.getAttachment("workzone");

        logger.info("url::::" + url);
        logger.info("parameters: " + Arrays.toString(invocation.getArguments()));
        logger.info("attachments: " + invocation.getAttachments());

        List<Invoker<T>> newInvokerList = new ArrayList<>();

        if (StringUtils.isEmpty(workzone)) {
            //默认走RoundRobin的策略
            return getRoundRobinLoadBalance().select(invokers, url, invocation);
        }

        //选择特定服务器
        for (Invoker<T> invoker : invokers) {
            URL serviceUrl = invoker.getUrl();
            logger.info("loop serviceUrl: " + serviceUrl.toIdentityString() + " " + serviceUrl.getParameters() + ", port: " + serviceUrl.getPort());

            if (serviceUrl.hasParameter(ZONE_KEY) && workzone!=null && workzone.equalsIgnoreCase(serviceUrl.getParameter(ZONE_KEY))) {
                logger.info("find match invoker for workzone: " + workzone + " ip: " + serviceUrl.getIp() + " port: " + serviceUrl.getPort());
                newInvokerList.add(invoker);
            }
        }

        if (!newInvokerList.isEmpty()) {
            return getRoundRobinLoadBalance().select(newInvokerList, url, invocation);
        }
        else {
            logger.info("not find invoker for workzone: " + workzone);
            return getRoundRobinLoadBalance().select(invokers, url, invocation);
        }
    }


}

