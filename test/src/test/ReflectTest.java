package test;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import com.my.bean.User;

public class ReflectTest
{
    
    public static void main(String[] args) throws Exception
    {
        // 什么是反射:程序运行的时候，探知类相关信息
        // 反射有什么用
        
        // Class
        // User u = new User();
        
        Class<?> userClass1 = User.class;
        // Class<?> userClass2 = u.getClass();
        // Class<?> userClass3 = Class.forName("com.my.bean.User");
        
        // System.out.println(userClass1 == userClass2);
        // System.out.println(userClass1 == userClass3);
        
        System.out.println(userClass1.getModifiers() + " " + userClass1.getName());
        
        System.out.println();
        System.out.println();
        
        // Constructor
        // Constructor[] c1 = userClass1.getConstructors();
        Constructor[] c2 = userClass1.getDeclaredConstructors();
        
        for (Constructor c : c2)
        {
            
            Parameter[] parameters = c.getParameters();
            
            String pStr = getParameterDesc(parameters);
            
            System.out.println(c.getModifiers() + " " + c.getName() + " ( " + pStr + " ) ");
        }
        
        System.out.println();
        System.out.println();
        
        // Method
        Method[] m1 = userClass1.getMethods();
        Method[] m2 = userClass1.getDeclaredMethods();
        
        for (Method m : m2)
        {
            String pStr = getParameterDesc(m.getParameters());
            
            System.out.println(
                    m.getModifiers() + " " + m.getReturnType().getName() + " " + m.getName() + " ( " + pStr + " ) ");
        }
        
        System.out.println();
        System.out.println();
        
        // Field
        Field[] f1 = userClass1.getFields();
        Field[] f2 = userClass1.getDeclaredFields();
        
        for (Field f : f2)
        {
            System.out.println(f.getModifiers() + " " + f.getName());
        }
        
        System.out.println();
        System.out.println();
        
        // 通过 反射 来实例化对象，给属性赋值，调用实例的方法
        userClass1.newInstance();// 调用的是无参构造
        
        Constructor constructor = userClass1.getConstructor(new Class[]
        { String[].class });
        
        Object instance = constructor.newInstance((Object) new String[]
        { "1", "2" });
        
        // 属性赋值
        
        for (Field f : f2)
        {
            f.setAccessible(true);// 不能用，破坏封装性
            
            if (f.getName().equals("username"))
            {
                f.set(instance, "admin");
            }
            else
            {
                f.set(instance, "1234");
            }
        }
        
        // 方法调用
        
        for (Method m : m2)
        {
            if (m.getName().indexOf("get") == 0)
            {
                Object obj = m.invoke(instance);
                System.out.println("invoke " + m.getName() + " value is:" + obj + "   hashCode:" + m.hashCode());
            }
            else if (m.getName().indexOf("set") == 0)
            {
                if (m.getName().indexOf("Username") > 0)
                {
                    m.invoke(instance, "zhang3");
                }
                else if (m.getName().indexOf("Password") > 0)
                {
                    m.invoke(instance, "4321");
                }
            }
        }
        
        System.out.println();
        System.out.println();
        
        // 怎么获取get、set方法
        for (Field f : f2)
        {
            String fieldName = f.getName();
            PropertyDescriptor pd = new PropertyDescriptor(fieldName, User.class);
            
            Method getMethod = pd.getReadMethod();
            Method setMethod = pd.getWriteMethod();
            
            System.out.println("PropertyDescriptor 获取的 get set 方法 是：" + getMethod.getName() + "   hashCode:"
                    + getMethod.hashCode() + "   " + setMethod.getName() + "   hashCode:" + setMethod.hashCode());
        }
        
        System.out.println("result：" + instance.getClass().getName() + "---" + instance);
        
    }
    
    private static String getParameterDesc(Parameter[] parameters)
    {
        String pStr = "";
        
        for (Parameter p : parameters)
        {
            // Method pm = p.getClass().getDeclaredMethod("getRealName");
            // pm.setAccessible(true);
            // Object realName = pm.invoke(p, null);
            
            pStr += p.getModifiers() + " " + p.getType().getName() + " " + p.getName() + " ,";
        }
        
        return pStr;
    }
    
}
