package com.my.framework.config;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletContextUtil
{
    public static String HTTP_SERVLET_REQUEST = "http_servlet_request";
    public static String HTTP_SERVLET_RESPONSE = "http_servlet_response";
    
    public static HttpSession getHttpSession()
    {
        return getHttpServletRequest().getSession();// true
    }
    
    public static HttpSession getHttpSession(boolean create)
    {
        return getHttpServletRequest().getSession(create);
    }
    
    public static ServletContext getServletContext()
    {
        return getHttpServletRequest().getServletContext();
    }
    
    public static HttpServletRequest getHttpServletRequest()
    {
        return (HttpServletRequest) ThreadLocalUtils.get(HTTP_SERVLET_REQUEST);
    }
    
    public static HttpServletResponse getHttpServletResponse()
    {
        return (HttpServletResponse) ThreadLocalUtils.get(HTTP_SERVLET_RESPONSE);
    }
    
}
