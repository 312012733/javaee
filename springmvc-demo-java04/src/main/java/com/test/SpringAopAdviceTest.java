package com.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.my.service.IStudentService;

public class SpringAopAdviceTest
{
    
    public static void main(String[] args)
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        
        IStudentService service = context.getBean(IStudentService.class);
        
        System.out.println(service.findeStudentById("s1"));

        context.close();
    }
}
