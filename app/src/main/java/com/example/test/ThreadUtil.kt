package com.example.test

import android.os.Handler
import android.os.Looper
import android.util.Log
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * 线程池工具
 * @author ex-yanzhangbao001
 */
object ThreadUtil {

    /**
     * 线程执行器
     */
    private val executor by lazy {
        ThreadPoolExecutor(
            //根据cup核心数设置线程池数量
            Runtime.getRuntime().availableProcessors(),
            //最大线程池数量= cpu核心数*2+1
            Runtime.getRuntime().availableProcessors().times(2).plus(1),
            //等待线程的存活时间
            1L,
            //等待线程存活时间的单位
            TimeUnit.HOURS,
            //工作线程队列
            LinkedBlockingDeque<Runnable>(),
            //默认的线程工厂
            Executors.defaultThreadFactory(),
            //取消策略，当超过等待线程池的数量后禁止添加了
            ThreadPoolExecutor.AbortPolicy()
        )
    }

    /**
     * 开始执行线程
     */
    @JvmStatic
    fun startThread(runnable: Runnable?) {
        runnable?.run {
            executor.execute(runnable)
        } ?: Log.e("ThreadUtil","ThreadUtil::startThread::runnable is Null")
    }

    /**
     * 取消线程
     */
    @JvmStatic
    fun cancelThread(runnable: Runnable?) {
        runnable?.run {
            executor.remove(runnable)
        }
    }

    private val mainThreadHandler = Handler(Looper.getMainLooper())

    @JvmStatic
    fun runOnMainThread(runnable: Runnable?) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable?.run()
        } else {
            runOnMainThread(runnable, 0)
        }
    }

    @JvmStatic
    fun runOnMainThread(runnable: Runnable?, delay: Long) {
        if (runnable == null) {
            return
        }
        if (delay == 0L && isMainThread()) {
            runnable.run()
        } else {
            mainThreadHandler.postDelayed(runnable, delay)
        }
    }

    @JvmStatic
    fun isMainThread(): Boolean {
        return Thread.currentThread() === Looper.getMainLooper().thread
    }
}