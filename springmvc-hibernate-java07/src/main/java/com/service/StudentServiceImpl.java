package com.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bean.MyClass;
import com.bean.Student;
import com.bean.StudentIdCard;
import com.bean.Teacher;
import com.dao.IMyClassDao;
import com.dao.IStudentDao;
import com.dao.IStudentIdCardDao;
import com.dao.ITeacherDao;
import com.vo.Page;
import com.vo.StudentDTO;

import net.sf.ehcache.search.SearchException;

//@Component
@Service
// @Scope(scopeName=ConfigurableBeanFactory.SCOPE_SINGLETON)
public class StudentServiceImpl implements IStudentService
{
    @Autowired
    private IMyClassDao myClassDao;
    @Autowired
    private ITeacherDao teacherDao;
    @Autowired
    private IStudentDao stuDao;
    @Autowired
    private IStudentIdCardDao stuIdCardDao;
    
    @Transactional(readOnly = true)
    @Override
    public Page<Student> findStudentByPage(Page<Student> pageParam, StudentDTO condition)
    {
        
        Page<Student> page = stuDao.findStudentByPage(pageParam, condition);
        
        return page;
    }
    
    @Transactional(readOnly = true)
    @Override
    public StudentDTO findeStudentById(String stuId)
    {
        Student stu = stuDao.findeStudentById(stuId);
        
        if (null == stu)
        {
            return null;
        }
        
        List<Teacher> excludeTeachers = teacherDao.findExcludeTeachersByStuId(stuId);
        
        StudentDTO studentDTO = stu.toStudentDTO();
        
        studentDTO.setExcludeTeachers(excludeTeachers);
        
        return studentDTO;
    }
    
    @Transactional(readOnly = false)
    @Override
    public void addStudent(StudentDTO stu)
    {
        Student student = stu.toStudent();
        student.setId(UUID.randomUUID().toString());
        student.setCreateTime(System.currentTimeMillis());
        student.setLastModifyTime(System.currentTimeMillis());
        
        // 添加班级的关系
        if (null != stu.getMyClass() && !StringUtils.isEmpty(stu.getMyClass().getId()))
        {
            String myClassId = stu.getMyClass().getId();
            MyClass dbClass = myClassDao.findClassById(myClassId);
            
            if (null == dbClass)
            {
                throw new SearchException("myclass is not found. classId:" + myClassId);
            }
            
            student.setMyClass(dbClass);
        }
        else
        {
            student.setMyClass(null);
        }
        
        // 添加和老师的关系
        if (null != stu.getTeacherIds() && stu.getTeacherIds().length > 0)
        {
            for (String teacherId : stu.getTeacherIds())
            {
                Teacher dbTeacher = teacherDao.findTeacherById(teacherId);
                
                if (null == dbTeacher)
                {
                    throw new SearchException("teacher is not found. teacherId:" + teacherId);
                }
                
                student.getTeachers().add(dbTeacher);
            }
        }
        
        stuDao.addStudent(student);
    }
    
    @Transactional(readOnly = false)
    @Override
    public void updateStudent(StudentDTO stu)
    {
        Student stuDB = stuDao.findeStudentById(stu.getId());
        
        if (null == stuDB)
        {
            throw new SecurityException("student is not found. stuId:" + stu.getId());
        }
        
        stuDB.setName(stu.getName());
        stuDB.setAge(stu.getAge());
        stuDB.setLastModifyTime(System.currentTimeMillis());
        
        // 修改和班级的关系
        if (null != stu.getMyClass() && !StringUtils.isEmpty(stu.getMyClass().getId()))
        {
            String myClassId = stu.getMyClass().getId();
            MyClass dbClass = myClassDao.findClassById(myClassId);
            
            if (null == dbClass)
            {
                throw new SearchException("myclass is not found. classId:" + myClassId);
            }
            
            stuDB.setMyClass(dbClass);
        }
        else
        {
            stuDB.setMyClass(null);
        }
        
        // 修改和老师的关系
        stuDB.getTeachers().clear();
        
        if (null != stu.getTeacherIds() && stu.getTeacherIds().length > 0)
        {
            
            for (String teacherId : stu.getTeacherIds())
            {
                Teacher dbTeacher = teacherDao.findTeacherById(teacherId);
                
                if (null == dbTeacher)
                {
                    throw new SearchException("teacher is not found. teacherId:" + teacherId);
                }
                
                stuDB.getTeachers().add(dbTeacher);
            }
        }
        
        stuDao.updateStudent(stuDB);
        
    }
    
    @Transactional(readOnly = false)
    @Override
    public void deleteStudent(String stuId)
    {
        Student stuDB = stuDao.findeStudentById(stuId);
        
        if (null == stuDB)
        {
            throw new SecurityException("student is not found. stuId:" + stuId);
        }
        
        // 删除学生证
        StudentIdCard idCard = stuDB.getStudentIdCard();
        
        if (null != idCard)
        {
            idCard.getStudent().setStudentIdCard(null);
            idCard.setStudent(null);
            
            stuIdCardDao.deleteStudentIdCard(idCard);
        }
        
        // 清理和班级的关系
        MyClass myClass = stuDB.getMyClass();
        
        if (null != myClass)
        {
            myClass.getStudents().remove(stuDB);
        }
        
        // 清理和老师的关系
        if (null != stuDB.getTeachers() && !stuDB.getTeachers().isEmpty())
        {
            for (Teacher t : stuDB.getTeachers())
            {
                t.getStudents().remove(stuDB);
            }
        }
        
        stuDao.deleteStudent(stuDB);
        
    }
    
    @Transactional(readOnly = false)
    @Override
    public void batchDeleteStudenst(String[] stuIds)
    {
        
        for (String stuId : stuIds)
        {
            deleteStudent(stuId);
        }
        
    }
    
    @Transactional
    @Override
    public void bindingIdcard(String stuId)
    {
        Student stuDB = stuDao.findeStudentById(stuId);
        
        if (null == stuDB)
        {
            throw new SecurityException("student is not found. stuId:" + stuId);
        }
        
        if (null != stuDB.getStudentIdCard())
        {
            throw new SecurityException("studentIdcard is exist. ");
        }
        
        // 生成一个学生证 并 保存
        StudentIdCard stuIdCard = createNewIdcard();
        stuIdCardDao.addStudentIdCard(stuIdCard);
        
        // 设置关系 并 保存
        stuDB.setStudentIdCard(stuIdCard);
        stuDao.updateStudent(stuDB);
    }
    
    @Transactional
    @Override
    public void unbindingIdcard(String stuId)
    {
        Student stuDB = stuDao.findeStudentById(stuId);
        
        if (null == stuDB)
        {
            throw new SecurityException("student is not found. stuId:" + stuId);
        }
        
        StudentIdCard idCard = stuDB.getStudentIdCard();
        
        if (null == idCard)
        {
            throw new SecurityException("studentIdcard is not exist. ");
        }
        
        // 解除关系 并 保存
        idCard.setStudent(null);
        stuDB.setStudentIdCard(null);
        stuDao.updateStudent(stuDB);
        
        // 删除学生证
        stuIdCardDao.deleteStudentIdCard(idCard);
        
    }
    
    private StudentIdCard createNewIdcard()
    {
        StudentIdCard stuIdCard = new StudentIdCard();
        
        String id = UUID.randomUUID().toString();
        stuIdCard.setId(id);
        stuIdCard.setNum(String.valueOf(System.currentTimeMillis()));
        
        return stuIdCard;
    }
    
    // public static void main(String[] args)
    // {
    // Set<String> set = new HashSet<>();
    //
    // for (int i = 0; i < 10000000; i++)
    // {
    //// String id = System.currentTimeMillis()+"";
    // String id = UUID.randomUUID().toString();
    // if (!set.add(id))
    // {
    // throw new SecurityException();
    // }
    //
    // System.out.println(i+"=="+id);
    // }
    // }
}
