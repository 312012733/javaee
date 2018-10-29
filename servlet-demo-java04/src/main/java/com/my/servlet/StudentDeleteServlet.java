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

public class StudentDeleteServlet extends HttpServlet
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
            // 业务的处理
            String stuId = req.getParameter("stuId");
            
            // 1.查询
            connection = JDBCUtils.openConnection();
            deleteStudent(connection, stuId);
            
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
    
    /**
     * @param connection
     * @param stuId
     * @throws Exception
     * @throws SecurityException
     */
    private void deleteStudent(Connection connection, String stuId) throws Exception, SecurityException
    {
        Student stuDB = findStuById(connection, stuId);
        
        if (null == stuDB)
        {
            throw new SecurityException("delete student error. id is not found. id is " + stuId);
        }
        
        // 2.删除
        deleteStudentById(connection, stuId);
    }
    
    private void deleteStudentById(Connection connection, String stuId) throws Exception
    {
        String sql = "DELETE s FROM t_student s where s.pk_student_id = ?";
        
        List<Object> params = new ArrayList<>();
        params.add(stuId);
        
        JDBCUtils.execute(connection, sql, params);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        this.doGet(req, resp);
    }
    
    private Student findStuById(Connection connection, String stuId) throws Exception, SecurityException
    {
        String sql = "SELECT * FROM t_student s where s.pk_student_id = ?";
        List<Object> params = new ArrayList<>();
        params.add(stuId);
        
        Student stuDB = JDBCUtils.findEntity(connection, sql, params, Student.class);
        
        return stuDB;
    }
    
}
