package com.my.xstream;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import com.my.bean.User;
import com.my.dao.IRoleDao;
import com.my.dao.IUserDao;
import com.my.framework.config.Configuration;
import com.my.framework.config.Session;
import com.my.framework.config.SessionFactory;
import com.my.framework.config.XmlUtils;

public class FrameworkTest
{
    
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException
    {
        // 加载 配置 文件
        XmlUtils xmlUtil = new XmlUtils("mybatis.xml");
        Configuration config = xmlUtil.parse(Configuration.class);
       
        // 构建sessionFactory
        SessionFactory sessionFactory = config.buildSessionFactory();
       
        // 获取session
        Session session = sessionFactory.openSesson();
       
        // 获取代理
        IUserDao userDao = session.getMapper(IUserDao.class);
        
//        System.out.println(userDao.getClass().getName());
//        
        User u1 = userDao.findUserByUsernameAndPassword("admin", "1234");
        
System.out.println(u1);


IRoleDao roleDao = session.getMapper(IRoleDao.class);
System.out.println(roleDao.findRoleById("1"));
//        
//        User u2 = userDao.findUserById("1");
//        
//        List<User> userList = userDao.findUsers();
//        
//        System.out.println(u1);
//        System.out.println(u2);
//        System.out.println(userList);
        
//        userDao.sava(new User(UUID.randomUUID().toString(), "li4", "1234", 99, true));
        
//        User u = userDao.findUserById("c6e59b7c-fadc-48e6-aff5-022b26c43d8a");
//        u.setAge(18);
//        u.setSex(false);
//        u.setPassword("9527");
//        u.setUsername("li44");
//        userDao.udate(u);
        
        
//        userDao.delete("c6e59b7c-fadc-48e6-aff5-022b26c43d8a");
        
    }
    
}
