package com.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bean2.User;
import com.dao.IUserDao;
import com.utils.HibernateUtils;

@Repository
public class UserDaoImpl implements IUserDao
{
    @Autowired
    private HibernateUtils hibernateUtils;
    
    @Override
    public User findUserByUsernameAndPassword(String userName, String password) throws Exception
    {
        
        String sql = "SELECT * from t_user u where u.user_name = :username and u.password = :password";
        
        Map<String, Object> condition = new HashMap<>();
        
        condition.put("username", userName);
        condition.put("password", password);
        
        return hibernateUtils.findEntit(sql, condition, User.class);
    }
    
}
