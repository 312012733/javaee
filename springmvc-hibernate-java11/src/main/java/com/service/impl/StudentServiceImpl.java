package com.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bean.MyClass;
import com.bean.Student;
import com.bean.StudentIdCard;
import com.bean.Teacher;
import com.dao.IMyClassDao;
import com.dao.IStudentDao;
import com.dao.IStudentIdCardDao;
import com.dao.ITeacherDao;
import com.service.IStudentService;
import com.vo.Page;
import com.vo.StudentDTO;

@Service
public class StudentServiceImpl implements IStudentService
{
    
    @Autowired
    private IStudentDao stuDao;
    
    @Autowired
    private IMyClassDao classDao;
    
    @Autowired
    private ITeacherDao teacherDao;
    
    @Autowired
    private IStudentIdCardDao idCardDao;
    
    @Override
    public void addStudent(StudentDTO stuDTO)
    {
        Student stu = new Student();
        
        stu.setId(UUID.randomUUID().toString());
        stu.setCreateTime(System.currentTimeMillis());
        stu.setLastModifyTime(stu.getCreateTime());
        
        stu.setAge(stuDTO.getAge());
        stu.setName(stuDTO.getName());
        stu.setGender(stuDTO.getGender());
        
        // 处理班级的关系
        if (null != stuDTO.getMyClass() && !StringUtils.isEmpty(stuDTO.getMyClass().getId()))
        {
            MyClass myClass = classDao.findMyClassById(stuDTO.getMyClass().getId());
            
            if (null == myClass)
            {
                throw new SecurityException("class id is error. class id is " + stuDTO.getMyClass().getId());
            }
            
            stu.setMyClass(myClass);
        }
        
        // 处理老师的关系
        if (null != stuDTO.getTeacherIds() && stuDTO.getTeacherIds().length > 0)
        {
            List<Teacher> teachers = teacherDao.findTeacherByTeacherIds(stuDTO.getTeacherIds());
            
            if (stuDTO.getTeacherIds().length != teachers.size())
            {
                throw new SecurityException("teacher ids is error. ");
            }
            
            stu.setTeachers(teachers);
        }
        
        stuDao.addStudent(stu);
    }
    
    @Override
    public StudentDTO findStudentById(String stuId)
    {
        
        Student stu = stuDao.findStudentById(stuId);
        
        if (stu == null)
        {
            return null;
        }
        
        List<Teacher> unOwerTeachers = teacherDao.findTeachersByExcludStuIds(stuId);
        
        StudentDTO stuDTO = new StudentDTO();
        
        stuDTO.setAge(stu.getAge());
        stuDTO.setName(stu.getName());
        stuDTO.setGender(stu.getGender());
        stuDTO.setMyClass(stu.getMyClass());
        
        stuDTO.setOwerTeachers(stu.getTeachers());
        stuDTO.setUnOwerTeachers(unOwerTeachers);
        
        return stuDTO;
    }
    
    @Override
    public void updateStudent(StudentDTO stuDTO)
    {
        String stuId = stuDTO.getId();
        
        Student dbStu = stuDao.findStudentById(stuId);
        
        if (null == dbStu)
        {
            throw new SecurityException("update stu error. stuId is not found. stuId:" + stuId);
        }
        
        dbStu.setAge(stuDTO.getAge());
        dbStu.setName(stuDTO.getName());
        dbStu.setLastModifyTime(System.currentTimeMillis());
        
        // 处理班級的关系
        
        MyClass myClass = null;
        
        if (null != stuDTO.getMyClass() && !StringUtils.isEmpty(stuDTO.getMyClass().getId()))
        {
            myClass = classDao.findMyClassById(stuDTO.getMyClass().getId());
            
            if (null == myClass)
            {
                throw new SecurityException("class id is error. class id is " + stuDTO.getMyClass().getId());
            }
            
            dbStu.setMyClass(myClass);
        }
        else
        {
            dbStu.setMyClass(null);
        }
        
        // 处理老师的关系
        
        if (null != stuDTO.getTeacherIds() && stuDTO.getTeacherIds().length > 0)
        {
            List<Teacher> teachers = teacherDao.findTeacherByTeacherIds(stuDTO.getTeacherIds());
            
            if (stuDTO.getTeacherIds().length != teachers.size())
            {
                throw new SecurityException("teacher ids is error. ");
            }
            
            dbStu.setTeachers(teachers);
        }
        else
        {
            dbStu.setTeachers(null);
        }
        
        stuDao.updateStudent(dbStu);
    }
    
    @Override
    public void delStudent(String stuId)
    {
        
        Student dbStu = stuDao.findStudentById(stuId);
        
        if (null == dbStu)
        {
            throw new SecurityException("del stu error. stuId is not found. stuId:" + stuId);
        }
        
        // 解除和学生证的关系
        StudentIdCard idCard = dbStu.getStudentIdCard();
        
        if (null != idCard)
        {
            idCard.setStudent(null);
            dbStu.setStudentIdCard(null);
            
            idCardDao.delete(idCard);
        }
        
        // 解除与班级的关系
        MyClass myClass = dbStu.getMyClass();
        if (null != myClass)
        {
            myClass.getStudents().remove(dbStu);
        }
        
        // 处理老师的关系
        List<Teacher> teachers = dbStu.getTeachers();
        
        for (Teacher teacher : teachers)
        {
            teacher.getStudents().remove(dbStu);
        }
        
        // 删除学生
        stuDao.delStudent(dbStu);
    }
    
    @Override
    public void batchDelStudents(String[] ids)
    {
        
        if (null == ids || ids.length <= 0)
        {
            throw new SecurityException("batch del stu error. stuId is null.");
        }
        
        for (String stuId : ids)
        {
            delStudent(stuId);
        }
    }
    
    @Override
    public Page<Student> findStudentsByPage(Page<Student> page, Student condition)
    {
        Page<Student> stuPage = stuDao.findStudentsByPage(page, condition);
        
        return stuPage;
    }
    
    @Override
    public void bindStuIdcard(String stuId)
    {
        Student stu = stuDao.findStudentById(stuId);
        
        // 验证
        
        if (null == stu)
        {
            throw new SecurityException("stu is not found. id is " + stuId);
        }
        
        if (null != stu.getStudentIdCard())
        {
            
            throw new SecurityException("idcard is exist. ");
        }
        
        // 生成一个的学生证
        StudentIdCard studentIdCard = buildStuIdCard();
        
        // 设置学生和学生证关系
        stu.setStudentIdCard(studentIdCard);
        stu.setLastModifyTime(System.currentTimeMillis());
        
        stuDao.updateStudent(stu);
    }
    
    @Override
    public void unBindStuIdcard(String stuId)
    {
        Student stu = stuDao.findStudentById(stuId);
        
        // 验证
        if (null == stu)
        {
            throw new SecurityException("stu is not found. id is " + stuId);
        }
        
        StudentIdCard idCard = stu.getStudentIdCard();
        
        if (null == idCard)
        {
            throw new SecurityException("idcard is not exist. ");
        }
        
        // 解除学生和学生证关系
        stu.setStudentIdCard(null);
        stu.setLastModifyTime(System.currentTimeMillis());
        
        // 删除学生证
        idCardDao.delete(idCard);
    }
    
    private StudentIdCard buildStuIdCard()
    {
        String id = UUID.randomUUID().toString();
        String num = System.currentTimeMillis() + "";
        
        StudentIdCard idcard = new StudentIdCard(id, num);
        
        return idcard;
    }
    
}
