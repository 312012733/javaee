package com.main;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.bean.Student;
import com.bean.StudentIdCard;
import com.config.HibernateConfig;
import com.utils.HibernateUtils;
import com.utils.HibernateUtils.PredicateCallBack;
import com.vo.Page;

public class SpringTest
{
    
    public static Page<Student> findByPage(HibernateUtils hibernateUtils, Student condition)
    {
        Page<Student> page = hibernateUtils.findPage(Student.class, new Page<>(1, 2), new PredicateCallBack()
        {
            
            @Override
            public Predicate toPredicate(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<?> root)
            {
                Predicate p = cb.conjunction();
                
                if (null != condition.getAge())
                {
                    p = cb.and(p, cb.equal(root.get("age"), condition.getAge()));
                }
                
                if (null != condition.getGender())
                {
                    p = cb.and(p, cb.equal(root.get("gender"), condition.getGender()));
                }
                
                if (!StringUtils.isEmpty(condition.getName()))
                {
                    p = cb.and(p, cb.like(root.get("name"), "%" + condition.getName() + "%"));
                }
                
                if (null != condition.getStudentIdCard() && !StringUtils.isEmpty(condition.getStudentIdCard().getNum()))
                {
                    p = cb.and(p, cb.like(root.get("studentIdCard").get("num"),
                            "%" + condition.getStudentIdCard().getNum() + "%"));
                }
                
                return p;
            }
            
        });
        
        return page;
    }
    
    public static void main(String[] args) throws InterruptedException
    {
        // ApplicationContext context = new
        // ClassPathXmlApplicationContext("application-context.xml");
        
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(HibernateConfig.class);
        
        // 工厂
        SessionFactory sf = context.getBean(SessionFactory.class);
        
        // session
        Session session = sf.getCurrentSession();
        
        Transaction transaction = session.beginTransaction();
        
        try
        {
            /**************************************************************/
            Student condition = new Student(null, "三", 11, true);
            condition.setStudentIdCard(new StudentIdCard("c1", "9527"));
            
            HibernateUtils hibernateUtils = context.getBean(HibernateUtils.class);
            
            // session.find(Student.class, "s1");
            // session.find(Student.class, "s1");
            // session.find(Student.class, "s1");
            // session.find(Student.class, "s1");
            
            // findByPage(hibernateUtils, condition);
            // findByPage(hibernateUtils, condition);
            //
            // Thread.sleep(3*1000);
            //
            // findByPage(hibernateUtils, condition);
            Page<Student> page = findByPage(hibernateUtils, condition);
            
            System.out.println(JSON.toJSONString(page, true));
            
            transaction.commit();
        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            transaction.rollback();
        }
        
        context.close();
        System.exit(0);
    }
}
