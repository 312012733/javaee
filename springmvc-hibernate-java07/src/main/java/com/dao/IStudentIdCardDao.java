package com.dao;

import com.bean.StudentIdCard;

public interface IStudentIdCardDao
{
    
    StudentIdCard findStuIdCardById(String idcardId);
    
    // StudentIdCard findStuIdCardByStuId(String stuId);
    
    void addStudentIdCard(StudentIdCard idCard);
    
    void deleteStudentIdCard(StudentIdCard idCard);
    
}
