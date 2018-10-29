package com.dao;

import com.bean.Student;
import com.vo.Page;
import com.vo.StudentDTO;

public interface IStudentDao
{
    Page<Student> findStudentByPage(Page<Student> pageParam, StudentDTO condition);
    
    Student findeStudentById(String stuId);
    
    void addStudent(Student stu);
    
    void updateStudent(Student stu);
    
    void deleteStudent(Student stuId);
    
    // void batchDeleteStudenst(String[] stuIds);
    
}
