package com.my.xstream;

import java.io.InputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class TestMain
{
    public static void main(String[] args)
    {
        Alipay alipay = null;
        
        try
        {
            
            XStream xstream = new XStream(new DomDriver());
            
            xstream.processAnnotations(Alipay.class);
            InputStream in = TestMain.class.getClassLoader().getResourceAsStream("alipay.xml");
            
            alipay = (Alipay) xstream.fromXML(in);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        System.out.println(alipay);
    }
}
