package com.utils;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.CacheMode;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vo.Order;
import com.vo.Page;

@Component
public class HibernateUtils
{
    @Autowired
    private SessionFactory sf;
    
    public static interface PredicateCallBack
    {
        Predicate toPredicate(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<?> root);
    }
    
    private <T> TypedQuery<T> buildQuery(Class<T> entity, Order order, PredicateCallBack predicateCallBack)
    {
        
        CriteriaBuilder cb = sf.getCurrentSession().getCriteriaBuilder();
        
        CriteriaQuery<T> cq = cb.createQuery(entity);
        
        Root<T> root = cq.from(entity);
        
        Predicate predicate = predicateCallBack.toPredicate(cb, cq, root);
        
        cq.select(root).where(predicate);
        
        if (order != null)
        {
            List<javax.persistence.criteria.Order> orderList = new ArrayList<>();
            
            for (String field : order.getFields())
            {
                javax.persistence.criteria.Order o = order.isDesc() ? cb.desc(root.get(field))
                        : cb.asc(root.get(field));
                
                orderList.add(o);
            }
            
            if (!orderList.isEmpty())
            {
                cq.orderBy(orderList.toArray(new javax.persistence.criteria.Order[orderList.size()]));
            }
        }
        
        TypedQuery<T> query = sf.getCurrentSession().createQuery(cq).setCacheable(true).setCacheRegion("sampleCache1")
                .setCacheMode(CacheMode.NORMAL);
        
        return query;
        
    }
    
    private <T> TypedQuery<T> buildQuery(Class<T> entity, PredicateCallBack predicateCallBack)
    {
        return buildQuery(entity, null, predicateCallBack);
    }
    
    public <T> List<T> findList(Class<T> entity, Order order, PredicateCallBack predicateCallBack)
    {
        TypedQuery<T> query = buildQuery(entity, order, predicateCallBack);
        
        return query.getResultList();
    }
    
    public <T> List<T> findList(Class<T> entity)
    {
        return findList(entity, null);
    }
    
    public <T> List<T> findList(Class<T> entity, Order order)
    {
        PredicateCallBack predicateCallBack = new PredicateCallBack()
        {
            @Override
            public Predicate toPredicate(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<?> root)
            {
                return cb.conjunction();
            }
        };
        return findList(entity, order, predicateCallBack);
    }
    
    public <T> T find(Class<T> entity, PredicateCallBack predicateCallBack)
    {
        try
        {
            TypedQuery<T> query = buildQuery(entity, predicateCallBack);
            
            return query.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }
    
    public <T> Page<T> findPage(Class<T> entity, Page<T> page, PredicateCallBack predicateCallBack)
    {
        return findPage(entity, page, null, predicateCallBack);
    }
    
    public <T> Page<T> findPage(Class<T> entity, Page<T> page, Order order, PredicateCallBack predicateCallBack)
    {
        TypedQuery<T> query = buildQuery(entity, order, predicateCallBack);
        
        List<T> resultList = query.setFirstResult(page.getOffset()).setMaxResults(page.getPageSize()).getResultList();
        
        Long count = getMaxCount(entity, predicateCallBack);
        
        page.setPageList(resultList);
        page.setMaxCount(count);
        return page;
    }
    
    private <T> Long getMaxCount(Class<T> entity, PredicateCallBack predicateCallBack)
    {
        
        CriteriaBuilder cb = sf.getCurrentSession().getCriteriaBuilder();
        
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        
        Root<T> root = cq.from(entity);
        
        Predicate predicate = predicateCallBack.toPredicate(cb, cq, root);
        
        cq.select(cb.count(root)).where(predicate);
        
        TypedQuery<Long> query = sf.getCurrentSession().createQuery(cq).setCacheable(true)
                .setCacheRegion("sampleCache1").setCacheMode(CacheMode.NORMAL);
        
        return query.getSingleResult();
    }
    
}
