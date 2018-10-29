package com.my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.my.bean.Role;
import com.my.bean.Student;
import com.my.bean.User;
import com.my.vo.ResponseEntity;
import com.utils.JDBCUtils;

public class StudentFindServlet extends HttpServlet
{
    private static final long serialVersionUID = -6721472665784197425L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        
        PrintWriter out = resp.getWriter();
        
        ResponseEntity respEntity = new ResponseEntity();
        
        try
        {
            // 业务的处理
            
            String stuId = req.getParameter("stuId");
            
            Connection connection = JDBCUtils.openConnection();
            
            String sql = "SELECT * FROM t_student s where s.pk_student_id = ?";
            
            List<Object> params = new ArrayList<>();
            params.add(stuId);
            
            Student stu = JDBCUtils.findEntity(connection, sql, params, Student.class);
            
            respEntity.setContent(stu);
            
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
    
    public static void main(String[] args)
    {
        // 1.java Obj -------------》 json Str
        
        User user = new User("9527", "admin", "1234");
        
        Role role = new Role();
        role.setName("管理员");
        
        user.setRole(role);
        
        String userJsonStr = JSONObject.toJSONString(user);
        
        System.out.println(userJsonStr);
        
        // 2. json str ----------------> java obj
        
        String json = "{\"id\":\"9527\",\"password\":\"1234\",\"role\":{\"name\":\"管理员\"},\"username\":\"admin\"}";
        
        User newUser = JSONObject.parseObject(json, User.class);
        
        System.out.println(newUser);
        
    }
    
}
