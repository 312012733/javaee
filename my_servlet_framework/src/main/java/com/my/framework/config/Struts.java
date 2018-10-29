package com.my.framework.config;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("struts")
public class Struts
{
    @XStreamImplicit(itemFieldName = "action")
    private List<Action> actions;
    
    public List<Action> getActions()
    {
        return actions;
    }
    
    public void setActions(List<Action> actions)
    {
        this.actions = actions;
    }
    
    @XStreamAlias("action")
    public class Action
    {
        @XStreamAlias("name")
        @XStreamAsAttribute
        String name;
        
        @XStreamAlias("className")
        @XStreamAsAttribute
        String className;
        
        @XStreamAlias("method")
        @XStreamAsAttribute
        String methodName;
        
        public String getName()
        {
            return name;
        }
        
        public void setName(String name)
        {
            this.name = name;
        }
        
        public String getClassName()
        {
            return className;
        }
        
        public void setClassName(String className)
        {
            this.className = className;
        }
        
        public String getMethodName()
        {
            return methodName;
        }
        
        public void setMethodName(String methodName)
        {
            this.methodName = methodName;
        }
        
        @Override
        public String toString()
        {
            return "Action [name=" + name + ", className=" + className + ", methodName=" + methodName + "]";
        }
        
    }
    
    @Override
    public String toString()
    {
        return "Struts [actions=" + actions + "]";
    }
    
}
