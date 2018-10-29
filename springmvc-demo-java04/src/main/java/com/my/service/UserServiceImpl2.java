package com.my.service;

import com.my.bean2.User;

public class UserServiceImpl2 implements IUserService
{
    
    @Override
    public User login(String username, String password)
    {

        System.out.println(username+"  login success....");
        
        return null;
    }
}
