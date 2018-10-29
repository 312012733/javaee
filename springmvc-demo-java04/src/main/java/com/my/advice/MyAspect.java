package com.my.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class MyAspect
{
    @Pointcut(value = "execution(* com.my.service.*.*(..)) && @annotation(com.my.advice.SystemLogger)")
    public void pointcut()
    {
    };
    
    // @Around(value = "pointcut()")
    // public void around(ProceedingJoinPoint jp)
    @Around(value = "execution(* com.my.service.*.*(..)) && @annotation(sdkfjsdkfjsdkfjsk)")
    public Object around(ProceedingJoinPoint jp, SystemLogger sdkfjsdkfjsdkfjsk) throws Throwable
    {
        
        try
        {
            Object obj = jp.getThis();
            System.out.println(obj);
            // jp.proceed();//
            
            // MethodSignature signature = (MethodSignature) jp.getSignature();
            // String[] parameterNames = signature.getParameterNames();
            // Method m = signature.getMethod();
            // SystemLogger systemLogger = m.getAnnotation(SystemLogger.class);
            
            System.out.println("-------around: desc: " + sdkfjsdkfjsdkfjsk.desc());
            
            return jp.proceed(jp.getArgs());
            
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            
            throw e;
        }
        
    }
    
    @Before("execution(* com.my.service.*.*(..))")
    public void before(JoinPoint jp) throws Throwable
    {
        
        System.out.println("--------------------" + this.getClass().getName() + "-----before  invoke.......source:"
                + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName());
        
    }
    
    @AfterReturning(value = "pointcut()", returning = "returValue")
    public void afterReturning(JoinPoint jp, Object returValue) throws Throwable
    {
        System.out.println("--------------------" + this.getClass().getName()
                + "-----afterReturning  invoke.......source:" + jp.getTarget().getClass().getName() + "."
                + jp.getSignature().getName() + "    returValue:" + returValue);
        
    }
    
    @AfterThrowing(value = "pointcut()", throwing = "e")
    public void afterThrowing(JoinPoint jp, Throwable e)
    {
        System.out
                .println("--------------------" + this.getClass().getName() + "-----afterThrowing  invoke.......source:"
                        + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName() + "    e:" + e);
        
    }
    
}
