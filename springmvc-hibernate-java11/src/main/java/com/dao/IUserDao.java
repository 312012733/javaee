package com.dao;

import com.bean2.User;

public interface IUserDao
{
    User findUserByUsernameAndPassword(String userName, String password) throws Exception;
    
}
