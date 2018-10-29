package utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public class IoUtils
{
    public static void main(String[] args) throws IOException
    {
        String sourcePath = "F:\\360Downloads\\pom.xml";
        String destPath1 = "F:\\360Downloads\\1\\pom.xml";
        String destPath2 = "F:\\360Downloads\\2\\pom.xml";
        
        InputStream in = readFile(sourcePath);
        writeFile(in, destPath1);
        
        in = readFile(sourcePath);
        Reader reader = new InputStreamReader(in, "utf-8");
        writeFile(reader, destPath2);
    }
    
    public static InputStream readFile(String path) throws IOException
    {
        return new FileInputStream(path);
    }
    
    public static void writeFile(InputStream in, String path) throws IOException
    {
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        
        checkDir(path);
        
        try
        {
            byte[] buf = new byte[1024];
            int len = -1;
            
            bos = new BufferedOutputStream(new FileOutputStream(path));
            bis = new BufferedInputStream(in);
            
            while ((len = bis.read(buf)) > 0)
            {
                bos.write(buf, 0, len);
            }
        }
        finally
        {
            close(bos);
            close(bis);
        }
    }
    
    private static void checkDir(String path) throws IOException
    {
        File dir = new File(path).getParentFile();
        
        if (dir.exists())
        {
            if (!dir.isDirectory())
            {
                throw new SecurityException("create dir error.. " + path);
            }
        }
        else if (!dir.mkdir())
        {
            throw new SecurityException("create dir error.. " + path);
        }
    }
    
    public static void writeFile(Reader reader, String path) throws IOException
    {
        FileWriter fw = null;
        BufferedWriter bw = null;
        BufferedReader br = null;
        
        checkDir(path);
        
        try
        {
            char[] buf = new char[1024];
            int len = -1;
            
            fw = new FileWriter(path);
            bw = new BufferedWriter(fw);
            br = new BufferedReader(reader);
            
            while ((len = reader.read(buf)) > 0)
            {
                fw.write(buf, 0, len);
            }
        }
        finally
        {
            close(fw);
            close(reader);
        }
    }
    
    public static void close(InputStream in)
    {
        try
        {
            if (null != in)
            {
                in.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void close(OutputStream out)
    {
        try
        {
            if (null != out)
            {
                out.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void close(Reader in)
    {
        try
        {
            if (null != in)
            {
                in.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void close(Writer out)
    {
        try
        {
            if (null != out)
            {
                out.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
