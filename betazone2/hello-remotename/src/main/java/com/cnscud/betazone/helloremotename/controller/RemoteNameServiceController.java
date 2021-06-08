package com.cnscud.betazone.helloremotename.controller;

import com.cnscud.betazone.helloremotename.core.service.RemoteNameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * 姓名服务.
 */
@RestController
@RequestMapping("remote")
public class RemoteNameServiceController implements RemoteNameService {
    private static Logger logger = LoggerFactory.getLogger(RemoteNameServiceController.class);

    @Autowired
    Environment environment;

    final static String defaultName = "guest";
    static Map<Integer, String> names = new HashMap<>();

    static {
        names.put(1, "Felix");
        names.put(2, "World");
        names.put(3, "Sea");
        names.put(4, "Sky");
        names.put(5, "Mountain");
    }

    @Override
    @RequestMapping("/id/{id}")
    public String readName(@PathVariable("id") int id) {

        if( names.get(id) == null ) {
            return defaultName + getServerName();
        }
        else
        {
            return names.get(id) + getServerName();
        }
    }


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
        // localHost.getHostName() 一般是返回电脑用户名
    }

    public String getServerName() {
        return " [remotename: " + readServiceIp() + ":" + readServicePort() + "]";
    }

}
