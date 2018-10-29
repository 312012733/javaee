package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bean.User;
import com.dao.IUserDao;

@Service
public class UserServiceImpl implements IUserService
{
    
    @Autowired
    private IUserDao userDao;
    
    @Transactional(readOnly = true)
    @Override
    public User login(String username, String password)
    {
        return userDao.findUser(username, password);
    }
    
}
