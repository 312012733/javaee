package com.dao;

import java.util.List;

import com.bean.Teacher;

public interface ITeacherDao
{
    Teacher findTeacherById(String teacherId);
    
    List<Teacher> findAllTeachers();
    
    List<Teacher> findExcludeTeachersByStuId(String stuId);
}
