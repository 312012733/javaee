package com.my.interceptor;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.vo.ResponseEntity;

@Component
public class ExceptionInterceptor extends AbstractInterceptor
// public class ExceptionInterceptor implements Interceptor
{
    //
    // @Override
    // public void destroy()
    // {
    // // TODO Auto-generated method stub
    //
    // }
    //
    // @Override
    // public void init()
    // {
    // // TODO Auto-generated method stub
    //
    // }
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public String intercept(ActionInvocation ai) throws Exception
    {
        try
        {
            return ai.invoke();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            HttpServletResponse resp = ServletActionContext.getResponse();
            resp.setStatus(400);
            
            // 获取action实例
            Object actionInstance = ai.getAction();
            
            // 获取action类模板
            Class<?> actinClass = actionInstance.getClass();
            
            /* 获取 responseEntity 的get方法 --------- start */
            Field[] fields = actinClass.getDeclaredFields();
            
            String responseEntityFieldName = "";
            
            for (Field field : fields)
            {
                if (field.getType().getName().equals(ResponseEntity.class.getName()))
                {
                    responseEntityFieldName = field.getName();
                    break;
                }
            }
            
            // TODO responseEntityFieldName 空判断。。
            
            PropertyDescriptor pd = new PropertyDescriptor(responseEntityFieldName, actinClass);
            
            Method responseEntityReadMethod = pd.getReadMethod();
            
            /* 获取 responseEntity 的get方法 --------- end */
            
            // 反射调用 ResponseEntity 属性的 get方法，获取ResponseEntity 实例。
            ResponseEntity<?> responseEntity = (ResponseEntity<?>) responseEntityReadMethod.invoke(actionInstance);
            
            String methodName = ai.getProxy().getMethod();
            
            // 设置错误消息
            responseEntity.setErrorMsg(actinClass.getSimpleName() + "." + methodName + " error. " + e.getMessage());
            
            return "json_success";
        }
        
    }
    
}
