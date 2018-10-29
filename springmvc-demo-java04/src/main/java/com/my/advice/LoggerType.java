package com.my.advice;

public enum LoggerType
{
    
    SYS("sys"), 
    USER("user");
    
    private String value;

    private LoggerType(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

}
