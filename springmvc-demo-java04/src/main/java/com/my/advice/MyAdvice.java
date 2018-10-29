package com.my.advice;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

public class MyAdvice implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice
{
    
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable
    {
        
        System.out.println("--------------------"+this.getClass().getName() + "-----before  invoke.......source:" + target.getClass().getName()
                + "." + method.getName());
        
    }
    
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable
    {
        System.out.println("--------------------"+this.getClass().getName() + "----afterReturning  invoke.......source:"
                + target.getClass().getName() + "." + method.getName());
        
    }
    
    public void afterThrowing(Method method, Object[] args, Object target, Exception ex)
    {
        System.out.println("--------------------"+this.getClass().getName() + "-----afterThrowing  invoke.......source:"
                + target.getClass().getName() + "." + method.getName());
        
    }
    
}
