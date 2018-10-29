package com.my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.my.bean.Role;
import com.my.bean.User;
import com.my.vo.ResponseEntity;
import com.utils.JDBCUtils;

public class StudentAddServlet extends HttpServlet
{
    private static final long serialVersionUID = -6721472665784197425L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        
        PrintWriter out = resp.getWriter();
        
        ResponseEntity respEntity = new ResponseEntity();
        
        Connection connection = null;
        
        try
        {
            // 获取表单参数
            String stuName = req.getParameter("stuName");
            String stuAge = req.getParameter("stuAge");
            String stuGender = req.getParameter("stuGender");
            String stuClassId = req.getParameter("stuClass");
            stuClassId = (null == stuClassId || stuClassId.length() < 0) ? null : stuClassId;
            
            Integer age = Integer.parseInt(stuAge);
            Boolean gender = Boolean.parseBoolean(stuGender);
            
            // 业务的处理
            connection = JDBCUtils.openConnection();
            
            String sql = "INSERT INTO t_student "
                    + "(pk_student_id , create_time , last_modify_time , student_name , age, gender , fk_class_id  )"
                    + " values (?,?,?,?,?,?,?)";
            
            List<Object> params = new ArrayList<>();
            
            params.add(UUID.randomUUID().toString());
            params.add(System.currentTimeMillis());
            params.add(System.currentTimeMillis());
            params.add(stuName);
            params.add(age);
            params.add(gender);
            params.add(stuClassId);
            
            JDBCUtils.execute(connection, sql, params);
            
            connection.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            try
            {
                JDBCUtils.rollback(connection);
            }
            catch (Exception e1)
            {
                e1.printStackTrace();
            }
            
            // 设置 请求失败的 状态 及 原因
            resp.setStatus(400);
            respEntity.setErrorMsg(e.getMessage());
        }
        finally
        {
            try
            {
                JDBCUtils.close(connection);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
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
