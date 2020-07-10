package com.cttnet.zhwg.ywkt.mtosi.async;

import com.cttnet.common.util.SysConfig;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * 异步任务管理器
 * </pre>
 * @author zhangyaomin
 * @since JDK 1.8
 */
public class AsyncManager {

	private static final Object LOCK = new Object();
	

	private ThreadPoolExecutor executor;
	/**
	 * 异步操作任务调度线程池
	 */
	private static AsyncManager asyncManager;

	private AsyncManager() {

	};

	/**
	 * 获取线程管理器
	 * @return
	 */
	public static AsyncManager getInstance() {

		if (asyncManager == null) {
			synchronized (LOCK) {
				if (asyncManager == null) {
					int corePoolSize = Integer.parseInt(SysConfig.getProperty("ywkt.async.core-pool-size", "5"));
					int maximumPoolSize = Integer.parseInt(SysConfig.getProperty("ywkt.async.maximum-pool-size", "5"));
					int keepAliveTime = Integer.parseInt(SysConfig.getProperty("ywkt.async.keep-alive-time", "0"));
					asyncManager = new AsyncManager();
					asyncManager.executor = new ThreadPoolExecutor(
							corePoolSize, maximumPoolSize,
							keepAliveTime, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
				}
			}
		}

		return asyncManager;
	}

	/**
	 * 执行任务
	 * @param runnable 任务
	 */
	public void execute(Runnable runnable) {
		this.executor.execute(runnable);
	}

}
