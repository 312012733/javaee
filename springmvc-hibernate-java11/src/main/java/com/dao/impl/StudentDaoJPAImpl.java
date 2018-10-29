package com.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.bean.Student;
import com.dao.IStudentDao;
import com.vo.Page;

@Repository
public class StudentDaoJPAImpl implements IStudentDao
{
    @Autowired
    private SessionFactory sessionFactory;
    
    // @Autowired
    // private HibernateUtils hibernateUtils;
    
    // @Override
    // public Page<Student> findStudentByPage(Page<Student> page, StudentDTO
    // condition)
    // {
    // Map<String, Object> conditionMap = new HashMap<>();
    // String condtionSql = "";
    //
    // if (null != condition)
    // {
    // if (!StringUtils.isEmpty(condition.getName()))
    // {
    // condtionSql += "and s.student_name like :name ";
    // conditionMap.put("name", "%" + condition.getName() + "%");
    // }
    //
    // if (null != condition.getAge())
    // {
    // condtionSql += "and s.age = :age ";
    // conditionMap.put("age", condition.getAge());
    // }
    //
    // if (null != condition.getGender())
    // {
    // condtionSql += "and s.gender = :gender ";
    // conditionMap.put("gender", condition.getGender());
    // }
    // }
    //
    // String listSql = "SELECT * FROM t_student s where 1 = 1 " + condtionSql +
    // " ORDER BY s.create_time DESC";
    // String countSql = "SELECT count(*) FROM t_student s where 1 = 1 " +
    // condtionSql;
    //
    // return hibernateUtils.findByPage(listSql, countSql, page, conditionMap,
    // Student.class);
    // }
    
    @Override
    public Page<Student> findStudentsByPage(Page<Student> page, Student stuCondition)
    {
        List<Student> studentList = findStudentList(page, stuCondition);
        Long maxCount = findMaxCount(stuCondition);
        page.setTotalCount(maxCount);
        page.setContent(studentList);
        
        return page;
    }
    
    private List<Student> findStudentList(Page<Student> page, Student stuCondition)
    {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
        Root<Student> root = criteriaQuery.from(Student.class);
        
        Predicate predicate = toPredicate(stuCondition, criteriaBuilder, root);
        Order order = criteriaBuilder.desc(root.get("createTime"));
        
        criteriaQuery.select(root).where(predicate).orderBy(order);
        
        TypedQuery<Student> query = session.createQuery(criteriaQuery);
        
        query.setFirstResult(page.getOffset());
        query.setMaxResults(page.getPageSize());
        
        List<Student> stuList = query.getResultList();
        
        return stuList;
    }
    
    private Long findMaxCount(Student stuCondition)
    {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Student> root = criteriaQuery.from(Student.class);
        
        Predicate predicate = toPredicate(stuCondition, criteriaBuilder, root);
        
        criteriaQuery.select(criteriaBuilder.count(root)).where(predicate);
        
        TypedQuery<Long> query = session.createQuery(criteriaQuery);
        
        Long count = query.getSingleResult();
        
        return count;
    }
    
    private Predicate toPredicate(Student stuCondition, CriteriaBuilder criteriaBuilder, Root<Student> root)
    {
        Predicate predicate = null;
        
        if (null != stuCondition)
        {
            
            List<Predicate> predicates = new ArrayList<Predicate>();
            
            if (!StringUtils.isEmpty(stuCondition.getName()))
            {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + stuCondition.getName() + "%"));
            }
            
            if (null != stuCondition.getAge())
            {
                predicates.add(criteriaBuilder.equal(root.get("age"), stuCondition.getAge()));
            }
            
            if (null != stuCondition.getGender())
            {
                predicates.add(criteriaBuilder.equal(root.get("gender"), stuCondition.getGender()));
            }
            
            if (null != stuCondition.getMyClass() && !StringUtils.isEmpty(stuCondition.getMyClass().getName()))
            {
                predicates.add(criteriaBuilder.like(root.get("myClass").get("name"),
                        "%" + stuCondition.getMyClass().getName() + "%"));
            }
            
            predicate = predicates.isEmpty() ? criteriaBuilder.conjunction()
                    : criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }
        return predicate;
    }
    
    @Override
    public Student findStudentById(String stuId)
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
    public void delStudent(Student stu)
    {
        sessionFactory.getCurrentSession().delete(stu);
    }

//    @Override
//    public void batchDelStudent(List<Student> students)
//    {
//        for (Student student : students)
//        {
//            sessionFactory.getCurrentSession().delete(student);
//        }
//        
//    }
}
