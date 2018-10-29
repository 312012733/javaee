package com.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bean.MyClass;
import com.service.IMyClassService;
import com.vo.ResponseEntity;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MyClassAction
{
    
    private static final Logger LOGGER = Logger.getLogger(MyClassAction.class);
    
    @Autowired
    private IMyClassService classService;
    
    private ResponseEntity<Object> responseEntity = new ResponseEntity<>();
    
    public String findAll() throws Exception
    {
        HttpServletResponse response = ServletActionContext.getResponse();
        
        try
        {
            List<MyClass> classList = classService.findMyClasses();
            responseEntity.setContent(classList);
        }
        catch (Exception e)
        {
            LOGGER.error("findAll classes error. ", e);
            
            response.setStatus(400);
            responseEntity.setErrorMsg("findAll classes error. " + e.getMessage());
        }
        
        return "json_success";
        
    }
    
    public ResponseEntity<Object> getResponseEntity()
    {
        return responseEntity;
    }
    
    public void setResponseEntity(ResponseEntity<Object> responseEntity)
    {
        this.responseEntity = responseEntity;
    }
    
}
