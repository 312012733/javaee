package thread;

public class Thread2 extends Thread// extends Thread
{
  
    @Override
    public void run()
    {
        System.out.println(this.getClass().getSimpleName()+"----begin----");
        
        try
        {
            Thread.sleep(1000*5);
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        System.out.println(this.getClass().getSimpleName()+"----end----");
       
    }
    
   
}
