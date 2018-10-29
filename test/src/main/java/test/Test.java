package test;

class A
{
    static
    {
        System.out.println("11111111111111111111");
    }
    
    {
        System.out.println("222222222222222222222222");
    }
    
    public A()
    {
        System.out.println("3333333333333333333333");
    }
    
    String a = "a";
    
    String getString()
    {
        return a;
    }
}

class B extends A
{
    static
    {
        System.out.println("4444444444444444444444444");
    }
    
    {
        System.out.println("5555555555555555555");
    }
    
    public B()
    {
        System.out.println("666666666666666666666666");
    }
    
    String a = "b";
    
    String getString()
    {
        return this.a;
    }
    
    String getString(String p)
    {
        return null;
    }
    
    Integer getString(Integer p)
    {
        return null;
    }
    
}

public class Test
{
    
    // public static void main(String[] args)
    // {
    // B bb = new B();
    // A aa = bb;
    //
    // System.out.println(bb == aa);
    //
    // System.out.println(bb.a);
    // System.out.println(bb.getString());
    //
    // System.out.println(aa.a);
    // System.out.println(aa.getString());
    // }
    
    public static void main(String[] args)
    {
        A a1 = new A();//
        A a2 = new B();
        B b = new B();
    }
}