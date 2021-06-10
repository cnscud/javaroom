package com.cnscud.betazone.hellonameservicebyheader.config;

import com.cnscud.betazone.pub.zonebyheader.ZoneHeaderFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注入Filter.
 *
 * @author Felix Zhang 2021-06-10 17:05
 * @version 1.0.0
 */
@Configuration
public class WebZoneHeaderFilterConfig {
    @Bean
    public FilterRegistrationBean<ZoneHeaderFilter> zoneheaderFilter() {
        FilterRegistrationBean<ZoneHeaderFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new ZoneHeaderFilter());
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }
}
