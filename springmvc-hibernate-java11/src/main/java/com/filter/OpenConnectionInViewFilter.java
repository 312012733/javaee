package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class OpenConnectionInViewFilter implements Filter
{
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fc) throws IOException, ServletException
    {
        
        fc.doFilter(req, resp);
        
        // DBUtils.close((Connection)
        // ThreadLocalUtils.get(ThreadLocalUtils.JDBC_CONNECTION));
        // HibernateUtils.close((SqlSession)
        // ThreadLocalUtils.get(ThreadLocalUtils.SQL_SESSION));
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void destroy()
    {
        // TODO Auto-generated method stub
        
    }
    
}
