package com.my.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.my.bean.User;
import com.utils.JDBCUtils;

public class UserDaoImpl implements IUserDao
{
    
    @Override
    public User findUser(String username, String password) throws Exception
    {
        
        // 验证用户名密码 是否 正确
        Connection connection = JDBCUtils.openConnection();
        
        String sql = "SELECT * from t_user u where u.user_name = ? and u.password = ?";
        
        List<Object> params = new ArrayList<Object>();
        params.add(username);
        params.add(password);
        
        return JDBCUtils.findEntity(connection, sql, params, User.class);
        
    }
    
}
