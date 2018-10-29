package com.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class GenerateCheckCodeUtils
{
    public static interface CallBack
    {
        void doBack(String checkCode);
    }
    
    public static void generateCheckCode(OutputStream out, CallBack callBak) throws IOException
    {
        // 先在内存中创建一张空白的图片
        BufferedImage image = new BufferedImage(110, 30, BufferedImage.TYPE_INT_BGR);
        
        // 创建画笔，用于将文字画到图片上
        Graphics graphics = image.getGraphics();
        
        // 设置图片的背景颜色 白色
        graphics.setColor(Color.white); // 设置的是画笔的颜色
        graphics.fillRect(0, 0, 110, 30);// 指定在哪个范围画上白色
        
        // 设置文字 5个 大小写字母、数字 随机生成 abc12
        String checkCode = "";
        for (int i = 0; i < 5; i++)
        {
            // 设置画笔的颜色：随机
            graphics.setColor(randomColor());
            // 设置文字的字体：随机
            graphics.setFont(randomFont());
            // 生成文字：随机
            String str = randomText();
            checkCode += str;
            // 将文字画到图片上
            graphics.drawString(str, 20 * i, 25);// x坐标每一个文字都必须不一样，避免文字重叠
        }
        
        // 业务处理
        callBak.doBack(checkCode);
        
        // 画干扰点
        Random random = new Random();
        for (int i = 0; i < 50; i++)
        {
            int x = random.nextInt(110);
            int y = random.nextInt(30);
            graphics.setColor(randomColor());
            graphics.fillOval(x, y, 2, 2);// 指定点的大小
        }
        
        // 画干扰线
        for (int i = 0; i < 5; i++)
        {
            int x1 = random.nextInt(110);
            int y1 = random.nextInt(30);
            int x2 = random.nextInt(110);
            int y2 = random.nextInt(30);
            graphics.setColor(randomColor());
            graphics.drawLine(x1, y1, x2, y2);// 两个点决定一条线
        }
        
        // 将图片发送给前端页面
        ImageIO.write(image, "jpg", out);
        
    }
    
    // 随机生成颜色
    private static Color randomColor()
    {
        Random random = new Random();
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));// 0-255
    }
    
    // 随机字体
    private static Font randomFont()
    {
        Random random = new Random();
        // 字体的名字
        String[] font_name = new String[]
        { "Broadway", "方正姚体", "方正舒体", "幼圆", "Colonna MT", "Footlight MT Light" };
        // 字体风格
        int[] font_style = new int[]
        { Font.BOLD, Font.ITALIC, Font.BOLD | Font.ITALIC };
        
        // 随机获取字体名下标、风格下标
        int name_index = random.nextInt(font_name.length);
        int style_index = random.nextInt(font_style.length);
        
        // 创建字体
        return new Font(font_name[name_index], font_style[style_index], 28);
    }
    
    // 随机生成文字
    private static String randomText()
    {
        String[] nums = new String[62];
        // 将0-9这些数字放入数组的前10个元素
        for (int i = 0; i < 10; i++)
        {
            nums[i] = String.valueOf(i);// 将数字转换成字符串
        }
        // 将26个大写字母放入数组里面，10 - 35
        for (int i = 65; i < 91; i++)
        {
            nums[i - 55] = Character.toString((char) i);// 先将数字转换成对应的char字母，再将字母转换成字符串
        }
        // 将26个小写字母放入数组：36-61
        for (int i = 97; i < 123; i++)
        {
            nums[i - 61] = Character.toString((char) i);
        }
        Random random = new Random();
        int index = random.nextInt(nums.length);// 随机生成字符串的下标
        
        // 随机在nums里面拿一个字符串返回
        return nums[index];
    }
    
}
