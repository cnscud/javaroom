package com.cnscud.dubboroom.base.dubbo;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Filter Sample.
 *
 * @author Felix Zhang 2021-06-18 15:10
 * @version 1.0.0
 */
public class MyFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(MyFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        URL serviceUrl = invoker.getUrl();

        // before filter ...
        logger.info("filter serviceUrl: " + serviceUrl.getParameters() + ", port: " + serviceUrl.getPort());

        Result result = invoker.invoke(invocation);

        // after filter ...
        return result;
    }
}
