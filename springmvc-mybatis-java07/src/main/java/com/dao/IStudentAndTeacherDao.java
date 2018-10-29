package com.dao;

public interface IStudentAndTeacherDao
{
    void batchSaveStuAndTeacher(String studentId, String[] teacherIds);
    
    void deleteByStudentId(String studentId);
}
