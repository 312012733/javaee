package com.service;

import com.bean.StudentIdCard;

public interface IStuIdCardService
{
    void save(StudentIdCard studentIdCard);
    
    void update(StudentIdCard studentIdCard);
    
    void delete(StudentIdCard idCard);
    
}
