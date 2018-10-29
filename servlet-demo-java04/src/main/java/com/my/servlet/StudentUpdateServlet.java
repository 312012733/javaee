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
import com.my.bean.Student;
import com.my.vo.ResponseEntity;
import com.utils.JDBCUtils;

public class StudentUpdateServlet extends HttpServlet
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
            
            String stuId = req.getParameter("stuId");
            String stuName = req.getParameter("stuName");
            String stuAge = req.getParameter("stuAge");
            // String stuGender = req.getParameter("stuGender");
            String stuClassId = req.getParameter("stuClass");
            stuClassId = (null == stuClassId || stuClassId.length() < 0) ? null : stuClassId;
            
            Integer age = Integer.parseInt(stuAge);
            // Boolean gender = Boolean.parseBoolean(stuGender);
            
            // 业务的处理
            connection = JDBCUtils.openConnection();
            
            // 1.查询l
            Student stuDB = findStuById(connection, stuId);
            
            if (null == stuDB)
            {
                throw new SecurityException("update student error. id is not found. id is " + stuId);
            }
            
            // 2.修改
            Student stuParam = new Student(stuDB.getId(), stuName, age, stuDB.getGender());
            
            updateStudent(connection, stuParam);
            
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
    
    private void updateStudent(Connection connection, Student stuParam) throws Exception
    {
        String sql = "UPDATE t_student s set " + "s.student_name  = ? , " + "s.age =  ?  , " + "s.last_modify_time = ? "
                + "where s.pk_student_id = ?";
        
        List<Object> params = new ArrayList<>();
        params.add(stuParam.getName());
        params.add(stuParam.getAge());
        params.add(System.currentTimeMillis());
        params.add(stuParam.getId());
        
        JDBCUtils.execute(connection, sql, params);
        
        connection.commit();
    }
    
    private Student findStuById(Connection connection, String stuId) throws Exception, SecurityException
    {
        String sql = "SELECT * FROM t_student s where s.pk_student_id = ?";
        List<Object> params = new ArrayList<>();
        params.add(stuId);
        
        Student stuDB = JDBCUtils.findEntity(connection, sql, params, Student.class);
        
        return stuDB;
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        this.doGet(req, resp);
    }
    
}
