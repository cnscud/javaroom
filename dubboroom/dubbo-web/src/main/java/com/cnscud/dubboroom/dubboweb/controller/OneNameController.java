package com.cnscud.dubboroom.dubboweb.controller;

import com.cnscud.dubboroom.base.service.NameService;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
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
 * Hello Name Controller.
 *
 * @author Felix Zhang 2021-06-11 15:47
 * @version 1.0.0
 */
@RestController
@RequestMapping("/onename")
public class OneNameController {
    private static Logger logger = LoggerFactory.getLogger(OneNameController.class);

    @DubboReference
    private NameService nameService;


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
        catch (UnknownHostException e) {
            logger.error(e.getMessage(), e);
        }
        return localHost.getHostAddress();  // 返回格式为：xxx.xxx.xxx
    }

    public String getServerName() {
        return " [web:" + readServiceIp() + ":" + readServicePort() + "]";
    }


    @RequestMapping("/id/{userid}")
    public String helloById(@PathVariable("userid") String userid, HttpServletRequest request) {
        if (StringUtils.isNotBlank(userid) && StringUtils.isNumeric(userid)) {

            String workzone = request.getHeader("workzone");
            RpcContext context = RpcContext.getContext();
            if (StringUtils.isNotEmpty(workzone)) {
                //todo 测试: 设置: 应该移动到Filter中

                //context.setArguments();
                context.setAttachment("workzone", workzone);
            }

            return "hello " + nameService.readName(Integer.parseInt(userid)) + getServerName() + "";
        }

        return "hello guest" + getServerName() + "";
    }


}
