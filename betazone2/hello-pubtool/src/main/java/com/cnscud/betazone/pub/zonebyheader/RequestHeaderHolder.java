package com.cnscud.betazone.pub.zonebyheader;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * Holder: store a String.
 * !!!! 仅供参考, 没有全面测试过.
 *
 * 注意: 测试的几种情况要考虑: 父子线程, 线程池, 高QPS等等: InheritableThreadLocal, TransmittableThreadLocal, HystrixRequestVariableDefault
 *
 * @author Felix Zhang 2021-06-10 10:37
 * @version 1.0.0
 */
public class RequestHeaderHolder {
    //如果存Map, 好像有问题, 此处用String
    private static final ThreadLocal<String> MYLOCAL;

    static {
        MYLOCAL = new InheritableThreadLocal();
                //new TransmittableThreadLocal();

    }

    public static String get() {
        return MYLOCAL.get();
    }


    public static void set(String value) {
        MYLOCAL.set(value);
    }

    public static void remove() {
        MYLOCAL.remove();
    }


}
