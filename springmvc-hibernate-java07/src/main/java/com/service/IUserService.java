package com.service;

import com.bean.User;

public interface IUserService
{
    User login(String username, String password);
}
