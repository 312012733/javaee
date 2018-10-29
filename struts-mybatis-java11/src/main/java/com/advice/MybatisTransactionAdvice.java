package com.advice;

import java.lang.reflect.Method;

import org.apache.ibatis.session.SqlSession;

import com.utils.MybatisUtils;

public class MybatisTransactionAdvice extends BaseAdvice
{
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        
        SqlSession session = MybatisUtils.getCurrentSession();
        
        try
        {
            // 执行 service的方法
            Object result = method.invoke(getSource(), args);
            
            session.commit();
            
            // System.out.println(
            // "【debug】" + getSource().getClass().getName() + "." +
            // method.getName() + " transaction is commit ");
            
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            if (null != session)
            {
                session.rollback();
            }
            
            throw (e.getCause() == null ? e : e.getCause());
        }
        finally
        {
            // MybatisUtils.close(session);
        }
        
    }
    
}