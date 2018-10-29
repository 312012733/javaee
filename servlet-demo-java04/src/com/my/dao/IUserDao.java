package com.my.dao;

import com.my.bean.User;

public interface IUserDao
{
    User findUser(String username, String password)throws Exception;
}
