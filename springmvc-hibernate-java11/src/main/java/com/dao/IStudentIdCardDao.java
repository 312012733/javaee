package com.dao;

import com.bean.StudentIdCard;

public interface IStudentIdCardDao
{
    
    void save(StudentIdCard studentIdCard);
    
    void update(StudentIdCard studentIdCard);
    
    void delete(StudentIdCard idCardId);
    
}
