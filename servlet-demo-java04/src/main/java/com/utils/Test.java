package com.utils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.my.bean.Student;

public class Test
{
    
    // public static void main(String[] args) throws Exception
    // {
    //
    // String sql = "INSERT INTO t_student "
    // + "(pk_student_id , create_time , last_modify_time , student_name , age,
    // gender , fk_class_id )"
    // + " values (?,?,?,?,?,?,?)";
    //
    // Connection con = JDBCUtils2.openConnection();
    //
    // List<Object> params = new ArrayList<>();
    //
    // params.add("9527");
    // params.add(System.currentTimeMillis());
    // params.add(System.currentTimeMillis());
    // params.add("刘德华");
    // params.add(28);
    // params.add(true);
    // params.add(null);
    //
    // JDBCUtils2.execute(con , sql, params);
    //
    // con.commit();
    // }
    // public static void main(String[] args) throws Exception
    // {
    //
    // Connection connection = JDBCUtils.openConnection();
    //
    //// String sql = "SELECT * FROM t_student s where s.pk_student_id = ?";
    // String sql = "SELECT * FROM t_student s ";
    //
    // List<Object> params = new ArrayList<>();
    //// params.add("1");
    //
    // List<Student> stuList = JDBCUtils2.findList(connection, sql,
    // params,Student.class);
    //
    // for (Student student : stuList)
    // {
    //
    // System.out.println(student);
    // }
    //
    // }
    
    // public static void main(String[] args) throws Exception
    // {
    //
    // Connection connection = JDBCUtils.openConnection();
    //
    // String sql = "SELECT * FROM t_student s where s.pk_student_id = ?";
    //
    // List<Object> params = new ArrayList<>();
    // params.add("9527");
    //
    // Student stu = JDBCUtils2.findEntity(connection, sql, params,
    // Student.class);
    //
    // System.out.println(stu);
    // }
    
    // public static void main(String[] args) throws Exception
    // {
    //
    // Connection connection = JDBCUtils.openConnection();
    //
    // String sql = "SELECT COUNT(*) FROM t_student";
    // Long maxCount = (Long) JDBCUtils.uniquResult(connection, sql, null);
    // System.out.println(maxCount);
    // }
    
    public static void main(String[] args)
    {
        // String str = JDBCUtils.JDBC_URL ;
        // JDBCUtils.JDBC_URL = "abc";
        
//        System.out.println(str);
    }
}
