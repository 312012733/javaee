package com.utils;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vo.Page;

//@org.springframework.context.annotation.Configuration
@Component
public class HibernateUtils
{
    private static final Logger LOG = Logger.getLogger(HibernateUtils.class);
    
    // @Bean
    // public SessionFactory buildSessionFactory()
    // {
    // Configuration config = new
    // Configuration().configure("hibernate.cfg.xml");
    // SessionFactory sf = config.buildSessionFactory();
    //
    // return sf;
    // }
    
    @Autowired
    private SessionFactory sf;
    
    public <T> Page<T> findByPage(String listSql, String countSql, Page<T> page, Map<String, Object> condition,
            Class<T> entityType)
    {
        List<T> pageList = findEntits(listSql, page, condition, entityType);
        
        BigInteger count = uniqueResult(countSql, condition, BigInteger.class);
        
        page.setContent(pageList);
        
        page.setTotalCount(count.longValue());
        
        return page;
    }
    
    public <T> List<T> findEntits(String sql, Map<String, Object> condition, Class<T> entityType)
    {
        return findEntits(sql, null, condition, entityType);
    }
    
    public <T> T findEntit(String sql, Map<String, Object> condition, Class<T> entityType)
    {
        
        List<T> list = findEntits(sql, condition, entityType);
        
        if (null == list)
        {
            return null;
        }
        
        if (list.size() > 1)
        {
            throw new SecurityException("result > 1");
        }
        
        return list.size() > 0 ? list.get(0) : null;
    }
    
    @SuppressWarnings("unchecked")
    public <T> T uniqueResult(String sql, Map<String, Object> condition, Class<T> resultType)
    {
        LOG.info(sql + " -- " + condition);
        
        Session session = sf.getCurrentSession();
        
        Query<?> query = session.createNativeQuery(sql);
        
        setParameter(condition, query);
        
        T result = (T) query.uniqueResult();
        
        return result;
    }
    
    private <T> List<T> findEntits(String sql, Page<T> page, Map<String, Object> condition, Class<T> entityType)
    {
        LOG.info(sql + " -- " + condition);
        
        Session session = sf.getCurrentSession();
        
        Query<T> query = session.createNativeQuery(sql, entityType);
        
        if (null != page)
        {
            query.setFirstResult(page.getOffset());
            query.setMaxResults(page.getPageSize());
        }
        
        setParameter(condition, query);
        
        List<T> listResult = query.getResultList();
        
        return listResult;
        
    }
    
    private <T> void setParameter(Map<String, Object> condition, Query<T> query)
    {
        if (null != condition)
        {
            for (Entry<String, Object> entry : condition.entrySet())
            {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
    }
    
}
