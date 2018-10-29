package singleton4;

/**
 * 速度快 线程安全
 * 
 */
public class Singleton4
{
    
    private static class InnerSingleton4
    {
        static Singleton4 instance = new Singleton4();
    }
    
    private Singleton4()
    {
        
    }
    
    public static Singleton4 getInstance()
    {
        return InnerSingleton4.instance;
    }
    
}
