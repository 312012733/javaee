package com.my.framework.config;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.my.framework.config.SqlMapper.Delete;
import com.my.framework.config.SqlMapper.Insert;
import com.my.framework.config.SqlMapper.ResultMap;
import com.my.framework.config.SqlMapper.ResultMap.Result;
import com.my.framework.config.SqlMapper.Select;
import com.my.framework.config.SqlMapper.Update;
import com.mysql.jdbc.StringUtils;

public class Session
{
    private Connection connection;
    
    private List<SqlMapper> sqlMapperList;
    
    Session(List<SqlMapper> sqlMapperList)
    {
        this.sqlMapperList = sqlMapperList;
    }
    
    public void commit() throws SQLException
    {
        if (null != connection)
        {
            connection.commit();
        }
    }
    
    public void rollback() throws SQLException
    {
        if (null != connection)
        {
            connection.rollback();
        }
        
    }
    
    public void close() throws SQLException
    {
        if (null != connection)
        {
            connection.close();
        }
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> classBean)// 参数是 接口 的 class
    {
        // 处理 代理对象的业务逻辑
        MyInvocationHandler handler = new MyInvocationHandler();
        
        // 生成 代理 对象
        T objProxy = (T) Proxy.newProxyInstance(Session.class.getClassLoader(), new Class[]
        { classBean }, handler);
        
        return objProxy;
    }
    
    private class MyInvocationHandler implements InvocationHandler
    {
        
        @SuppressWarnings("rawtypes")
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
        {
            
            String namespace = proxy.getClass().getInterfaces()[0].getName();
            
            SqlMapper sqlMapper = findSqlMpper(namespace);
            
            String id = method.getName();// 配置文件里面 select delete update insert
                                         // 标签的id
            
            Class returnType = method.getReturnType();// 用来判断结果集
            
            Select select = findSelect(id, sqlMapper);
            
            if (null != select)
            {
                return executSelect(select, args, returnType, sqlMapper);
            }
            
            Insert insert = findInsert(id, sqlMapper);
            
            if (null != insert)
            {
                return execute(insert.getSql(), args);
            }
            
            Delete delete = findDelete(id, sqlMapper);
            
            if (null != delete)
            {
                return execute(delete.getSql(), args);
            }
            
            Update update = findUpdate(id, sqlMapper);
            
            if (null != update)
            {
                return execute(update.getSql(), args);
            }
            
            throw new SecurityException(id + " is not mapping in " + namespace);
        }
        
    }
    
    @SuppressWarnings("rawtypes")
    private Object executSelect(Select select, Object[] args, Class returnType, SqlMapper sqlMapper) throws Exception
    {
        
        // 构造PreparedStatement
        PreparedStatement statement = buildPreparedStatement(select.getSql(), args);
        
        // 执行sql
        ResultSet rset = statement.executeQuery();
        
        // 构造resultMap 和 resultType
        String resultTypeName = select.getResultType();
        ResultMap resultMap = null;
        Class entityType = null;
        
        if (StringUtils.isNullOrEmpty(resultTypeName))
        {// 取的是resultMap
            resultMap = findResultMap(select.getResultMap(), sqlMapper.getResultMapList());
            entityType = Class.forName(resultMap.getType());
        }
        else
        {// 取的是resultType
            entityType = Class.forName(resultTypeName);
        }
        
        return buildResult(returnType, rset, resultMap, entityType);
        
    }
    
    private Object execute(String sql, Object[] args) throws Exception
    {
        // 构造PreparedStatement
        PreparedStatement statement = buildPreparedStatement(sql, args);
        
        // 执行sql
        return statement.execute();
    }
    
    @SuppressWarnings(
    { "rawtypes", "unchecked" })
    private Object buildResult(Class returnType, ResultSet rset, ResultMap resultMap, Class entityType)
            throws SQLException, InstantiationException, IllegalAccessException
    {
        List list = new ArrayList<>();
        
        // 结果集映射成javabean
        while (rset.next())
        {
            Field[] fields = entityType.getDeclaredFields();
            
            // 实例化 结果对象
            Object obj = entityType.newInstance();
            
            for (Field f : fields)
            {
                f.setAccessible(true);
                Class<?> type = f.getType();
                
                String cloumName = f.getName();
                
                if (null != resultMap)
                {
                    // 构造数据库表的列名
                    cloumName = buildColumByResultMap(f.getName(), resultMap);
                }
                
                try
                {
                    // 给属性 赋值
                    if (type == Boolean.class || type == boolean.class)
                    {
                        f.set(obj, rset.getBoolean(cloumName));
                    }
                    else
                    {
                        f.set(obj, rset.getObject(cloumName));
                    }
                }
                catch (Exception e)
                {
                    System.out.println("【error】 " + e);
                }
            }
            
            list.add(obj);
        }
        
        // 判断方法返回类型
        if (returnType == entityType)
        {
            if (list.size() > 1)
            {
                throw new SecurityException("the resut length > 1.");
            }
            
            return list.isEmpty() ? null : list.get(0);
        }
        else if (returnType == List.class)
        {
            return list;
        }
        
        throw new SecurityException("没发开这个类型。。。" + returnType.getName());
    }
    
    private String buildColumByResultMap(String fieldName, ResultMap resultMap)
    {
        
        // 匹配id
        if (null != resultMap.getPkID() && resultMap.getPkID().getProperty().equals(fieldName))
        {
            return resultMap.getPkID().getColumn();
        }
        
        if (null != resultMap.getResultList())
        {
            
            // 匹配result
            for (Result result : resultMap.getResultList())
            {
                if (result.getProperty().equals(fieldName))
                {
                    return result.getColumn();
                }
            }
        }
        
        return fieldName;
    }
    
    private PreparedStatement buildPreparedStatement(String sql, Object[] args) throws SQLException
    {
        // 动态参数处理
        Map<String, Object> dynParamMap = DynParmParseUtils.buildDynParamMap(sql, args);
        
        String newSql = buildSql(sql);
        
        PreparedStatement statement = connection.prepareStatement(newSql);
        
        if (null != dynParamMap)
        {
            int i = 0;
            
            // 设置参数
            for (Entry<String, Object> entry : dynParamMap.entrySet())
            {
                statement.setObject(++i, entry.getValue());
            }
        }
        
        return statement;
    }
    
    private String buildSql(String sql)
    {
        return sql.replaceAll("#\\{[^\\{\\}]+\\}", "?");
    }
    
    private Select findSelect(String id, SqlMapper sqlMapper)
    {
        if (null == sqlMapper.getSelectList())
        {
            return null;
        }
        
        for (Select select : sqlMapper.getSelectList())
        {
            if (select.getId().equals(id))
            {
                return select;
            }
        }
        
        return null;
    }
    
    private Insert findInsert(String id, SqlMapper sqlMapper)
    {
        if (null == sqlMapper.getInsertList())
        {
            return null;
        }
        
        for (Insert select : sqlMapper.getInsertList())
        {
            if (select.getId().equals(id))
            {
                return select;
            }
        }
        
        return null;
    }
    
    private Delete findDelete(String id, SqlMapper sqlMapper)
    {
        if (null == sqlMapper.getDeleteList())
        {
            return null;
        }
        
        for (Delete select : sqlMapper.getDeleteList())
        {
            if (select.getId().equals(id))
            {
                return select;
            }
        }
        
        return null;
    }
    
    private Update findUpdate(String id, SqlMapper sqlMapper)
    {
        if (null == sqlMapper.getUpdateList())
        {
            return null;
        }
        
        for (Update select : sqlMapper.getUpdateList())
        {
            if (select.getId().equals(id))
            {
                return select;
            }
        }
        
        return null;
    }
    
    private SqlMapper findSqlMpper(String namespace)
    {
        if (null == sqlMapperList)
        {
            throw new SecurityException("findSqlMpper is not found. sqlMapperList is null. ");
        }
        
        for (SqlMapper sqlMapper : sqlMapperList)
        {
            if (sqlMapper.getNamespace().equals(namespace))
            {
                return sqlMapper;
            }
        }
        
        throw new SecurityException("findSqlMpper is not found. namespace is " + namespace);
    }
    
    private ResultMap findResultMap(String id, List<ResultMap> resultMapList)
    {
        if (StringUtils.isNullOrEmpty(id))
        {
            throw new SecurityException("findResultMap error. id is null. ");
        }
        
        for (ResultMap resultMap : resultMapList)
        {
            if (id.equals(resultMap.getId()))
            {
                return resultMap;
            }
        }
        
        throw new SecurityException("findResultMap error. resultMap is not found . id is " + id);
    }
    
    Connection getConnection()
    {
        return connection;
    }
    
    void setConnection(Connection connection)
    {
        this.connection = connection;
    }
    
}
