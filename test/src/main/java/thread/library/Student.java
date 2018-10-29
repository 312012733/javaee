package thread.library;

public class Student implements Runnable
// extends Thread
{
    
    private Library library;
    
    public Student(Library library)
    {
        if (null == library)
        {
            throw new IllegalArgumentException("参数错误. 图书馆 不能是 空。。");
        }
        
        this.library = library;
    }
    
    @Override
    public void run()
    {
        // 借书
        Book book = library.borrowing();
        
        try
        {
            Thread.sleep(1000 * 12);// 看了6秒的书
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        
        // 还书
        library.returning(book);
      
        
    }
    
    public static void main(String[] args)
    {
        
        Library library = new Library();
        
        for (int i = 0; i < 20; i++)
        {
            new Thread(new Student(library)).start();
            
            try
            {
                Thread.sleep(500);
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
