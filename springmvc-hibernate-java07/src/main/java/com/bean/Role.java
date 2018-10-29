package com.bean;

public class Role
{
    private String name;
    
    public Role()
    {
    }
    
    public Role(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Override
    public String toString()
    {
        return "Role [name=" + name + "]";
    }
    
}
