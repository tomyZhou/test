package com.example.event;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

import kotlin.jvm.Throws;

public class CheckLoginAspect {

    private boolean isLogin = false;


    @Pointcut("execution(@com.example.event.CheckLogin**(..)")
    public void checkLogin(){

    }


    @Around("checkLogin()")
    public void aroundCheckLogin(ProceedingJoinPoint joinPoint) throws Throwable {
        if(isLogin){
            joinPoint.proceed();
        }else{
            Log.e("xxx","请先登录");
        }
    }

}
