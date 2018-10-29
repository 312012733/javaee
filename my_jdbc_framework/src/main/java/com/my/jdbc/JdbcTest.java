package com.my.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.my.bean.User;

public class JdbcTest
{
    
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
    public static void main(String[] args) throws Exception
    {
        // 加载驱动
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
        }
        
        // 获取连接
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        
        connection.setAutoCommit(false);
        
        // 申明语句
        String sql_insert1 = "INSERT INTO t_user (pk_id,user_name) VALUES ('u1','u1')";
        String sql_insert2 = "INSERT INTO t_role (pk_role_id,name) VALUES ('r1','r1')";
//        String sql_update = "UPDATE t_user u set user_name = ? ,pass_word = ? , age = ?, sex = ? where u.id = ?";
//        String sql_delete = "DELETE u1 from t_user u1 where u1.id = ?";
//        String sql_find = "SELECT * from t_user u1 where u1.id = ?";
        
//        PreparedStatement statement = connection.prepareStatement(sql_insert1);
        PreparedStatement statement = connection.prepareStatement(sql_insert2);
        
        // 执行
        // statement.execute();
        // statement.executeUpdate();
        // statement.executeQuery();
        
        
        // 结果集
        statement.execute();
        
        
    }
    
}
