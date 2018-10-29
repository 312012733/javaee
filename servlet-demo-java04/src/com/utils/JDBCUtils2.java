package com.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/*
 * TODO: 应该在服务器启动的时候 去初始化 jdbc 相关配置
 * */
public class JDBCUtils2
{
    private static final String JDBC_URL = "url";
    private static final String JDBC_DRIVER = "jdbc.driverClassName";
    private static final String JDBC_USER_NAME = "jdbc.username";
    private static final String JDBC_PASSWORD = "jdbc.password";
    
    private static String url;
    private static String diverClassName;
    private static String username;
    private static String password;
    
    static
    {
        try
        {
            // 加载jdbc.properties文件
            Properties prop = new Properties();
            prop.load(JDBCUtils2.class.getClassLoader().getResourceAsStream("jdbc.properties"));
            
            url = prop.getProperty(JDBC_URL);
            diverClassName = prop.getProperty(JDBC_DRIVER);
            username = prop.getProperty(JDBC_USER_NAME);
            password = prop.getProperty(JDBC_PASSWORD);
            
            // 1.加载驱动
            Class.forName(diverClassName);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    // 2.获取连接
    public static Connection openConnection() throws Exception
    {
        Connection con = DriverManager.getConnection(url, username, password);
        
        con.setAutoCommit(false);
        
        return con;
    }
    
    // 3.获取语句
    private static PreparedStatement getPreparedStatement(Connection con, String sql, List<Object> params)
            throws Exception
    {
        PreparedStatement statment = con.prepareStatement(sql);
        
        if (null != params && params.size() > 0)
        {
            int parameterIndex = 1;
            
            for (Object param : params)
            {
                statment.setObject(parameterIndex++, param);
            }
        }
        
        return statment;
    }
    
    // 4.执行语句
    private static <T> List<T> execute(Connection con, String sql, List<Object> params, Class<T> entityType)
            throws Exception
    {
        PreparedStatement statment = getPreparedStatement(con, sql, params);
        
        boolean flag = statment.execute();
        
        if (flag)
        {
            // 5.获取结果集
            
            List<T> resultList = buildResultSet(statment.getResultSet(), entityType);
            
            return resultList;
        }
        
        return null;
        
    }
    
    private static <T> List<T> buildResultSet(ResultSet resultSet, Class<T> entityType) throws Exception
    {
        List<T> resultList = new ArrayList<>();
        
        while (resultSet.next())
        {
            Field[] fields = entityType.getDeclaredFields();
            
            T entity = entityType.newInstance();
            
            for (Field field : fields)
            {
                String fieldName = field.getName();
                
                try
                {
                    String columName = MappingUtils.getColumNameByFieldName(fieldName, entityType);
                    
                    String filedTypeName = field.getType().getName();
                    
                    Object columValue = null;
                    
                    if (Boolean.class.getName().equals(filedTypeName) || boolean.class.getName().equals(filedTypeName))
                    {
                        columValue = resultSet.getBoolean(columName);
                    }
                    else
                    {
                        columValue = resultSet.getObject(columName);
                    }
                    
                    // field.setAccessible(true);
                    // field.set(entity, columValue);
                    
                    // 给entity对应的java 属性赋值。。。。
                    PropertyDescriptor pd = new PropertyDescriptor(fieldName, entityType);
                    // pd.getReadMethod()//get方法
                    Method setMethod = pd.getWriteMethod();// set方法
                    setMethod.invoke(entity, columValue);// 执行set方法
                }
                catch (Exception e)
                {
                    LogUtils.error("field:" + fieldName + " is error. " + e);
                }
                
            }
            
            resultList.add(entity);
            
        }
        
        return resultList;
    }
    
    public static <T> List<T> findList(Connection con, String sql, List<Object> params, Class<T> entityType)
            throws Exception
    {
        return execute(con, sql, params, entityType);
    }
    
    public static <T> T findEntity(Connection con, String sql, List<Object> params, Class<T> entityType)
            throws Exception
    {
        List<T> resultList = execute(con, sql, params, entityType);
        
        if (null == resultList || resultList.size() <= 0)
        {
            return null;
        }
        else if (resultList.size() == 1)
        {
            return resultList.get(0);
        }
        
        throw new SecurityException("result is > 1");
    }
    
    public static Object uniquResult(Connection con, String sql, List<Object> params) throws Exception
    {
        PreparedStatement statment = getPreparedStatement(con, sql, params);
        
        ResultSet resultSet = statment.executeQuery();
        
        if (null == resultSet)
        {
            throw new SecurityException("sql is not query statement. ");
        }
        
        return resultSet.next() ? resultSet.getObject(1) : null;
    }
    
    public static void execute(Connection con, String sql, List<Object> params) throws Exception
    {
        execute(con, sql, params, null);
    }
    
}
