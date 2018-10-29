package com.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bean.MyClass;
import com.dao.IMyClassDao;
import com.utils.HibernateUtils;

@Repository
public class MyClassDao implements IMyClassDao
{
    
    @Autowired
    private HibernateUtils hibernateUtils;
    
    @Autowired
    private SessionFactory sf;
    
    @Override
    public List<MyClass> findMyClasses()
    {
        // String sql = "select * from t_class c LEFT JOIN t_student s ON
        // c.pk_class_id = s.fk_class_id where 1 = 1 order by class_name desc";
        String sql = "select * from t_class  where 1 = 1 order by class_name desc";
        List<MyClass> myClassList = hibernateUtils.findEntits(sql, null, MyClass.class);
        
        return myClassList;
    }
    
    @Override
    public MyClass findMyClassById(String classId)
    {
        return sf.getCurrentSession().get(MyClass.class, classId);
    }
    
}
