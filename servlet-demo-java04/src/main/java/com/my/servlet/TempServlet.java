package com.my.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.my.vo.ResponseEntity;

public class TempServlet extends HttpServlet
{
    private static final long serialVersionUID = -6721472665784197425L;
    
//    private IUserService userService = new UserServiceImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        
        PrintWriter out = resp.getWriter();
        
        ResponseEntity respEntity = new ResponseEntity();
        
        try
        {
            
            // 三大作用域对象 HttpServletRequest HttpSession ServletContext
            
            // 存。取 。东西
            
            req.setAttribute("key", "value");// A --- set --- forward(req,resp)
            req.getAttribute("key");// B ------ get
            
            // req.getRemoteHost();
            // req.getRemotePort();
            
            HttpSession session = req.getSession();
            session.setAttribute("key", "value");// A --- set
            session.getAttribute("key"); // B -- get
            
            ServletContext context = req.getServletContext();
            // context = getServletContext();
            // context = this.getServletConfig().getServletContext();
            //
            // context.setAttribute("key", "value");
            // context.getAttribute("key");
            //
            // context.getContextPath();
            // context.getRealPath("/xxx");//E:\apache-tomcat-9.0.0.M26\wtpwebapps\servlet-demo-java04\xxx
            // context.getResourceAsStream("\\page\\a.html");//E:\apache-tomcat-9.0.0.M26\wtpwebapps\servlet-demo-java04\page\a.html
       
        
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            // 设置 请求失败的 状态 及 原因
            resp.setStatus(400);
            respEntity.setErrorMsg(e.getMessage());
        }
        
        // 通用的响应结果
        String result = JSONObject.toJSONString(respEntity);
        out.write(result);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        this.doGet(req, resp);
    }
    
}
