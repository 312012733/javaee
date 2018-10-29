package com.dao;

import java.util.List;

import com.bean.MyClass;

public interface IMyClassDao
{
    List<MyClass> findAllClasses();
    
    MyClass findClassById(String classId);
}
