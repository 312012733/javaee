package com.dao;

import com.bean.User;

public interface IUserDao
{
    
    // select * from t_user u where u.user_name = ? and u.password = ?
    User findUser(String username, String password);
    
}
