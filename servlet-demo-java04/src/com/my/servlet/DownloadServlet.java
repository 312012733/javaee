package com.my.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadServlet extends HttpServlet
{
    private static final long serialVersionUID = -6721472665784197425L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.setCharacterEncoding("utf-8");
        
        OutputStream out = resp.getOutputStream();
        
        try
        {
            String fileName = req.getParameter("fileName");
            
            // 设置类容保存的方式 Content-Disposition
            resp.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            
            // 读文件
            InputStream in = req.getServletContext().getResourceAsStream("upload" + File.separator + fileName);
            
            // 写文件
            int len = -1;
            
            byte[] buf = new byte[1024];
            
            while ((len = in.read(buf)) > 0)
            {
                out.write(buf, 0, len);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            // 设置 请求失败的 状态 及 原因
            resp.setStatus(400);
            // respEntity.setErrorMsg(e.getMessage());
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        this.doGet(req, resp);
    }
    
}
