package com.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bean.Teacher;
import com.utils.HibernateUtils;

// @Component
@Repository
public class TeacherDao implements ITeacherDao
{
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private HibernateUtils hibernateUtils;
    
    @Override
    public Teacher findTeacherById(String teacherId)
    {
        return sessionFactory.getCurrentSession().get(Teacher.class, teacherId);
    }
    
    @Override
    public List<Teacher> findAllTeachers()
    {
        return hibernateUtils.findList(Teacher.class);
    }
    
    @Override
    public List<Teacher> findExcludeTeachersByStuId(String stuId)
    {
        String sql = "SELECT * FROM t_teacher t WHERE t.pk_teacher_id NOT IN ( SELECT st.pk_teacher_id FROM t_student_teacher st WHERE st.pk_student_id = :stuId ) ";
        Query<Teacher> query = sessionFactory.getCurrentSession().
                createNativeQuery(sql, Teacher.class)
                .setParameter("stuId", stuId);
        
        return query.getResultList();
    }
}
