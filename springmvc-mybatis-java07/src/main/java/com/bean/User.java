package com.bean;

import java.util.ArrayList;
import java.util.List;

public class User
{
    private String id;
    
    private String username;
    
    private String password;
    
    private Boolean active;
    
    private Long creatTime;
    
    private Role role;
    
    private List<Group> groups;
    
    public User()
    {
    }
    
    public User(String id, String username, String password)
    {
        this.id = id;
        this.username = username;
        this.password = password;
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
    
    public Role getRole()
    {
        return role;
    }
    
    public void setRole(Role role)
    {
        this.role = role;
    }
    
    public List<Group> getGroups()
    {
        if (null == this.groups)
        {
            this.groups = new ArrayList<>();
        }
        
        return groups;
    }
    
    public void setGroups(List<Group> groups)
    {
        this.groups = groups;
    }
    
    public Boolean getActive()
    {
        return active;
    }
    
    public void setActive(Boolean active)
    {
        this.active = active;
    }
    
    public Long getCreatTime()
    {
        return creatTime;
    }
    
    public void setCreatTime(Long creatTime)
    {
        this.creatTime = creatTime;
    }
    
    @Override
    public String toString()
    {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", active=" + active
                + ", creatTime=" + creatTime + ", role=" + role + ", groups=" + groups + "]";
    }
    
}
