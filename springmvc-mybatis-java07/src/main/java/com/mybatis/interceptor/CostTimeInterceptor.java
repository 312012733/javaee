package com.mybatis.interceptor;

import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

@Intercepts(
{ @Signature(type = Executor.class, method = "update", args =
        { MappedStatement.class, Object.class }),
    @Signature(type = Executor.class, method = "query", args =
        { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
        @Signature(type = Executor.class, method = "query", args =
        { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class,
                BoundSql.class }), })
public class CostTimeInterceptor implements Interceptor
{
    private static final Logger LOG = Logger.getLogger(CostTimeInterceptor.class);
    
    private Properties properties;
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable
    {
        Long startTime = System.currentTimeMillis();
        
        Object result = invocation.proceed();//TODO
        
        Double costTime = (System.currentTimeMillis() - startTime) * 1.0;
        
        String unit = properties.getProperty("unit", "mm");
        
        if ("s".equalsIgnoreCase(unit))
        {
            costTime = costTime / 1000.0;
        }
        
        LOG.info("---------------------" + invocation.getTarget().getClass().getName() + "."
                + invocation.getMethod().getName() + " costTime:" + costTime + " " + unit);
        
        return result;
    }
    
    @Override
    public Object plugin(Object target)
    {
        return Plugin.wrap(target, this);
    }
    
    @Override
    public void setProperties(Properties properties)
    {
        this.properties = properties;
    }
    
}
