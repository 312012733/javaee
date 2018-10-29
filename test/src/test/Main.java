package test;

import java.io.UnsupportedEncodingException;

public class Main
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
    // 1,0000,0001,0000
    // public static final String YUAN = "元";
    // public static final String JIAO = "角";
    // public static final String FEN = "分";
    // public static final String DOT = ".";
    
    // 2．金额转换，阿拉伯数字的金额转换成中国传统的形式如：（￥1011）－>（一千零一拾一元整）输出。
    public static String num2Cn(int money)
    {
        if (money <= 0)
        {
           throw new IllegalArgumentException("money must be > 0");
        }
        
        char[] charsMoney = String.valueOf(money).toCharArray();
        String result = "";
        String subResult = "";
        
        for (int i = 0; i < charsMoney.length; i++)
        {
            int reversIndex = charsMoney.length - i - 1;
            int intNum = Integer.parseInt(charsMoney[reversIndex] + "");
            String position = POSITION_DIC[i % 4];
            String unit = UNIT_DIC[i / 4];
            
            subResult = (intNum == 0 ? NUM_DIC[intNum] : NUM_DIC[intNum] + position) + subResult;
            
            if ((i + 1) % 4 == 0)
            {
                if (!"零零零零".equals(subResult))
                {
                    subResult += unit;
                }
                
                result = subResult + result;
                subResult = "";
            }
            else if (reversIndex == 0)
            {
                result = subResult + unit + result;
            }
        }
        
        System.out.println(result);
        
        result = result.replaceAll("零+亿", "亿零").replaceAll("零+万", "万零").replaceAll("零+圆", "").replaceAll("圆", "")
                .replaceAll("零+", "零");
        
        System.out.println(result);
        
        return (result.lastIndexOf("零") == result.length() - 1) ? result.substring(0, result.length() - 1) : result;
    }
    
    // 123456
    /*
     * 123456 1 23456 2 3456 3 456 4 56 5 6 6
     * 
     * ""+ 6 +5 + 4 + 3 +2 +1
     * 
     */
    static String func(String s)
    {
        return s.length() > 0 ? func(s.substring(1)) + s.charAt(0) : "";
    }
    
    // 写一个函数去掉一个字符串中单词间多余的空格，使得相邻两个单词间有且只有一个空格。例如当输入字符串是
    // “Hello!__Game_programming__world!”时，调用该函数后字符串变为“Hello!_Game_programming_world!”。
    /**
     * 去除字符串中多余的空格
     * 
     * @param s
     *            需要处理的字符串
     * @return 处理后的字符串
     */
    static String doString(String s)
    {
        // return s.replaceAll("\\s+", " ").trim();
        
        // 1, char[]
        
        // 2.遍历 char【】
        
        // 3. StringBuilder.appened(''); == String str; str+=''+"";
        
        // 4. 条件 a.非空格 b.char[i]==' ' i>1 char[i-1]！=' '
        
        char[] chars = s.toCharArray();
        
        // String result = "";
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < chars.length; i++)
        {
            if (chars[i] == ' ')
            {
                if (i > 0 && chars[i - 1] != ' ')
                {
                    // result += String.valueOf(chars[i]);
                    result.append(chars[i]);
                }
            }
            else
            {
                // result += String.valueOf(chars[i]);
                result.append(chars[i]);
            }
        }
        
        return result.toString().trim();
    }
    
    /**
     * 编写一个截取字符串的函数，输入为一个字符串和字节数，输出为按字节截取的字符串。
     * 但是要保证汉字不被截半个，如“我ABC”4，应该截为“我AB”，输入“我ABC汉DEF”，6，应该输出为“我ABC”而不是“我ABC+汉的半个”。
     */
    public static String test(String str, int byteNum)
    {
        
        if (null != str)
        {
            char[] chars = str.toCharArray();
            int sumByteSize = 0;
            
            for (int i = 0; i < chars.length; i++)
            {
                String charStr = String.valueOf(chars[i]);
                int charByteSize = charStr.getBytes().length;
                
                sumByteSize += charByteSize;
                
                if (sumByteSize >= byteNum)
                {
                    return str.substring(0, i + 1);
                }
            }
            
            throw new IllegalArgumentException("byteNum > param bytes");
        }
        
        throw new IllegalArgumentException("param is null");
    }
    
    /**
     * 8. 如果系统要使用超大整数（超过long长度范围），请你设计一个数据结构来存储这种超大型数字以及设计一种算法来实现超大整数加法运算）。
     */
    public static String computAdd(long p1, long p2)
    {
        String s1 = p1 >= p2 ? String.valueOf(p1) : String.valueOf(p2);
        String s2 = p1 < p2 ? String.valueOf(p1) : String.valueOf(p2);
        
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        
        String result = "";
        int t1 = 0, t2 = 0, g2 = 0;
        
        for (int i = 0; i < c1.length; i++)
        {
            t1 = Integer.parseInt(c1[c1.length - i - 1] + "");
            t2 = i >= c2.length ? 0 : Integer.parseInt(c2[c2.length - i - 1] + "");
            
            int r = t1 + t2 + g2;
            int g1 = r >= 10 ? r % 10 : r;
            g2 = r >= 10 ? 1 : 0;
            
            result = g1 + result;
        }
        
        return g2 > 0 ? g2 + result : result;
    }
    
    /**
     * 8. 如果系统要使用超大整数（超过long长度范围），请你设计一个数据结构来存储这种超大型数字以及设计一种算法来实现超大整数减法运算）。
     */
    public static String computReduce(long p1, long p2)
    {
        
        String symbol = p1 > p2 ? "" : "-";
        
        String s1 = "";
        String s2 = "";
        
        if (p1 < 0)
        {
            if (p2 >= 0) // -1 - 2 = -1 + -2
            {
                return symbol + computAdd(p1 * -1, p2);
            }
            
            // -3 - -1 = 1 - 3 = 3 - 1
            long np1 = p2 * -1;
            long np2 = p1 * -1;
            
            s1 = np1 >= np2 ? String.valueOf(np1) : String.valueOf(np2);
            s2 = np1 < np2 ? String.valueOf(np1) : String.valueOf(np2);
        }
        else
        {
            if (p2 < 0) // 1 - -2 = 1 + 2
            {
                return computAdd(p1, p2 * -1);
            }
            
            // 1 - 2 = -(2-1)
            long np1 = p1;
            long np2 = p2;
            s1 = np1 >= np2 ? String.valueOf(np1) : String.valueOf(np2);
            s2 = np1 < np2 ? String.valueOf(np1) : String.valueOf(np2);
        }
        
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        
        String result = "";
        int t1 = 0, t2 = 0, g2 = 0;
        
        for (int i = 0; i < c1.length; i++)
        {
            t1 = Integer.parseInt(c1[c1.length - i - 1] + "");
            t2 = i >= c2.length ? 0 : Integer.parseInt(c2[c2.length - i - 1] + "");
            
            int nt1 = t1 - g2 < 0 ? t1 + 10 - g2 : t1 - g2;
            int g1 = nt1 >= t2 ? nt1 - t2 : nt1 + 10 - t2;
            g2 = nt1 >= t2 && t1 - g2 >= 0 ? 0 : 1;
            
            result = g1 + result;
        }
        
        char[] temp = result.toCharArray();
        int i = 0;
        
        for (; i < temp.length; i++)
        {
            if (temp[i] != '0')
            {
                break;
            }
        }
        
        return symbol + result.substring(i);
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException
    {
        
        // long p1 = -1000000;
        // long p2 = -999;
        //
        // // System.out.println(comput(Long.MAX_VALUE, Long.MAX_VALUE));
        // // System.out.println(computAdd(0, 99999999));
        //
        // String r1 = String.valueOf(p1 - p2);
        // String r2 = computReduce(p1, p2);
        //
        // System.out.println(r1.equals(r2) + "-------r1=" + r1 + "-------r2=" +
        // r2);
        
//        int i = 0;
//        
//        while (true)
//        {
//            System.out.println((i++ % 8)+1);
//            
//        }
        
         System.out.println(num2Cn(-1000));
        // for (int i = 100000000; i < 999999999; i++)
        // {
        //
        // System.out.println(i+" "+num2Cn(i));
        // }
        
        // // String str = "[u4E00-u9FA5]汉字?[uFE30-uFFA0]全角字符";
        // String str = new
        // String("[u4E00-u9FA5]汉字?[uFE30-uFFA0]全角字符".getBytes(), "UTF-8");
        //
        // System.out.println("总字节：" + str.getBytes().length);
        //
        // for (int i = 1; i <= str.getBytes().length; i++)
        // {
        //
        // String result = test(str, i);
        // System.out.println(i + " " + result);
        // }
        
        // String s1 = "飞";
        // char c1 = '飞';
        
        // System.out.println(doString(" aaa bb ccc dd eeeee ff ggg "));
        
        // int a = 8, b = 4;
        //
        // int expr = a++ % ++b * 2;// 8%5*2 = 6
        
        // double expr = 20.0 / 8.0 * 8.0;
        
        // int expr = 4 << 2 + 1;//4<<3 a<<b a*2^B 1<<2
        
        // int expr = -4321 >>>30;
        
        // int expr = 123 ^ 321 ^ 123;
        //
        // int a = 2;
        //
        // int b = 3;
        
        // int temp = b;
        //
        // b = a;
        // a = temp;
        //
        // int a_ = a^b^a;
        // int b_ = a;
        
        // System.out.println(a_+" "+b_);
        
        // int a = 255;//-128 -- 128 -1
        // int expr = (byte)a + (((byte)a) & 0xff);
        
        // int expr = "123454321".charAt(4) + 2;
        
        // '5'+2;
        // a A 0
        
        // Integer a = new Integer(1);
        // Integer b = new Integer(1);
        //
        // Integer c = -129;
        // Integer d = -129;
        
        // boolean expr = a == b;//false true
        
        // System.out.println(a==b);
        // System.out.println(a==c);
        // System.out.println(d==c);
        
        // String expr = func("Gameloft");
        // System.out.println(expr);
        
    }
    
}
