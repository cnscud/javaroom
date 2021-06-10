package com.cnscud.betazone.hellonameservicebyheader.feign;

import com.cnscud.betazone.pub.zonebyheader.RequestHeaderHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Hello Controller from Remote Service.
 *
 * @author Felix Zhang 2021-06-04 09:29
 * @version 1.0.0
 */
@RestController
@RequestMapping("remote")
public class HelloNameByRemoteController {

    private static Logger logger = LoggerFactory.getLogger(HelloNameByRemoteController.class);

    @Autowired
    private FeignRemoteNameService feignRemoteNameService;

    @Autowired
    Environment environment;

    public String readServicePort() {
        return environment.getProperty("local.server.port");
    }

    public String readServiceIp() {
        InetAddress localHost = null;
        try {
            localHost = Inet4Address.getLocalHost();
        }
        catch (
                UnknownHostException e) {
            logger.error(e.getMessage(), e);
        }
        return localHost.getHostAddress();  // 返回格式为：xxx.xxx.xxx
        // localHost.getHostName() 一般是返回电脑用户名
    }

    public String getServerName() {
        return " [nameservice:" + readServiceIp() + ":" + readServicePort() + "]";
    }


    @RequestMapping("/id/{userid}")
    public String helloById(@PathVariable("userid") String userid, HttpServletRequest request) {
        logger.debug("call helloById with " + userid);

        logger.info("[nameservice] workzone header:" + request.getHeader("workzone"));
        /*

        //测试代码, 移入到filter中
        RequestHeaderHolder.set(request.getHeader("workzone"));
        */

        //测试异步调用
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.warn("thread call: " + feignRemoteNameService.readName(Integer.parseInt(userid)) + getServerName());
            }
        }).start();

        if (StringUtils.isNotBlank(userid) && StringUtils.isNumeric(userid)) {
            return "hello " + feignRemoteNameService.readName(Integer.parseInt(userid)) + getServerName();
        }

        return "hello guest"  +  getServerName();
    }

    @RequestMapping("/name/{username}")
    public String helloByName(@PathVariable("username") String username) {

        logger.debug("call helloByName with " + username);

        return "hello " + username  + getServerName();
    }

}

