package com.service;

import java.util.List;

import com.bean.Teacher;

public interface ITeacherService
{
    Teacher findTeacherById(String teacherId);
    
    List<Teacher> findAllTeachers();
    
    List<Teacher> findExcludeTeachersByStuId(String stuId);
}
