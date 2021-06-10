package com.cnscud.betazone.pub.zonebyheader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Web Filter.
 *
 * @author Felix Zhang 2021-06-10 16:45
 * @version 1.0.0
 */
public class ZoneHeaderFilter  extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        logger.info("[ZoneHeaderFilter] workzone header:" + request.getHeader("workzone"));

        //给Feign Client用的, 使用位置: FeignRequest4ZoneHeaderInterceptor
        RequestHeaderHolder.set(request.getHeader("workzone"));

        filterChain.doFilter(request, response);
    }



}
