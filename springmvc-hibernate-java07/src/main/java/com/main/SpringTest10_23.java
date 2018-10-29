package com.main;

import java.util.ArrayList;
import java.util.List;

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
import com.config.HibernateConfig;
import com.utils.HibernateUtils;
import com.utils.HibernateUtils.PredicateCallBack;
import com.vo.Page;

public class SpringTest10_23
{
    
    public static Predicate toPredicate(CriteriaBuilder criteriaBuilder, CriteriaQuery<Student> createQuery,
            Root<Student> root, Student condition)
    {
        List<Predicate> listPredicate = new ArrayList<>();
        
        if (null != condition.getAge())
        {
            Predicate temp = criteriaBuilder.equal(root.get("age"), condition.getAge());
            listPredicate.add(temp);
        }
        
        if (null != condition.getGender())
        {
            Predicate temp = criteriaBuilder.equal(root.get("gender"), condition.getGender());
            listPredicate.add(temp);
        }
        
        if (!StringUtils.isEmpty(condition.getName()))
        {
            Predicate temp = criteriaBuilder.like(root.get("name"), "%" + condition.getName() + "%");
            listPredicate.add(temp);
        }
        
        Predicate[] predicateAray = new Predicate[listPredicate.size()];
        
        return criteriaBuilder.and(listPredicate.toArray(predicateAray));
    }
    
    public static void main(String[] args)
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
            
            HibernateUtils hibernateUtils = context.getBean(HibernateUtils.class);
            
            // Student stu = hibernateUtils.find(Student.class, new
            // PredicateCallBack()
            // {
            //
            // @Override
            // public Predicate toPredicate(CriteriaBuilder cb, CriteriaQuery<?>
            // cq, Root<?> root)
            // {
            // return cb.and(cb.equal(root.get("id"), condition.getId()));
            // }
            //
            // });
            
            // List<Student> stuList = hibernateUtils.findList(Student.class,
            // new PredicateCallBack()
            
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
                    
                    return p;
                }
                
            });
            
            System.out.println(JSON.toJSONString(page, true));
            
            /***************************************************************/
            // Student condtion = new Student(null, "xx", 12, true);// 条件
            //
            // CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();//
            // 用来构造sql语句的某部分
            //
            // CriteriaQuery<Student> createQuery =
            // criteriaBuilder.createQuery(Student.class);// 查询语句
            //
            // Root<Student> root = createQuery.from(Student.class);// 关联的实体对象
            //
            // Predicate predicates = toPredicate(criteriaBuilder, createQuery,
            // root, condtion);// 条件
            //
            // createQuery.select(root).where(predicates).orderBy(criteriaBuilder.desc(root.get("createTime")));//
            // 拼接完整的sql
            //
            // TypedQuery<Student> query = session.createQuery(createQuery);//
            // 获取执行的query
            //
            // List<Student> resultList = query.getResultList();// 开始执行查询
            
            // System.out.println(JSON.toJSONString(resultList, true));
            
            /***************************************************************/
            // Student stu = session.find(Student.class, "s2");
            //
            // String nameParam = "三";
            // int curPage = 1;
            // int pageSize = 2;
            //
            // // String hql = "from com.bean.Student s where s.name like
            // :name";
            // // Query<Student> query = session.createQuery(hql,
            // // Student.class).setParameter("name", "%" +nameParam+"%");
            //
            //
            //
            // String sql = "select * from t_student s where s.student_name like
            // :name123 ";
            // Query<Student> query = session.createNativeQuery(sql,
            // Student.class);
            // query.setFirstResult(curPage * pageSize);//offset
            // query.setMaxResults(pageSize);//pagesize
            // query.setParameter("name123", "%"+nameParam+"%");
            //
            //// query.setParameter("offset", (curPage * pageSize));
            //// query.setParameter("pagesize", pageSize);
            //
            // List<Student> stuList = query.getResultList();
            //
            // System.out.println(JSON.toJSONString(stuList, true));
            
            /***************************************************************/
            
            // Student newStu1 = new Student("s1", "张三", 11, true);
            //
            // session.save(newStu1);
            //
            // Student newStu = new Student("s2", "李四", 11, true);
            //
            // session.save(newStu);
            
            /***************************************************************/
            
            // Student stu = session.find(Student.class, "s2");
            // if(null!=stu) {
            //
            // stu.setName("李四四");
            // stu.setCreateTime(System.currentTimeMillis());
            // stu.setLastModifyTime(System.currentTimeMillis());
            // session.update(stu);
            // }
            
            /***************************************************************/
            //
            // Student stu = session.find(Student.class, "s2");
            // if (null != stu)
            // {
            // session.delete(stu);
            // }
            //
            // // System.out.println(stu);
            
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
