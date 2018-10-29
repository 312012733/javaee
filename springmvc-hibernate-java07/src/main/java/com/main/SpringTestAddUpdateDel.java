package com.main;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bean.StudentIdCard;
import com.config.HibernateConfig;

public class SpringTestAddUpdateDel
{
    
    public static void main(String[] args) throws InterruptedException
    {
        // ApplicationContext context = new
        // ClassPathXmlApplicationContext("application-context.xml");
        
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(HibernateConfig.class);
        
        // 工厂
        SessionFactory sf = context.getBean(SessionFactory.class);
        
        // session
        Session session = sf.getCurrentSession();
        
        Transaction transaction = session.beginTransaction();
        
        try
        {
            /**************************************************************/
            // Student newStu = new Student("s8", "zhang3", 11, true);
            //
            // StudentIdCard icCard = new StudentIdCard("c8",
            // System.currentTimeMillis()+"");
            //
            // icCard.setStudent(newStu);
            // newStu.setStudentIdCard(icCard);
            //
            // session.save(icCard);
            
            /**************************************************************/
            // StudentIdCard idCard = session.get(StudentIdCard.class, "c8");
            // StudentIdCard idCard2 = session.get(StudentIdCard.class, "c7");
            //
            // idCard.setNum("bbb");
            //
            // if (null != idCard.getStudent())
            // {
            // idCard.getStudent().setStudentIdCard(idCard2);
            // }
            //
            // session.update(idCard);
            
            /**************************************************************/
            // Student stu = session.get(Student.class, "s8");//--->c7
            //
            // if (null != stu)
            // {
            // stu.getStudentIdCard().setStudent(null);//
            //
            // session.delete(stu);
            // }
            
            StudentIdCard studentIdCard = session.get(StudentIdCard.class, "c8");
            
            studentIdCard.getStudent().setStudentIdCard(null);
            
            session.delete(studentIdCard);
            
            transaction.commit();
        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            transaction.rollback();
        }
        
        context.close();
        System.exit(0);
    }
}
