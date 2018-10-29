package com.my.xstream;

import java.io.InputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.AbstractXmlDriver;

@SuppressWarnings("deprecation")
public class Configruation<T>
{
    private InputStream in;
    
    private AbstractXmlDriver xmlDriver;
    
    public Configruation(InputStream in, AbstractXmlDriver xmlDriver)
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
    public T parse(Class<T> beanClass)
    {
        XStream xstream = new XStream(xmlDriver);
        
        xstream.processAnnotations(beanClass);
        
        return (T) xstream.fromXML(in);
    }
}