package com.cnscud.betazone.pub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logger Utils.
 *
 * @author Felix Zhang 2021-06-04 14:31
 * @version 1.0.0
 */
public class LogUtils {

    static Logger logger = LoggerFactory.getLogger(LogUtils.class);

    public static void info(String msg){
        logger.info(msg);
    }

    public static void debug(String msg){
        logger.debug(msg);
    }

    public static void warn(String msg){
        logger.warn(msg);
    }
}
