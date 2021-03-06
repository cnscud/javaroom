package com.cnscud.betazone.hellonameservicebyheader.localservice;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Hello Controller.
 *
 * @author Felix Zhang 2021-06-04 09:29
 * @version 1.0.0
 */
@RestController
@RequestMapping("hello")
public class HelloNameController {

    private static Logger logger = LoggerFactory.getLogger(HelloNameController.class);

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

    @Autowired
    private UserNameService userNameService;

    @RequestMapping("/id/{userid}")
    public String helloById(@PathVariable("userid") String userid) {
        logger.debug("call helloById with " + userid);

        if (StringUtils.isNotBlank(userid) && StringUtils.isNumeric(userid)) {
            return "hello " + userNameService.readName(Integer.parseInt(userid)) + getServerName();
        }

        return "hello guest"  +  getServerName();
    }

    @RequestMapping("/name/{username}")
    public String helloByName(@PathVariable("username") String username) {

        logger.debug("call helloByName with " + username);

        return "hello " + username  + getServerName();
    }

}

