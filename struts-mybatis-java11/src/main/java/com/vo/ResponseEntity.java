package com.vo;

public class ResponseEntity<T>
{
    private String errorMsg;
    
    private T content;
    
    public ResponseEntity()
    {
    }
    
    public String getErrorMsg()
    {
        return errorMsg;
    }
    
    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }
    
    public T getContent()
    {
        return content;
    }
    
    public void setContent(T content)
    {
        this.content = content;
    }
    
    @Override
    public String toString()
    {
        return "ResponseEntity [errorMsg=" + errorMsg + ", content=" + content + "]";
    }
    
}
