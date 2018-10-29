package com.service;

import com.bean.Student;
import com.vo.Page;
import com.vo.StudentDTO;

public interface IStudentService
{
    Page<Student> findStudentByPage(Page<Student> page, StudentDTO condition);
    
    StudentDTO findeStudentById(String stuId);
    
    void addStudent(StudentDTO stu);
    
    void updateStudent(StudentDTO stu);
    
    void deleteStudent(String stuId);
    
    void batchDeleteStudenst(String[] stuIds);
    
    void bindingIdcard(String stuId);
    
    void unbindingIdcard(String stuId);
}
