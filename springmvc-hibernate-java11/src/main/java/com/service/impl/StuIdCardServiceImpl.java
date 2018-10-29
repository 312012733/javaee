package com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.StudentIdCard;
import com.dao.IStudentIdCardDao;
import com.service.IStuIdCardService;

@Service
public class StuIdCardServiceImpl implements IStuIdCardService
{
    @Autowired
    private IStudentIdCardDao idCardDao;
    
    @Override
    public void save(StudentIdCard studentIdCard)
    {
        
        idCardDao.save(studentIdCard);
    }
    
    @Override
    public void update(StudentIdCard studentIdCard)
    {
        idCardDao.update(studentIdCard);
    }
    
    @Override
    public void delete(StudentIdCard studentIdCard)
    {
        idCardDao.delete(studentIdCard);
    }
    
}
