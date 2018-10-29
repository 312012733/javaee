package com.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//@WebListener
public class SpringApplicationContextListener implements ServletContextListener
{
    
    public static class SpringContext
    {
        
        private static ApplicationContext context;
        
        static
        {
            context = new ClassPathXmlApplicationContext("application-context.xml");
        }
        
        public static ApplicationContext context()
        {
            return context;
        }
    }
    
    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        // try
        // {
        // Class.forName(SpringContext.class.getName());
        // }
        // catch (ClassNotFoundException e)
        // {
        // e.printStackTrace();
        // }
        
    }
    
}
