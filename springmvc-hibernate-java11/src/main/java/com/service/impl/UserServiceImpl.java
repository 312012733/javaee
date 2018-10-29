package com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean2.User;
import com.dao.IUserDao;
import com.service.IUserService;

@Service
public class UserServiceImpl implements IUserService
{
    @Autowired
    private IUserDao userDao;
    
    @Override
    public User findUserByUsernameAndPassword(String userName, String password) throws Exception
    {
        return userDao.findUserByUsernameAndPassword(userName, password);
    }
    
}
