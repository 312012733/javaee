package com.vo;

public class UserDTO
{
    private String checkCode;
    private String username;
    private String password;
    
    public UserDTO()
    {
    }
    
    public UserDTO(String checkCode, String username, String password)
    {
        this.checkCode = checkCode;
        this.username = username;
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
    
    @Override
    public String toString()
    {
        return "UserDTO [checkCode=" + checkCode + ", username=" + username + ", password=" + password + "]";
    }
    
}
