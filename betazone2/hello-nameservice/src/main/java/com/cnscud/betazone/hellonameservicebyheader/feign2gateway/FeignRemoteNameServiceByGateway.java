package com.cnscud.betazone.hellonameservicebyheader.feign2gateway;


import com.cnscud.betazone.helloremotename.core.service.RemoteNameService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "betazone-hello-gateway")
public interface FeignRemoteNameServiceByGateway extends RemoteNameService {

    @RequestMapping("/remoteapi/remote/id/{id}")
    @Override
    String readName(@PathVariable("id") int id) ;
}
