//package utils;
//
//import javax.security.auth.login.Configuration;
//
//import com.my.framework.config.SessionFactory;
//import com.my.framework.config.XmlUtils;
//
//public class MyFrameworkUtils
//{
//    private static Configuration config = null;
//    
//    private static SessionFactory sf = null;
//    
//    static
//    {
//        try
//        {
//            // 加载配置
//            // 加载 配置 文件
//            XmlUtils xmlUtil = new XmlUtils("mybatis.xml");
//            config = xmlUtil.parse(Configuration.class);
//            
//            // 构建sessionFactory
//            sf = config.buildSessionFactory();
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        
//    }
//    
//    public static SessionFactory getSf()
//    {
//        return sf;
//    }
//    
//}
