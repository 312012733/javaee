package com.main;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.bean.MyClass;
import com.bean.Student;
import com.bean.Teacher;
import com.config.MybatisConfig;
import com.dao.IMyClassDao;
import com.dao.IStudentAndTeacherDao;
import com.dao.IStudentDao;
import com.dao.ITeacherDao;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

public class SpringTest
{
    
    public static void main(String[] args)
    {
        // ApplicationContext context = new
        // ClassPathXmlApplicationContext("application-context.xml");
        
        ApplicationContext context = new AnnotationConfigApplicationContext(MybatisConfig.class);
        
        // SqlSessionFactory sf = context.getBean(SqlSessionFactory.class);
        // SqlSession session = sf.openSession();
        // IStudentDao studentDao = session.getMapper(IStudentDao.class);
        
        // IStudentDao studentDao = context.getBean(IStudentDao.class);
        // IMyClassDao classDao = context.getBean(IMyClassDao.class);
//        ITeacherDao teacherDao = context.getBean(ITeacherDao.class);
        IStudentAndTeacherDao stDao = context.getBean(IStudentAndTeacherDao.class);
        
        stDao.batchSaveStuAndTeacher("s1", new String[] {"t1","t2","t3"});
//        stDao.deleteByStudentId("s1");
        
//        Teacher teacher = teacherDao.findTeacherById("t1");
//        List<Teacher> allTeachers = teacherDao.findAllTeachers();
//        List<Teacher> excludeTeachers = teacherDao.findExcludeTeachersByStuId("s3");
        
//        System.out.println(JSONObject.toJSONString(teacher, true));
//        System.out.println(JSONObject.toJSONString(allTeachers, true));
//        System.out.println(JSONObject.toJSONString(excludseTeachers, true));
        
        // Student stu = studentDao.findeStudentById("s1");
        
        // PageHelper.startPage(1, 5);
        // Page<Student> page = studentDao.findStudentByPage(null);
        //
        // System.out.println(JSONObject.toJSONString(com.vo.Page.buildPage(page),
        // true));
        
        // List<MyClass> myClassList = classDao.findAllClasses();
        // System.out.println(JSONObject.toJSONString(myClassList, true));
        
        // String[] stuIds = new String[] {"s1","s2"};
        // studentDao.batchDeleteStudenst(stuIds );
        
    }
}
