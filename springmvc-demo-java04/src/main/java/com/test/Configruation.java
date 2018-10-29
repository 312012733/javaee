package com.test;

import com.my.bean.MyClass;
import com.my.bean.Student;
import com.my.bean.StudentIdCard;
import com.my.bean.Teacher;
import com.my.bean2.User;

//@Configuration
public class Configruation
{
    // @Bean(name = "user")
    // @Scope(scopeName=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public User getUser()
    {
        return new User("9528", "admin", "1234");
    }
    
    // @Bean(name = "student")
    public Student getStudent(StudentIdCard studentIdCard, MyClass myClass, Teacher teacher)
    {
        Student stu = new Student("s2", "李四", 19, true);
        
        stu.setStudentIdCard(studentIdCard);
        stu.setMyClass(myClass);
        stu.getTeachers().add(teacher);
        
        return stu;
    }
    
    // @Bean(name = "teacher")
    // public Teacher getTeacher()
    // {
    // return new Teacher("t1", "李老师");
    // }
    //
    // @Bean(name = "studentIdCard")
    // public StudentIdCard getStudentIdCard()
    // {
    // return new StudentIdCard("c1","1234");
    // }
    //
    // @Bean(name = "myClass")
    // public MyClass getMyClass()
    // {
    // return new MyClass("b1","1B");
    // }
}
