package com.main;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.alibaba.fastjson.JSONObject;
import com.bean.Teacher;
import com.dao.ITeacherDao;

public class Many2Many
{
    public static void main(String[] args)
    {
        // 加载配置文件
        InputStream in = Many2Many.class.getClassLoader().getResourceAsStream("mybatis.xml");
        
        // 获取session工厂
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(in);
        
        // 获取session
        SqlSession session = sf.openSession();
        
        // // 获取dao代理对象
        // IStudentDao stuDao = session.getMapper(IStudentDao.class);
        // // 执行方法
        //
        // // Student stu = stuDao.findStudentById("1");
        //
        // Page<Student> page = new Page<>(0, 10);
        // List<Student> stuList = stuDao.findStudentsByPage(page, null);
        
        ITeacherDao teacherDao = session.getMapper(ITeacherDao.class);
        
        List<Teacher> teacherAllList = teacherDao.findTeachers();
        String result1 = JSONObject.toJSONString(teacherAllList);
        
        List<Teacher> teacherList = teacherDao.findTeachersByExcludStuIds("1");
        String result2 = JSONObject.toJSONString(teacherList);
        
        System.out.println(result1);
        System.out.println(result2);
        
        session.commit();
        
    }
}
