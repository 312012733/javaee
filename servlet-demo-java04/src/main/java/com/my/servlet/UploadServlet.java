package com.my.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.my.vo.ResponseEntity;

public class UploadServlet extends HttpServlet
{
    private static final long serialVersionUID = -6721472665784197425L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        // resp.setContentType("application/json;charset=utf-8");
        // resp.setContentType("text/html;charset=utf-8");
        
        PrintWriter out = resp.getWriter();
        
        ResponseEntity respEntity = new ResponseEntity();
        
        try
        {
            
            String uploadDir = req.getServletContext().getRealPath("upload");
            
            createDir(uploadDir);
            
            // 获取参数
            // String stuName = req.getParameter("stuName");
            
            // 获取文件
            for (Part part : req.getParts())
            {
                String fileName = getFileName(part);
                
                if (null == fileName || fileName.length() <= 0)
                {
                    continue;
                }
                
                // 保存文件
                // saveFile(dir, fileName, part);
                part.write(uploadDir + File.separator + fileName);
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            // 设置 请求失败的 状态 及 原因
            resp.setStatus(400);
            respEntity.setErrorMsg(e.getMessage());
        }
        
        // 通用的响应结果
        // String result = JSONObject.toJSONString(respEntity);
        // out.write(result);
        
        out.write("<html> <head> <meta charset=\"utf-8\"></head><body><h1>上传成功</h1></body></html>");
        
        // 重定向
        // resp.sendRedirect("/test/success.html");
        // resp.setStatus(302);
        // resp.setHeader("Location", "/test/success.html");
        // resp.setHeader("Location", "http://www.sina.com.cn");
        
        // String contextPath = req.getContextPath();
        
        // 请求转发
        // req.getRequestDispatcher("/success.html").forward(req, resp);
    }
    
    private File createDir(String uploadDir)
    {
        File dir = new File(uploadDir);
        
        if (dir.exists())
        {
            if (!dir.isDirectory())
            {
                throw new SecurityException("uploadDir is exists. but is not dir..   " + uploadDir);
            }
        }
        else if (!dir.mkdirs())
        {
            throw new SecurityException("mkdirs error..   " + uploadDir);
        }
        
        return dir;
    }
    
    // private static void saveFile(String dir, String fileName, Part part)
    // throws IOException
    // {
    // FileOutputStream fos = null;
    //
    // try
    // {
    // fos = new FileOutputStream(dir + File.separator + fileName);
    //
    // InputStream in = part.getInputStream();
    //
    // int len = -1;
    //
    // byte[] buf = new byte[1024];
    //
    // while ((len = in.read(buf)) > 0)
    // {
    // fos.write(buf, 0, len);
    // }
    // }
    // finally
    // {
    // if (null != fos)
    // {
    // fos.close();
    // }
    // }
    // }
    
    private String getFileName(Part part)
    {
        // form-data; name="head1"; filename="head11.png"
        String contentDisposition = part.getHeader("Content-Disposition");
        
        String fileNameKey = "filename=\"";
        int offset = contentDisposition.indexOf(fileNameKey);
        
        if (offset > 0)
        {
            String fileName = contentDisposition.substring(offset + fileNameKey.length(),
                    contentDisposition.length() - 1);
            return fileName;
        }
        
        return null;
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        this.doGet(req, resp);
    }
    
}
