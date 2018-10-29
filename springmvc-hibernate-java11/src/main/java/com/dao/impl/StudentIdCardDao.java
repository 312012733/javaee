package com.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bean.StudentIdCard;
import com.dao.IStudentIdCardDao;

@Repository
public class StudentIdCardDao implements IStudentIdCardDao
{
    @Autowired
    private SessionFactory sf;
    
    // @Autowired
    // private HibernateUtils hibernateUtils;
    
    @Override
    public void save(StudentIdCard studentIdCard)
    {
        sf.getCurrentSession().save(studentIdCard);
        
    }
    
    @Override
    public void update(StudentIdCard studentIdCard)
    {
        sf.getCurrentSession().update(studentIdCard);
        
    }
    
    @Override
    public void delete(StudentIdCard idCard)
    {
        sf.getCurrentSession().delete(idCard);
    }
    
}
