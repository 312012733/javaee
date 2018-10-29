package com.my.dao;

import java.util.List;

import com.my.bean.User;

public interface IUserDao
{
    
    List<User> findUsers();
    
    User findUserById(String id);
    
    User findUserByUsernameAndPassword(String username, String password);
    
    void sava(User user);
    
    void udate(User user);
    
    void delete(String id);
    
    List<User> findUsersByRoleId(String id);
}
