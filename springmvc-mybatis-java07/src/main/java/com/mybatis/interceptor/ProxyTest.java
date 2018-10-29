package com.mybatis.interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class ProxyTest
{
    public static interface Pay
    {
        
        void payMoney(int money);
    }
    
    public static class Boss implements Pay
    {
        @Override
        public void payMoney(int money)
        {
            System.out.println("boss 实际支付 " + money + " 块钱。。");
        }
    }
    
    public static class Assistant implements InvocationHandler
    {
        // protected Pay target;
        //
        // public Assistant(Pay target)
        // {
        // this.target = target;
        // }
        protected Pay target;
        
        public Assistant(Pay target)
        {
            this.target = target;
        }
        
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
        {
            System.out.println("BossInvocationHandler 付钱过程的第一步： 去收银台。");
            
            Object result = method.invoke(target, args);
            
            System.out.println("BossInvocationHandler 付钱过程的最后一步： 返回boss那里。");
            
            return result;
        }
        
    }
    
    public static void main(String[] args)
    {
        int money = 200;
        // Pay target = new Boss();// 目标对象
        Pay target = new Boss();// 目标对象
        
        // 1.JDK 动态代理
        Assistant assistant = new Assistant(target);
        Pay proxy = (Pay) Proxy.newProxyInstance(ProxyTest.class.getClassLoader(), Boss.class.getInterfaces(),
                assistant);
        
        proxy.payMoney(money);
        
        System.out.println("---------jdk proxy obj-----------------------" + proxy.getClass().getName());
        
        // 2.CGLB
        Enhancer enhancer = new Enhancer();
        
        Callback callback = new MethodInterceptor()
        {
            @Override
            public Object intercept(Object obj, Method method, Object[] arg2, MethodProxy methodProxy) throws Throwable
            {
                
                System.out.println("----cglib 动态代理---目标方法 执行 之前...........");
                
                Object result = method.invoke(target, arg2);
                
                System.out.println("----cglib 动态代理---目标方法 执行 之后...........");
                
                return result;
            }
        };
        
        Boss proxyBoss = (Boss) enhancer.create(Boss.class, callback);
        
        proxyBoss.payMoney(money);
        
        System.out.println("---------cglib proxy obj-----------------------" + proxyBoss.getClass().getName());
    }
    
    // public static void main(String[] args)
    // {
    // int money = 200;
    //
    // Pay boss = new Boss();
    //
    //// boss.payMoney(200);
    //
    // Pay assistant = new Assistant(boss);
    //
    // assistant.payMoney(200);
    //
    // }
    
    // public static class Assistant implements Pay
    // {
    // Pay boss;
    //
    // public Assistant(Pay boss)
    // {
    // this.boss = boss;
    // }
    //
    // @Override
    // public void payMoney(int money)
    // {
    //
    // System.out.println("Assistant 付钱过程的第一步： 去收银台。");
    //
    // boss.payMoney(200);
    //
    // System.out.println("Assistant 付钱过程的最后一步： 返回boss那里。");
    // }
    //
    // }
}
