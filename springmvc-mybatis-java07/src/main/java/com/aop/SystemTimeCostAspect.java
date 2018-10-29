package com.aop;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.annotation.SystemCostTime;

@Component
@Aspect
public class SystemTimeCostAspect
{
    private static final Log LOG = LogFactory.getLog(SystemTimeCostAspect.class);
    
    @Autowired
    private ApplicationContext context;
    
    @Pointcut(value = "execution(* com.controller.*.*(..)) &&  @annotation(com.annotation.SystemCostTime)") // TODO
    public void pointCut()
    {
        
    }
    
    // @Around(value = "pointCut()") // pointCut
    // public Object hanlder(ProceedingJoinPoint joinPoint) throws Throwable//
    // jointpoint
    // @Around(value = "execution(* com.controller.*.*(..)) &&
    // @annotation(systemCostTime1231)") // pointCut
    // public Object hanlder(ProceedingJoinPoint joinPoint, SystemCostTime
    // systemCostTime1231) throws Throwable// jointpoint
    @Around(value = "execution(* com.controller.*.*(..)) && @annotation(com.annotation.SystemCostTime)") // pointCut
    public Object aroundHanlder(ProceedingJoinPoint joinPoint) throws Throwable// jointpoint
    {
        try
        {
            // 获取注解
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method targetMethod = methodSignature.getMethod();
            RequestMapping requestMapping = targetMethod.getAnnotation(RequestMapping.class);
            SystemCostTime systemCostTime = targetMethod.getAnnotation(SystemCostTime.class);
            
            // 实现切面业务
            long startTime = System.currentTimeMillis();
            
            Object[] args = joinPoint.getArgs();
            Object result = joinPoint.proceed(args);// 执行一个代理方法 或者 是 目标方法
            
            long cost = System.currentTimeMillis() - startTime;
            
            Object proxy = null;
            Class<?>[] interfaces = joinPoint.getTarget().getClass().getInterfaces();
            
            if (null != interfaces && interfaces.length > 0)
            {
                proxy = context.getBean(interfaces[0]);
            }
            else
            {
                proxy = context.getBean(joinPoint.getTarget().getClass());
            }
            
            LOG.info(proxy + "【" + joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName()
                    + " cost:" + cost + "ms" + "】");
            return result;
        }
        catch (Throwable e)
        {
            throw e;
        }
        
    }
    
    // @Before(value = "execution(* com.controller.*.*(..)) &&
    // @annotation(com.annotation.SystemCostTime)") // pointCut
    // public void beforeHanlder(JoinPoint joinPoint) throws Throwable//
    // jointpoint
    // {
    // LOG.info("【" + joinPoint.getTarget().getClass().getName() + "." +
    // joinPoint.getSignature().getName()
    // + " beforeHanlder --- invoke...");
    // }
    //
    // @AfterReturning(value = "execution(* com.controller.*.*(..)) &&
    // @annotation(com.annotation.SystemCostTime)", returning = "result") //
    // pointCut
    // public void afterHanlder(JoinPoint joinPoint, Object result) throws
    // Throwable// jointpoint
    // {
    // LOG.info("【" + joinPoint.getTarget().getClass().getName() + "." +
    // joinPoint.getSignature().getName()
    // + " afterHanlder --- invoke..." + result);
    // }
}
