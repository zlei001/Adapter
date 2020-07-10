package com.cttnet.zhwg.ywkt.util;

import com.cttnet.common.util.SysConfig;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <pre></pre>
 *
 * @author zhangyaomin
 * @date 2020-06-20
 * @since jdk 1.8
 */
public class AsyncUtils {

    private AsyncUtils() {}
    private final static ThreadPoolExecutor executor;

    static {
        int corePoolSize = Integer.parseInt(SysConfig.getProperty("ywkt.http.core-pool-size", "5"));
         executor = new ThreadPoolExecutor(
                corePoolSize, corePoolSize,
                0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    }

    /**
     * 执行任务
     * @param runnable 任务
     */
    public static void execute(Runnable runnable) {
        executor.execute(runnable);
    }


}
