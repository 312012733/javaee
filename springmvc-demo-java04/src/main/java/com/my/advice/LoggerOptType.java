package com.my.advice;

public enum LoggerOptType
{
    SAVE("0"), UPDATE("1"), DELETE("2"), LOGIN("3"), LOGIN_OUT("4");
    
    private String value;
    
    private LoggerOptType(String value)
    {
        this.value = value;
    }
    
    public String getValue()
    {
        return value;
    }
    
}
