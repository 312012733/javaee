package com.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bean.MyClass;
import com.utils.HibernateUtils;

// @Component
@Repository
public class MyClassDao implements IMyClassDao
{
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private HibernateUtils hibernateUtils;
    
    @Override
    public List<MyClass> findAllClasses()
    {
        return hibernateUtils.findList(MyClass.class);
    }
    
    @Override
    public MyClass findClassById(String classId)
    {
        return sessionFactory.getCurrentSession().find(MyClass.class, classId);
    }
}
