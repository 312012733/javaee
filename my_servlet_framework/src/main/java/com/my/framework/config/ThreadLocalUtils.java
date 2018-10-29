package com.my.framework.config;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalUtils
{
    /**
     * 本地线程类：用来放东西，线程绝对安全
     */
    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();
    
    private static Map<String, Object> getMap()
    {
        Map<String, Object> cache = threadLocal.get();
        
        if (null == cache)
        {
            threadLocal.set(new HashMap<>());
        }
        
        return threadLocal.get();
    }
    
    public static Object get(String key)
    {
        
        return getMap().get(key);
    }
    
    public static void put(String key, Object value)
    {
        getMap().put(key, value);
    }
    
    public static void clean()
    {
        getMap().clear();
    }
    
    // public static void main(String[] args) throws InterruptedException
    // {
    // ThreadLocalUtils.put("user", "Admin");
    //
    // new Thread()
    // {
    // @Override
    // public void run()
    // {
    //
    // for (int i = 0; i < 10; i++)
    // {
    // System.out.println(Thread.currentThread().getName() + "-------" +
    // ThreadLocalUtils.get("user"));
    // try
    // {
    // Thread.sleep(1000);
    // }
    // catch (InterruptedException e)
    // {
    // e.printStackTrace();
    // }
    // }
    //
    // }
    // }.start();
    //
    // System.out.println("-------------");
    //
    // Thread.sleep(1000 * 12);
    //
    // new Thread()
    // {
    // @Override
    // public void run()
    // {
    // System.out.println(Thread.currentThread().getName() + "-------" +
    // ThreadLocalUtils.get("user"));
    //
    // }
    // }.start();
    //
    // }
    
}
