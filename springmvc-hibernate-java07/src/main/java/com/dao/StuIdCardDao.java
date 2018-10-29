package com.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bean.StudentIdCard;

// @Component
@Repository
public class StuIdCardDao implements IStudentIdCardDao
{
    @Autowired
    private SessionFactory sessionFactory;
    
    // @Autowired
    // private HibernateUtils hibernateUtils;
    
    @Override
    public StudentIdCard findStuIdCardById(String idcardId)
    {
        return sessionFactory.getCurrentSession().get(StudentIdCard.class, idcardId);
    }
    
    // @Override
    // public StudentIdCard findStuIdCardByStuId(String stuId)
    // {
    // PredicateCallBack predicateCallBack = new PredicateCallBack()
    // {
    // @Override
    // public Predicate toPredicate(CriteriaBuilder cb, CriteriaQuery<?> cq,
    // Root<?> root)
    // {
    // Predicate restrictions = cb.equal(root.get("student").get("id"), stuId);
    // return cb.and(restrictions);
    // }
    // };
    //
    // return hibernateUtils.find(StudentIdCard.class, predicateCallBack);
    // }
    
    @Override
    public void addStudentIdCard(StudentIdCard idCard)
    {
        sessionFactory.getCurrentSession().save(idCard);
    }
    
    @Override
    public void deleteStudentIdCard(StudentIdCard idCard)
    {
        sessionFactory.getCurrentSession().delete(idCard);
    }
}
