package com.dao;

import com.bean.Student;
import com.vo.StudentDTO;

public interface IStudentDao
{
    // select * from t_student s  where 1 = 1 limit 0 , 5 --- > List<Student>
//    List<Student> findStudentByPage(RowBounds rowBoounds, StudentDTO condition);//#{arg0.offset} #{arg0.pageSize}
//    List<Student> findStudentByPage(Page<Student> page, StudentDTO condition);//#{arg0.offset} #{arg0.pageSize}
    com.github.pagehelper.Page<Student> findStudentByPage(StudentDTO condition);//#{arg0.offset} #{arg0.pageSize}
    
//    Long findTotalCount(StudentDTO condition);//#{arg0.offset} #{arg0.pageSize}

    Student findeStudentById(String stuId);//{arg0}
    
    void addStudent(Student stu);//#{name}    #{createTime}
    
    void updateStudent(Student stu);
    
    void deleteStudent(String stuId);
    
    void batchDeleteStudenst(String[] stuIds);
    
}
