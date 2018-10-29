package com.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bean.Teacher;
import com.service.ITeacherService;
import com.vo.ErrorHanlder;

@RestController
public class TeacherController
{
    @Autowired
    private ITeacherService teacherService;
    
    @RequestMapping(value = "/teacher/all", method = RequestMethod.GET)
    public ResponseEntity<Object> findAll() throws ServletException, IOException
    {
        try
        {
            List<Teacher> teacherList = teacherService.findAllTeachers();
            return new ResponseEntity<Object>(teacherList, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            return new ResponseEntity<Object>(new ErrorHanlder(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
}
