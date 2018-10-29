package singleton4;

/**
 * 速度 不太慢
 * 
 * 能懒加载
 */
public class Singleton3
{
    private static Singleton3 instance;
    
    private Singleton3()
    {
        
    }
    
    public static Singleton3 getInstance()
    {
        if (null == instance)
        {
            // a b
            synchronized (Singleton3.class)
            {
                // b //a
                if (null == instance)
                {
                    instance = new Singleton3();
                }
            }
        }
        
        return instance;
    }
}
