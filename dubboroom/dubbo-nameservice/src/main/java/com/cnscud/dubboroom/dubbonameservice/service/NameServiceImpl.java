package com.cnscud.dubboroom.dubbonameservice.service;

import com.cnscud.dubboroom.base.service.NameService;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Name Service.
 *
 * @author Felix Zhang 2021-06-11 12:10
 * @version 1.0.0
 */
@DubboService
public class NameServiceImpl implements NameService {
    private static Logger logger = LoggerFactory.getLogger(NameServiceImpl.class);

    @Autowired
    Environment environment;

    @Value("${dubbo.protocol.port}")
    private String port;

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
    public String readName(int id) {
        //logger.info("[remotename] workzone header:" + request.getHeader("workzone"));
        logger.info("call readName");

        if( names.get(id) == null ) {
            return defaultName + getServerName();
        }
        else
        {
            return names.get(id) + getServerName();
        }

    }

    public String readServicePort() {
        //return environment.getProperty("local.server.port");
        return port;
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
        return " [remotename: " + readServiceIp() + ":" + readServicePort() + "]";
    }

}
