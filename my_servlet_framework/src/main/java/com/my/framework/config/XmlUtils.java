package com.my.framework.config;

import java.io.InputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.AbstractXmlDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;

@SuppressWarnings("deprecation")
public class XmlUtils
{
    private InputStream in;
    
    private AbstractXmlDriver xmlDriver;
    
    public XmlUtils(String fileName)
    {
        this(XmlUtils.class.getClassLoader().getResourceAsStream(fileName), new DomDriver());
    }
    
    public XmlUtils(InputStream in, AbstractXmlDriver xmlDriver)
    {
        if (null == in)
        {
            throw new IllegalArgumentException("inputStream is null. ");
        }
        
        if (null == xmlDriver)
        {
            throw new IllegalArgumentException("xmlDriver is null. ");
        }
        
        this.in = in;
        this.xmlDriver = xmlDriver;
    }
    
    @SuppressWarnings("unchecked")
    public <T> T parse(Class<T> beanClass)
    {
        XStream xstream = new XStream(xmlDriver);
        
        xstream.processAnnotations(beanClass);
        
        return (T) xstream.fromXML(in);
    }
}