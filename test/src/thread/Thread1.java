package thread;

public class Thread1 extends Thread
{
    Thread2 t2 = new Thread2();
    
    public Thread1()
    {
    }
    
    @Override
    public void run()
    {
        System.out.println(this.getClass().getSimpleName() + "----begin----");
        
        try
        {
            t2.start();
            t2.join();
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println(this.getClass().getSimpleName() + "----end----");
    }
    
    public static void main(String[] args)
    {
        Thread1 t1 = new Thread1();
        
        t1.start();
        
    }
}
