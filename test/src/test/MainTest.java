package test;

import java.io.UnsupportedEncodingException;

public class MainTest
{
    
    public static final String EMPTY = "";
    public static final String ZERO = "零";
    public static final String ONE = "壹";
    public static final String TWO = "贰";
    public static final String THREE = "叁";
    public static final String FOUR = "肆";
    public static final String FIVE = "伍";
    public static final String SIX = "陆";
    public static final String SEVEN = "柒";
    public static final String EIGHT = "捌";
    public static final String NINE = "玖";
    public static final String TEN = "拾";
    public static final String HUNDRED = "佰";
    public static final String THOUSAND = "仟";
    public static final String TEN_THOUSAND = "万";
    public static final String HUNDRED_MILLION = "亿";
    
    public static final String[] NUM_DIC =
    { ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE };
    
    public static final String[] POSITION_DIC =
    { "", TEN, HUNDRED, THOUSAND };
    
    public static final String[] UNIT_DIC =
    { "圆", TEN_THOUSAND, HUNDRED_MILLION, TEN_THOUSAND + HUNDRED_MILLION, HUNDRED_MILLION + HUNDRED_MILLION, "" };
    
    
    /**
     * 1 输入 123456 返回 654321
     */
    static String func(String s)
    {
        return "";
    }
    
    /**
     * 2 写一个函数去掉一个字符串中单词间多余的空格，使得相邻两个单词间有且只有一个空格。例如当输入字符串是
     * “Hello!__Game_programming__world!”时，调用该函数后字符串变为“Hello!_Game_programming_world!”。
     * 
     * 去除字符串中多余的空格
     * 
     * @param s
     *            需要处理的字符串
     * @return 处理后的字符串
     */
    static String doString(String s)
    {
        return "";
    }
    
    /**
     * 3 编写一个截取字符串的函数，输入为一个字符串和字节数，输出为按字节截取的字符串。
     * 但是要保证汉字不被截半个，如“我ABC”4，应该截为“我AB”，输入“我ABC汉DEF”，6，应该输出为“我ABC”而不是“我ABC+汉的半个”。
     */
    public static String test(String str, int byteNum)
    {
        
        return "";
    }
    
    /**
     * 4．金额转换，阿拉伯数字的金额转换成中国传统的形式如：（￥1011）－>（一千零一拾一元整）输出。
     */
    public static String num2Cn(int money)
    {
        return "";
    }
    
    /**
     * 5. 如果系统要使用超大整数（超过long长度范围），请你设计一个数据结构来存储这种超大型数字以及设计一种算法来实现超大整数加法运算）。
     */
    public static String computAdd(long p1, long p2)
    {
        return "";
    }
    
    /**
     * 6. 如果系统要使用超大整数（超过long长度范围），请你设计一个数据结构来存储这种超大型数字以及设计一种算法来实现超大整数减法运算）。
     */
    public static String computReduce(long p1, long p2)
    {
        
        return "";
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException
    {
        
    }
    
}
