package com.dao;

import com.bean.Student;
import com.vo.Page;

public interface IStudentDao
{
    void addStudent(Student stu);
    
    Student findStudentById(String stuId);
    
    Page<Student> findStudentsByPage(Page<Student> page, Student condition);
    
//    Long queryCount(Student condition);
    
    void updateStudent(Student stu);
    
    void delStudent(Student student);
    
    // delete s from stu s where s.id in ( 'id1' , 'id2' , 'id3' );
//    void batchDelStudent(List<Student> students);
    
}
