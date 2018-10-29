package com.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bean.Student;
import com.service.IStudentService;
import com.vo.Page;
import com.vo.ResponseEntity;
import com.vo.StudentDTO;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentAction
{
    @Autowired
    private IStudentService stuService;
    
    private ResponseEntity<Object> responseEntity = new ResponseEntity<>();
    
    private StudentDTO stuDTO = new StudentDTO();
    
    private Page<Student> page = new Page<>();
    
    public String findByPage() throws IOException
    {
        
        HttpServletResponse response = ServletActionContext.getResponse();
        
        try
        {
            Page<Student> pageResult = stuService.findStudentsByPage(page, stuDTO.buildStuCondition());
            
            responseEntity.setContent(pageResult);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            response.setStatus(400);
            responseEntity.setErrorMsg("find stu by page error. " + e.getMessage());
        }
        
        return "json_success";
        
    }
    
    public String add() throws IOException
    {
        
        HttpServletRequest req = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        
        try
        {
            String[] teacherIds = req.getParameterValues("teacherIds[]");
            stuDTO.setTeacherIds(teacherIds);
            stuService.addStudent(stuDTO);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            response.setStatus(400);
            responseEntity.setErrorMsg("add stu error. " + e.getMessage());
        }
        
        return "json_success";
        
    }
    
    public String update() throws IOException
    {
        
        HttpServletRequest req = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        
        try
        {
            String[] teacherIds = req.getParameterValues("teacherIds[]");
            stuDTO.setTeacherIds(teacherIds);
            stuService.updateStudent(stuDTO);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            response.setStatus(400);
            responseEntity.setErrorMsg("add stu error. " + e.getMessage());
        }
        
        return "json_success";
        
    }
    
    public String findById() throws IOException
    {
        
        HttpServletResponse response = ServletActionContext.getResponse();
        
        try
        {
            StudentDTO stu = stuService.findStudentById(stuDTO.getId());
            
            responseEntity.setContent(stu);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            response.setStatus(400);
            responseEntity.setErrorMsg("add stu error. " + e.getMessage());
        }
        
        return "json_success";
        
    }
    
    public String del() throws IOException
    {
        
        HttpServletResponse response = ServletActionContext.getResponse();
        
        try
        {
            stuService.delStudent(stuDTO.getId());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            response.setStatus(400);
            responseEntity.setErrorMsg("add stu error. " + e.getMessage());
        }
        
        return "json_success";
        
    }
    
    public String batDel() throws IOException
    {
        
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        
        try
        {
            String[] ids = request.getParameterValues("stuIds[]");
            stuService.batchDelStudents(ids);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            response.setStatus(400);
            responseEntity.setErrorMsg("add stu error. " + e.getMessage());
        }
        
        return "json_success";
        
    }
    
    public String bindIdCard() throws IOException
    {
        
        HttpServletResponse response = ServletActionContext.getResponse();
        
        try
        {
            stuService.bindStuIdcard(stuDTO.getId());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            response.setStatus(400);
            responseEntity.setErrorMsg("bindStuIdCard error. " + e.getMessage());
        }
        
        return "json_success";
    }
    
    public String unBindIdCard() throws IOException
    {
        
        HttpServletResponse response = ServletActionContext.getResponse();
        
        try
        {
            stuService.unBindStuIdcard(stuDTO.getId());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            response.setStatus(400);
            responseEntity.setErrorMsg("unBindStuIdCard error. " + e.getMessage());
        }
        
        return "json_success";
    }
    
    public ResponseEntity<?> getResponseEntity()
    {
        return responseEntity;
    }
    
    public void setResponseEntity(ResponseEntity<Object> responseEntity)
    {
        this.responseEntity = responseEntity;
    }
    
    public StudentDTO getStuDTO()
    {
        return stuDTO;
    }
    
    public void setStuDTO(StudentDTO stuDTO)
    {
        this.stuDTO = stuDTO;
    }
    
    public Page<Student> getPage()
    {
        return page;
    }
    
    public void setPage(Page<Student> page)
    {
        this.page = page;
    }
    
}
