package com.example.test

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.coroutines.delay

class SimpleApplicatioin:Application() {

    override fun onCreate() {
        super.onCreate()

        ARouter.openDebug()
        ARouter.openLog()
        ARouter.init(this)

        ThreadUtil.startThread(Runnable {
           Thread.sleep(3000)
            PrintTimeUtil.time("子线程初始化完成")
        })

        CrashCatchHandler.getInstance().init(this)
    }
}