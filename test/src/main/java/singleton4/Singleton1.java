package singleton4;


/**
 *  速度快
 *  
 *  不能懒加载
 * */
public class Singleton1
{
    private static Singleton1 s1 = new Singleton1();
    
    private Singleton1()
    {
        
    }
    
    public static Singleton1 getInstance()
    {
        return s1;
    }
}
