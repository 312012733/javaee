package thread.library;

public interface ObjectPool<T>
{
    T borrowing();//重池子里面拿
    
    void returning(T t);//往池子里面放
}
