package com.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.my.service.IUserService;
import com.my.service.UserServiceImpl2;

public class SpringTest
{
    
    public static class MyInvocationHandler implements InvocationHandler
    {
        private Object source;
        
        public MyInvocationHandler(Object source)
        {
            this.source = source;
        }
        
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
        {
            try
            {
                
                Object result = method.invoke(source, args);
                
                System.out.println("commit.........success......................");
                return result;
            }
            catch (Exception e)
            {
                throw e;
            }
            
        }
        
    }
    
    public static void main(String[] args)
    {
        
        // UserServiceProxy proxy = new UserServiceProxy();
        UserServiceImpl2 userService = new UserServiceImpl2();
        // userService.login("admin", "1234");
        
        MyInvocationHandler myInvocatoinHandler = new MyInvocationHandler(userService);
        
        IUserService proxy = (IUserService) Proxy.newProxyInstance(SpringTest.class.getClassLoader(),
                UserServiceImpl2.class.getInterfaces(), myInvocatoinHandler);
        
        proxy.login("admin1234", "1234");
        
    }
    
}
