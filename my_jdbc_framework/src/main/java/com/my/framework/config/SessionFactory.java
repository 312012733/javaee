package com.my.framework.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class SessionFactory
{
    static final String JDBC_DRIVER = "driver";
    static final String JDBC_URL = "url";
    static final String JDBC_USER_NAME = "username";
    static final String JDBC_PASSWORD = "password";
    
    private List<SqlMapper> sqlMapperList;
    
    private String diver;
    private String url;
    private String username;
    private String password;
    
    public SessionFactory(List<SqlMapper> sqlMapperList)
    {
        this.sqlMapperList = sqlMapperList;
    }
    
    public Session openSesson()
    {
        return openSesson(true);
    }
    
    public Session openSesson(boolean autoCommit)
    {
        try
        {
            
            Class.forName("com.mysql.jdbc.Driver");
            
            // 获取连接
            Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
            connection.setAutoCommit(autoCommit);
            
            Session session = new Session(sqlMapperList);
            session.setConnection(connection);
            
            return session;
        }
        catch (Exception e)
        {
            throw new SecurityException(e);
        }
    }
    
    String getDiver()
    {
        return diver;
    }
    
    void setDiver(String diver)
    {
        this.diver = diver;
    }
    
    String getUrl()
    {
        return url;
    }
    
    void setUrl(String url)
    {
        this.url = url;
    }
    
    String getUsername()
    {
        return username;
    }
    
    void setUsername(String username)
    {
        this.username = username;
    }
    
    String getPassword()
    {
        return password;
    }
    
    void setPassword(String password)
    {
        this.password = password;
    }
    
}
