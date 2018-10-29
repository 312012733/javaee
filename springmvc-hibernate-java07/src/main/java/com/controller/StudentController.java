package com.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bean.Student;
import com.service.IStudentService;
import com.vo.ErrorHanlder;
import com.vo.Page;
import com.vo.StudentDTO;

@RestController
public class StudentController
{
    @Autowired
    private IStudentService stuService;
    
    @RequestMapping(value = "/student/page", method = RequestMethod.GET)
    public ResponseEntity<Object> findByPage(Page<Student> page, StudentDTO condition, HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            Page<Student> stuList = stuService.findStudentByPage(page, condition);
            
            stuList.getPageList().get(0).getTeachers().size();
            
            
            return new ResponseEntity<Object>(stuList, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            return new ResponseEntity<Object>(new ErrorHanlder(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value = "/{stuId}/student", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable String stuId, StudentDTO condition, HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            StudentDTO stu = stuService.findeStudentById(stuId);
            return new ResponseEntity<Object>(stu, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            return new ResponseEntity<Object>(new ErrorHanlder(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value = "/student", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Object> add(@RequestBody StudentDTO stuParam, HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            stuService.addStudent(stuParam);
            return new ResponseEntity<Object>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            return new ResponseEntity<Object>(new ErrorHanlder(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value = "/{stuId}/student", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<Object> update(@PathVariable String stuId, @RequestBody StudentDTO stuParam,
            HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            stuParam.setId(stuId);
            stuService.updateStudent(stuParam);
            return new ResponseEntity<Object>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            return new ResponseEntity<Object>(new ErrorHanlder(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value = "/{stuId}/student", method = RequestMethod.DELETE, consumes = "application/json")
    public ResponseEntity<Object> delete(@PathVariable String stuId, StudentDTO stuParam, HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            stuService.deleteStudent(stuId);
            return new ResponseEntity<Object>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            return new ResponseEntity<Object>(new ErrorHanlder(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value = "/students", method = RequestMethod.DELETE, consumes = "application/json")
    public ResponseEntity<Object> batchDelete(@RequestBody String[] stuIds, StudentDTO stuParam,
            HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            stuService.batchDeleteStudenst(stuIds);
            return new ResponseEntity<Object>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            return new ResponseEntity<Object>(new ErrorHanlder(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value = "/binding/{stuId}/idcard", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Object> bindingIdcard(@PathVariable String stuId)
    {
        try
        {
            stuService.bindingIdcard(stuId);
            return new ResponseEntity<Object>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            return new ResponseEntity<Object>(new ErrorHanlder(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value = "/unbinding/{stuId}/idcard", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Object> unbindingIdcard(@PathVariable String stuId)
    {
        try
        {
            stuService.unbindingIdcard(stuId);
            return new ResponseEntity<Object>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            return new ResponseEntity<Object>(new ErrorHanlder(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
}
