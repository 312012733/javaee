package com.my.interceptor;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@Component
public class TimeInterceptor extends AbstractInterceptor
{
    private static final Logger LOGGER = Logger.getLogger(TimeInterceptor.class);
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public String intercept(ActionInvocation invocation) throws Exception
    {
        Long startTime = System.currentTimeMillis();
        
        String result = invocation.invoke();// 执行下一个拦截器，或者是 执行action的方法
        
        Long endTime = System.currentTimeMillis();
        
        LOGGER.debug("cost:" + (endTime - startTime) + "  uri:" + invocation.getProxy().getActionName() + "  method:"
                + invocation.getProxy().getMethod() + "   className:"
                + invocation.getProxy().getAction().getClass().getName() + "  result:" + result);
        
        return result;
    }
    
}
