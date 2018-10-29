package com.service;

import com.bean2.User;

public interface IUserService
{
    
    User findUserByUsernameAndPassword(String userName, String password) throws Exception;
    
}
