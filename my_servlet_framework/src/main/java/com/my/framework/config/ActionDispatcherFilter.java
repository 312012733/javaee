package com.my.framework.config;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.framework.config.Struts.Action;

public class ActionDispatcherFilter implements Filter
{
    
    private Struts coinfig;
    
    static Map<String, ActionInfo> actionMap = new HashMap<>();
    
    class ActionInfo
    {
        Class<?> actionClass;
        
        Method method;
        
        public ActionInfo(Class<?> actionClass, Method method)
        {
            this.actionClass = actionClass;
            this.method = method;
        }
        
        @Override
        public String toString()
        {
            return "ActionInfo [actionClass=" + actionClass + ", method=" + method.getName() + "]";
        }
        
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        XmlUtils utils = new XmlUtils("struts.xml");
        coinfig = utils.parse(Struts.class);
        
        if (null != coinfig && null != coinfig.getActions())
        {
            Map<String, Class<?>> actionClassMap = new HashMap<>();
            for (Action action : coinfig.getActions())
            {
                String className = action.getClassName();
                Class<?> actionClass = null;
                
                if (actionClassMap.containsKey(className))
                {
                    actionClass = actionClassMap.get(className);
                }
                else
                {
                    try
                    {
                        Class<?> classObj = Class.forName(className);
                        actionClassMap.put(className, classObj);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        continue;
                    }
                }
                
                try
                {
                    String name = action.getName();
                    Method method = actionClass.getMethod(action.getMethodName());
                    actionMap.put(name, new ActionInfo(actionClass, method));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    continue;
                }
            }
        }
        
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;
        
        ThreadLocalUtils.put(ServletContextUtil.HTTP_SERVLET_REQUEST, httpReq);
        ThreadLocalUtils.put(ServletContextUtil.HTTP_SERVLET_RESPONSE, httpResp);
        
        String actionName = buildActionName(httpReq);
        
        ActionInfo actionInfo = actionMap.get(actionName);
        
        if (null != actionInfo)
        {
            try
            {
                actionInfo.method.invoke(actionInfo.actionClass.newInstance());
                return;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
        chain.doFilter(request, response);
    }
    
    private String buildActionName(HttpServletRequest httpReq)
    {
        String path = httpReq.getContextPath();
        String uri = httpReq.getRequestURI();
        
        return uri.substring(path.length() + 1);
    }
    
    @Override
    public void destroy()
    {
        
    }
    
    public static String getServletPath(HttpServletRequest request)
    {
        String servletPath = request.getServletPath();
        
        String requestUri = request.getRequestURI();
        // Detecting other characters that the servlet container cut off (like
        // anything after ';')
        if (requestUri != null && servletPath != null && !requestUri.endsWith(servletPath))
        {
            int pos = requestUri.indexOf(servletPath);
            if (pos > -1)
            {
                servletPath = requestUri.substring(requestUri.indexOf(servletPath));
            }
        }
        
        if (!isEmpty(servletPath))
        {
            return servletPath;
        }
        
        int startIndex = request.getContextPath().equals("") ? 0 : request.getContextPath().length();
        int endIndex = request.getPathInfo() == null ? requestUri.length()
                : requestUri.lastIndexOf(request.getPathInfo());
        
        if (startIndex > endIndex)
        { // this should not happen
            endIndex = startIndex;
        }
        
        return requestUri.substring(startIndex, endIndex);
    }
    
    public static String getUri(HttpServletRequest request)
    {
        // handle http dispatcher includes.
        String uri = (String) request.getAttribute("javax.servlet.include.servlet_path");
        if (uri != null)
        {
            return uri;
        }
        
        uri = getServletPath(request);
        if (!isEmpty(uri))
        {
            return uri;
        }
        
        uri = request.getRequestURI();
        return uri.substring(request.getContextPath().length());
    }
    
    public static boolean isEmpty(final CharSequence cs)
    {
        return cs == null || cs.length() == 0;
    }
}
