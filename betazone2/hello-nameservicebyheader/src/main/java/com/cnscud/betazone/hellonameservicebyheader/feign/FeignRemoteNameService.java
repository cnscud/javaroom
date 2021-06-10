package com.cnscud.betazone.hellonameservicebyheader.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 远端接口定义.
 */
@FeignClient(value = "betazone-hello-remotename")
public interface FeignRemoteNameService {

    @RequestMapping("/remote/id/{id}")
    String readName(@PathVariable("id") int id) ;
}
