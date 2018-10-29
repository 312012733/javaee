package com.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bean.Teacher;
import com.service.ITeacherService;
import com.vo.ResponseEntity;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TeacherAction
{
    
    private static final Logger LOGGER = Logger.getLogger(TeacherAction.class);
    
    @Autowired
    private ITeacherService teacherService;
    
    private ResponseEntity<Object> responseEntity = new ResponseEntity<>();
    
    private String stuId;
    
    public String findAll() throws Exception
    {
        HttpServletResponse response = ServletActionContext.getResponse();
        
        try
        {
            List<Teacher> teacherList = teacherService.findTeachers();
            responseEntity.setContent(teacherList);
        }
        catch (Exception e)
        {
            LOGGER.error("findAll teachers error. ", e);
            
            response.setStatus(400);
            responseEntity.setErrorMsg("findAll teachers error. " + e.getMessage());
        }
        
        return "json_success";
        
    }
    
    public String findExcludTeachersByStuId() throws Exception
    {
        HttpServletResponse response = ServletActionContext.getResponse();
        
        try
        {
            List<Teacher> teacherList = teacherService.findExcludTeachersByStuId(this.stuId);
            responseEntity.setContent(teacherList);
        }
        catch (Exception e)
        {
            LOGGER.error("findAll teachers error. ", e);
            
            response.setStatus(400);
            responseEntity.setErrorMsg("findAll teachers error. " + e.getMessage());
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
    
    public String getStuId()
    {
        return stuId;
    }
    
    public void setStuId(String stuId)
    {
        this.stuId = stuId;
    }
    
}
