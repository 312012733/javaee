package test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.my.bean.User;

import utils.DynParmParseUtils;

public class DynProxyTest implements InvocationHandler
{
    private IUserDao userDao;
    
    public DynProxyTest()
    {
    }
    
    public DynProxyTest(IUserDao userDao)
    {
        this.userDao = userDao;
    }
    
    /*
     * 处理 代理 业务逻辑
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        
        System.out.println("--------interface:" + proxy.getClass().getInterfaces()[0].getName());
        System.out.println("--------method:" + method.getName());
        System.out.println("--------method ReturnType:" + method.getReturnType().getName());
        
        for (Object object : args)
        {
            System.out.println("--------args:" + object);
        }
        
        String logDesc = "添加用户名为#{arg0.username}的用户，他的角色是#{arg0.role.name}.";
        
        String newLoagDesc = new DynParmParseUtils().build(logDesc, args);
        
        System.out.println(newLoagDesc);
        
        Object result = method.invoke(userDao, args);// 被代理实例 将要执行的方法
        
        return result;
    }
    
    public static void main(String[] args)
    {
        // 创建一个代理
        IUserDao userDao = (IUserDao) Proxy.newProxyInstance(DynProxyTest.class.getClassLoader(), new Class[]
        { IUserDao.class }, new DynProxyTest(new UserDao()));
        
        // System.out.println(userDao.findUser("admin", "9528"));
        
        User user = new User();
//        user.setRole(new Role("管理员"));
        
        userDao.sava(user);
    }
}
