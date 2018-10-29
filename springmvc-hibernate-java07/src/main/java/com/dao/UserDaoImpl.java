package com.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bean.User;
import com.utils.HibernateUtils;
import com.utils.HibernateUtils.PredicateCallBack;

// @Component
@Repository
public class UserDaoImpl implements IUserDao
{
    // @Autowired
    // private SessionFactory sessionFactory;
    
    @Autowired
    private HibernateUtils hibernateUtils;
    
    @Override
    public User findUser(String username, String password)
    {
        PredicateCallBack condition = new PredicateCallBack()
        {
            @Override
            public Predicate toPredicate(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<?> root)
            {
                List<Predicate> predicateList = new ArrayList<>();
                
                predicateList.add(cb.equal(root.get("username"), username));
                predicateList.add(cb.equal(root.get("password"), password));
                
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
        
        return hibernateUtils.find(User.class, condition);
    }
}
