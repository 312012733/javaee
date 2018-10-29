package com.my.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.utils.GenerateCheckCodeUtils;
import com.utils.GenerateCheckCodeUtils.CallBack;
import com.utils.LogUtils;

public class GenerateCheckCodeServlet extends HttpServlet
{
    public static final String CHECK_CODE = "checkCode";
    
    private static final long serialVersionUID = -6721472665784197425L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.setCharacterEncoding("utf-8");
        
        try
        {
            OutputStream output = resp.getOutputStream(); // 获得可以向客户端返回图片的输出流
            
            // 创建验证码图片并返回图片上的字符串
            GenerateCheckCodeUtils.createAndSend(new CallBack()
            {
                @Override
                public void doSomething(String checkCode)
                {
                    LogUtils.debug("checkCode:"+checkCode);
                    req.getSession().setAttribute(CHECK_CODE, checkCode);
                }
                
            }, output);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            // 设置 请求失败的 状态 及 原因
            resp.setStatus(400);
        }
        
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        this.doGet(req, resp);
    }
    
}
