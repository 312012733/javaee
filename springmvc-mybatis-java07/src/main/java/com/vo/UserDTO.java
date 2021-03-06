package com.vo;

public class UserDTO
{
    private String id;
    
    private String username;
    
    private String password;
    
    private String checkCode;
    
    public UserDTO()
    {
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
    
    public String getCheckCode()
    {
        return checkCode;
    }
    
    public void setCheckCode(String checkCode)
    {
        this.checkCode = checkCode;
    }
    
    @Override
    public String toString()
    {
        return "UserDTO [id=" + id + ", username=" + username + ", password=" + password + ", checkCode=" + checkCode
                + "]";
    }
    
}
