package com.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bean.Teacher;
import com.dao.ITeacherDao;
import com.utils.HibernateUtils;

@Repository
public class TeacherDao implements ITeacherDao
{
    
    @Autowired
    private HibernateUtils hibernateUtils;
    
    @Autowired
    private SessionFactory sf;
    
    @Override
    public List<Teacher> findTeachers()
    {
        String sql = "select * from t_teacher  where 1 = 1 order by teacher_name desc";
        List<Teacher> teacherList = hibernateUtils.findEntits(sql, null, Teacher.class);
        
        return teacherList;
    }
    
    @Override
    public List<Teacher> findTeachersByExcludStuIds(String stuId)
    {
        
        String sql = "SELECT * from t_teacher t where t.pk_teacher_id not in  "
                + "( SELECT t1.pk_teacher_id from t_teacher t1 INNER JOIN t_student_teacher st on t1.pk_teacher_id = st.pk_teacher_id  "
                + "where st.pk_student_id = :stuId  ) order by t.teacher_name DESC";
        
        Map<String, Object> condition = new HashMap<>();
        condition.put("stuId", stuId);
        
        List<Teacher> teacherList = hibernateUtils.findEntits(sql, condition, Teacher.class);
        
        return teacherList;
    }
    
    @Override
    public List<Teacher> findTeacherByTeacherIds(String[] teacherIds)
    {
        List<Teacher> teacherList = new ArrayList<>();
        
        for (String tId : teacherIds)
        {
            Teacher teacher = findTeacherById(tId);
            
            if (null != teacher)
            {
                teacherList.add(teacher);
            }
        }
        
        return teacherList;
    }
    
    @Override
    public Teacher findTeacherById(String teacherId)
    {
        return sf.getCurrentSession().get(Teacher.class, teacherId);
    }
}
