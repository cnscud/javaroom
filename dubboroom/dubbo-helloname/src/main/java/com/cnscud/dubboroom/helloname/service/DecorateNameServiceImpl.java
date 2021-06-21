package com.cnscud.dubboroom.helloname.service;

import com.cnscud.dubboroom.base.service.DecorateNameService;
import com.cnscud.dubboroom.base.service.NameService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Decorate Name Service Imple.
 *
 * @author Felix Zhang 2021-06-16 18:12
 * @version 1.0.0
 */
@DubboService
public class DecorateNameServiceImpl implements DecorateNameService {

    private static Logger logger = LoggerFactory.getLogger(DecorateNameServiceImpl.class);


    @DubboReference
    private NameService nameService;


    @Value("${dubbo.protocol.port}")
    private String port;


    @Override
    public String readName(int id) {
        //return "[Decorate]" + "";

        return "[Decorate]" + nameService.readName(id) + getServerName() + "";
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
        return " [helloname: " + readServiceIp() + ":" + readServicePort() + "]";
    }

}
