package com.my.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "t_student_idcard")
public class StudentIdCard
{
    @Id
    @Column(name = "pk_student_idcard_id")
    private String id;
    
    @Column(name = "student_num")
    private String num;
    
    @JsonIgnore
    @OneToOne(mappedBy = "studentIdCard")
    @Fetch(value=FetchMode.JOIN)
    private Student student;
    
    public StudentIdCard()
    {
    }
    
    public StudentIdCard(String id, String num)
    {
        this.id = id;
        this.num = num;
    }
    
    public String getId()
    {
        return id;
    }
    
    public String getNum()
    {
        return num;
    }
    
    public void setNum(String num)
    {
        this.num = num;
    }
    
    public Student getStudent()
    {
        return student;
    }
    
    public void setStudent(Student student)
    {
        this.student = student;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    @Override
    public String toString()
    {
        return "StudentIdCard [id=" + id + ", num=" + num + ", student="  + "]";
    }
    
}
