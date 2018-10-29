package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CharSetFilter implements Filter
{
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fc) throws IOException, ServletException
    {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpResp = (HttpServletResponse) resp;
        
        httpReq.setCharacterEncoding("utf-8");
        httpResp.setCharacterEncoding("utf-8");
        
        fc.doFilter(req, resp);
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
