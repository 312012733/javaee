package com.test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SpringBootDemoService
{
    
    public ResponseEntity<Object> test(String str, Integer num, Boolean flag)
    {
        
        try
        {
            // 1
            // 2
            // 3
            
            // commit
        }
        catch (Exception e)
        {
            
            // rollback
        }
        finally
        {
            
            // close
        }
        
        return new ResponseEntity<Object>("world", HttpStatus.OK);
    }
    
    public static void main(String[] args)
    {

        for (Method method : SpringBootDemoService.class.getMethods())
        {
            for (Parameter param : method.getParameters())
            {
                System.out.println(method.getName()+"    "+param.getName());
            }
            
        }
    }
}
