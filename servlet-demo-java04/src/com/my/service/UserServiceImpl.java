package com.my.service;

import com.my.bean.User;
import com.my.dao.IUserDao;
import com.my.dao.UserDaoImpl;

public class UserServiceImpl implements IUserService
{
    private IUserDao userDao = new UserDaoImpl();
    
    @Override
    public User login(String username, String password) throws Exception
    {
        User user = userDao.findUser(username, password);
        
        if (null == user)
        {
            throw new SecurityException("username or password is error. ");
        }
        
        return user;
    }
}
