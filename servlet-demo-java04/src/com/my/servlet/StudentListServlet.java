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
import com.my.vo.Page;
import com.my.vo.ResponseEntity;
import com.utils.JDBCUtils;

public class StudentListServlet extends HttpServlet
{
    private static final long serialVersionUID = -6721472665784197425L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
//        System.out.println(this.getClass().getName()+"-----doGet-------");
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        
        PrintWriter out = resp.getWriter();
        
        ResponseEntity respEntity = new ResponseEntity();
        Connection connection = null;
        
        try
        {
            // 业务的处理
            String curPageParam = req.getParameter("curPage");
            String pageSizeParam = req.getParameter("pageSize");
            
            Integer curPage = Integer.parseInt(curPageParam);
            Integer pageSize = Integer.parseInt(pageSizeParam);
            
            connection = JDBCUtils.openConnection();
            
            Page<Student> page = new Page<>(curPage, pageSize);
            
            // 1.获取分页的列表
            List<Student> stuList = findPageList(page, connection);
            
            // 2.获取总条数
            Long maxCount = fndPageMaxCount(connection);
            
            page.setPageList(stuList);
            page.setMaxCount(maxCount);
            
            respEntity.setContent(page);
            
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
     * @return
     * @throws Exception
     */
    private Long fndPageMaxCount(Connection connection) throws Exception
    {
        String sql = "SELECT COUNT(*) FROM t_student";
        Long maxCount = (Long) JDBCUtils.uniquResult(connection, sql, null);
        return maxCount;
    }
    
    /**
     * @param curPage
     * @param pageSize
     * @param connection
     * @return
     * @throws Exception
     */
    private List<Student> findPageList(Page<Student> page, Connection connection) throws Exception
    {
        String sql = "SELECT * FROM t_student s ORDER BY s.create_time DESC limit ? , ?";
        
        List<Object> params = new ArrayList<>();
        
        params.add(page.getOffset());
        params.add(page.getPageSize());
        
        List<Student> stuList = JDBCUtils.findList(connection, sql, params, Student.class);
        return stuList;
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        this.doGet(req, resp);
    }
    
}
