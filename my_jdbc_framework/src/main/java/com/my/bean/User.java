package com.my.bean;

public class User
{
    private String id;
    
    private String username;
    
    private String password;
    
    private int age;
    
    private boolean sex;
    
    public User()
    {
    }
    
    public User(String id, String username, String password, Integer age, Boolean sex)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.age = age;
        this.sex = sex;
    }
    

    
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public boolean isSex()
    {
        return sex;
    }

    public void setSex(boolean sex)
    {
        this.sex = sex;
    }

    @Override
    public String toString()
    {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", age=" + age + ", sex=" + sex
                + "]";
    }
    
}
