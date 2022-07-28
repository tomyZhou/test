package com.example.event;

import android.util.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;
import org.json.JSONObject;

import java.util.HashMap;

/**'
 * aspectj 在 kotlin下不起作用，解决办法：https://blog.csdn.net/EthanCo/article/details/87938487
 */
@Aspect
public class AspectTrace {

    // 定义切点
    @Pointcut("execution(* android.app.Activity+.onCreate(..))")
    public void activityOnCreate() {

    }


    @Around("activityOnCreate()")
    public void onCreateInjector(ProceedingJoinPoint point) {
        Object object = point.getTarget();
        Log.i("TestAsject","onCreateInjector: "+ object.getClass().getSimpleName() + ": enter");
        try {
            point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * 针对带有AspectAnalyze注解的方法
     */
    @Pointcut("execution(@com.example.event.AspectAnalyze * *(..))")
    public void aspectAnalyzeAnnotation() {
    }

    /**
     * 针对前面 aspectAnalyzeAnnotation() 的配置
     */
    @Around("aspectAnalyzeAnnotation()")
    public void aroundJoinAspectAnalyze(final ProceedingJoinPoint joinPoint) throws Throwable {
        Object target = joinPoint.getTarget();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        AspectAnalyze aspectAnalyze = methodSignature.getMethod().getAnnotation(AspectAnalyze.class);
        long startTimeMillis = System.currentTimeMillis();
        joinPoint.proceed();
        long duration = System.currentTimeMillis() - startTimeMillis;
        SourceLocation location = joinPoint.getSourceLocation();
        String message = String.format("%s(%s:%s) [%sms]", methodSignature.getMethod().getName(), location.getFileName(), location.getLine(), duration);
        Log.e("AspectTrace", message);

        HashMap map = getParam(joinPoint);
        Log.e("AspectTrace", new JSONObject(map).toString());

        EventTrack.track(map); //调用打点api


    }

    //获取参数名和参数值
    public HashMap getParam(ProceedingJoinPoint proceedingJoinPoint) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Object[] values = proceedingJoinPoint.getArgs();
        String[] names = ((CodeSignature) proceedingJoinPoint.getSignature()).getParameterNames();
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], values[i]);
        }

        return map;
    }


    /**
     * 针对前面 aspectDebugLogAnnotation() 或 activityOnCreatePointcut() 的配置
     */
    @Around("aspectDebugLogAnnotation() || activityOnCreatePointcut()")
    public void aroundJoinAspectDebugLog(final ProceedingJoinPoint joinPoint) throws Throwable {
        long startTimeMillis = System.currentTimeMillis();
        joinPoint.proceed();
        long duration = System.currentTimeMillis() - startTimeMillis;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        SourceLocation location = joinPoint.getSourceLocation();
        String message = String.format("%s(%s:%s) [%sms]", methodSignature.getMethod().getName(), location.getFileName(), location.getLine(), duration);
        Log.e("AspectTrace", message);
    }

}
