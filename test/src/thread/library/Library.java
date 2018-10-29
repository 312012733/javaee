package thread.library;

import java.util.ArrayList;
import java.util.List;

public class Library implements ObjectPool<Book>
{
    private List<Book> books = new ArrayList<>();
    
    public Library()
    {
        for (int i = 0; i < 10; i++)
        {
            books.add(new Book("天龙八部-" + (i + 1)));
        }
    }
    
    @Override
    public synchronized Book borrowing()// 借书
    {
        while (books.size() <= 0)
        {
            try
            {
                System.out.println(Thread.currentThread() + "-----没书了--只好休息一下 -  -");
                this.wait();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        
        Book book = books.remove(0);
        
        System.out.println(Thread.currentThread().getName() + "--借了一本-【" + book.getName() + "】");
        
        return book;
    }
    
    @Override
    public synchronized void returning(Book book)// 还书
    {
        books.add(book);
        
        System.out.println(Thread.currentThread().getName() + "--还了一本-【" + book.getName() + "】");
        
        // this.notify();
        this.notifyAll();
    }
    
}
