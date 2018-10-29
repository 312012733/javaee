package com.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.bean.Student;
import com.utils.HibernateUtils;
import com.utils.HibernateUtils.PredicateCallBack;
import com.vo.Order;
import com.vo.Order.OrderType;
import com.vo.Page;
import com.vo.StudentDTO;

@Repository
public class StudentDaoImpl implements IStudentDao
{
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private HibernateUtils hibernateUtils;
    
    @Override
    public Page<Student> findStudentByPage(Page<Student> pageParam, StudentDTO condition)
    {
        PredicateCallBack predicateCallBack = new PredicateCallBack()
        {
            @Override
            public Predicate toPredicate(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<?> root)
            {
                List<Predicate> listPredicate = new ArrayList<>();
                
                if (null != condition.getAge())
                {
                    Predicate temp = cb.equal(root.get("age"), condition.getAge());
                    listPredicate.add(temp);
                }
                
                if (null != condition.getGender())
                {
                    Predicate temp = cb.equal(root.get("gender"), condition.getGender());
                    listPredicate.add(temp);
                }
                
                if (!StringUtils.isEmpty(condition.getName()))
                {
                    Predicate temp = cb.like(root.get("name"), "%" + condition.getName() + "%");
                    listPredicate.add(temp);
                }
                
                if (null != condition.getStudentIdCard() && !StringUtils.isEmpty(condition.getStudentIdCard().getNum()))
                {
                    Predicate temp = cb.like(root.get("studentIdCard").get("num"),
                            "%" + condition.getStudentIdCard().getNum() + "%");
                    listPredicate.add(temp);
                }
                
                Predicate[] predicateAray = new Predicate[listPredicate.size()];
                
                return cb.and(listPredicate.toArray(predicateAray));
            }
        };
        
        return hibernateUtils.findPage(Student.class, pageParam, new Order(OrderType.DESC, "createTime"),
                predicateCallBack);
    }
    
    @Override
    public Student findeStudentById(String stuId)
    {
        return sessionFactory.getCurrentSession().find(Student.class, stuId);
    }
    
    @Override
    public void addStudent(Student stu)
    {
        sessionFactory.getCurrentSession().save(stu);
    }
    
    @Override
    public void updateStudent(Student stu)
    {
        sessionFactory.getCurrentSession().update(stu);
    }
    
    @Override
    public void deleteStudent(Student stu)
    {
        sessionFactory.getCurrentSession().delete(stu);
    }
    
}
