package com.my.service;

import com.my.bean2.User;

public class UserServiceProxy implements IUserService
{
    
    IUserService source = new UserServiceImpl2();
    
    @Override
    public User login(String username, String password)
    {
        try
        {
            
            User user = source.login(username, password);
            
            // commit;
            System.out.println("commit success..");
            return user;
        }
        catch (Exception e)
        {
            // rollBack
            throw e;
        }
        finally
        {
            // close
        }
    }
    
}
