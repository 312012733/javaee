package com.my.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.my.bean.User;
import com.my.listener.MyHttpSessionBindingListener;
import com.my.service.IUserService;
import com.my.service.UserServiceImpl;
import com.my.vo.ResponseEntity;

/*
 * 
 * M V C
 * modle view controller
 * 
 * */
public class LoginServlet extends HttpServlet
{
    private static final long serialVersionUID = -6721472665784197425L;
    
    private IUserService userService = new UserServiceImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        
        PrintWriter out = resp.getWriter();
        
        ResponseEntity respEntity = new ResponseEntity();
        
        try
        {
            // 获取表单参数
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String checkCode = req.getParameter("checkcode");
            
            // 业务的处理
            HttpSession session = req.getSession();// 获取会话 等价于
            
            if (!checkCode.equals(session.getAttribute(GenerateCheckCodeServlet.CHECK_CODE)))
            {
                // throw new SecurityException("check code is error. ");
            }
            
            User user = userService.login(username, password);
            
            session.setAttribute(session.getId(), user);
            
            // session.setMaxInactiveInterval(0);// 0<= 不超时 。 默认值：-1
            // session.invalidate();
            
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
