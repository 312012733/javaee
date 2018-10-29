package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bean.MyClass;
import com.dao.IMyClassDao;

@Service
public class MyClassServiceImpl implements IMyClassService
{
    
    @Autowired
    private IMyClassDao classDao;
    
    @Transactional(readOnly = true)
    @Override
    public List<MyClass> findAllClasses()
    {
        return classDao.findAllClasses();
    }
    
}
