package com.cnscud.dubboroom.helloname.dubbo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableDubbo(scanBasePackages = "com.cnscud.dubboroom.helloname")
//@PropertySource("classpath:/spring/dubbo-provider.properties")
public class DubboConfig {
}

