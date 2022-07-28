package com.example.test;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CrashCatchHandler implements Thread.UncaughtExceptionHandler {
 
    public static final String TAG = "CrashCatchHandler";
    private static CrashCatchHandler crashHandler = new CrashCatchHandler();
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultCaughtExceptionHandler;
 
    /**
     * 饿汉单例模式(静态）
     *
     * @return
     */
    public static CrashCatchHandler getInstance() {
        return crashHandler;
    }
 
    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        //获取默认的系统异常捕获器
        mDefaultCaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //把当前的crash捕获器设置成默认的crash捕获器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
 
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (!handleException(throwable) && mDefaultCaughtExceptionHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultCaughtExceptionHandler.uncaughtException(thread, throwable);
        }else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : "+ e);
            }


        }
    }
    /**
     * 自定义错误处理
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        final String msg = ex.getLocalizedMessage();
        if (msg == null) {
            return false;
        }

        String errorStack = getErrorStackTrace(ex);

         Log.e("xxxxxxxxxxxxx",errorStack);

        return true;
    }

    private String getErrorStackTrace(Throwable e){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(baos));
        return baos.toString();
    }
}