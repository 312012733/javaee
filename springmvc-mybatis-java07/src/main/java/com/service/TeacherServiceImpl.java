package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bean.Teacher;
import com.dao.ITeacherDao;

@Service
public class TeacherServiceImpl implements ITeacherService
{
    
    @Autowired
    private ITeacherDao teacherDao;
    
    @Transactional(readOnly = true)
    @Override
    public Teacher findTeacherById(String teacherId)
    {
        return teacherDao.findTeacherById(teacherId);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Teacher> findAllTeachers()
    {
        return teacherDao.findAllTeachers();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Teacher> findExcludeTeachersByStuId(String stuId)
    {
        return teacherDao.findExcludeTeachersByStuId(stuId);
    }
    
}
