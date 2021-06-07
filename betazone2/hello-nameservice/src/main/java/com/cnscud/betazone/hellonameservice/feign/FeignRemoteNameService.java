package com.cnscud.betazone.hellonameservice.feign;


import com.cnscud.betazone.helloremotename.core.service.RemoteNameService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "betazone-hello-remotename")
public interface FeignRemoteNameService extends RemoteNameService {

    @RequestMapping("/remote/id/{id}")
    @Override
    String readName(@PathVariable("id") int id) ;
}
