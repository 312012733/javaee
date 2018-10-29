package com.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bean.Student;
import com.dao.IStudentDao;
import com.utils.HibernateUtils;
import com.vo.Page;

//@Repository
public class StudentDaoImpl implements IStudentDao
{
    
    @Autowired
    private SessionFactory sf;
    
    @Autowired
    private HibernateUtils hibernateUtils;
    
    @Override
    public Student findStudentById(String stuId)
    {
        return sf.getCurrentSession().get(Student.class, stuId);
    }
    
    @Override
    public Page<Student> findStudentsByPage(Page<Student> page, Student condition)
    {
        Map<String, Object> conditionMap = new HashMap<>();
        
        String conditionSql = buildPageCondition(condition, conditionMap);
        
        String listSql = "SELECT * from t_student s " + "where 1 =1 " + conditionSql + "order by s.create_time desc";
        String countSql = "SELECT count(*) from t_student s where 1 = 1 " + conditionSql;
        
        Page<Student> stuPage = hibernateUtils.findByPage(listSql, countSql, page, conditionMap, Student.class);
        
        return stuPage;
    }
    
    /**
     * @param condition
     * @param conditionMap
     * @return
     */
    private String buildPageCondition(Student condition, Map<String, Object> conditionMap)
    {
        StringBuilder conditionSql = new StringBuilder();
        
        if (null == condition)
        {
            return "";
        }
        
        if (null != condition.getName() && condition.getName().length() > 0)
        {
            conditionSql.append("and s.student_name like :name ");
            conditionMap.put("name", "%" + condition.getName() + "%");
        }
        
        if (null != condition.getAge())
        {
            conditionSql.append("and s.age = :age ");
            conditionMap.put("age", condition.getAge());
            
        }
        
        if (null != condition.getGender())
        {
            conditionSql.append("and s.gender = :gender ");
            conditionMap.put("gender", condition.getGender());
        }
        
        return conditionSql.toString();
    }
    
    @Override
    public void addStudent(Student stu)
    {
        sf.getCurrentSession().save(stu);
    }
    
    @Override
    public void updateStudent(Student stu)
    {
        sf.getCurrentSession().update(stu);
    }
    
    @Override
    public void delStudent(Student student)
    {
        // TODO Auto-generated method stub
        sf.getCurrentSession().delete(student);
    }
    
    // @Override
    // public void batchDelStudent(List<Student> students)
    // {
    // for (Student student : students)
    // {
    // sf.getCurrentSession().delete(student);
    // }
    // }
}
