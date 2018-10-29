package com.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vo.ResponseEntity;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UploadAndDownloadAction
{
    
    private ResponseEntity<?> responseEntity = new ResponseEntity<>();
    
    private String stuName;
    
    private File[] head1;
    
    private File[] head2;
    
    private String[] head1FileName;// 文件的属性名+FileName == 文件的真实文件名
    
    private String[] head2FileName;
    
    // private InputStream inputStreamForDownload;
    
    private String fileName;// 下载时，页面给的文件名。用存保存附件
    
    Collection<String> errors1 = null;
    
    public String upload() throws IOException
    {
        
        HttpServletRequest resuqest = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        
        try
        {
            // 创建目录
            String dirPath = resuqest.getServletContext().getRealPath("upload");
            creatUploadDir(dirPath);
            
            // 写文件
            if (null != head1)
            {
                writeFiles(head1, head1FileName, dirPath);
            }
            
            if (null != head2)
            {
                writeFiles(head2, head2FileName, dirPath);
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //
            response.setStatus(400);
            responseEntity.setErrorMsg("upload error. " + e.getMessage());
            
        }
        
        return "success";
    }
    
    public String download() throws IOException
    {
        return "success";
    }
    
    public InputStream getInputStreamForDownload()
    {
        return ServletActionContext.getRequest().getServletContext()
                .getResourceAsStream("upload" + File.separator + fileName);
        
        // inputStreamForDownload =
        // ServletActionContext.getRequest().getServletContext()
        // .getResourceAsStream("upload" + File.separator + fileName);
        //
        // return inputStreamForDownload;
    }
    
    private void writeFiles(File[] files, String[] fileNames, String dirPath) throws IOException
    {
        for (int i = 0; i < files.length; i++)
        {
            String descFilePath = dirPath + File.separator + fileNames[i];
            
            FileUtils.copyFile(files[i], new File(descFilePath));
        }
    }
    
    private void creatUploadDir(String dirPath)
    {
        File dir = new File(dirPath);
        
        if (dir.exists())
        {
            if (!dir.isDirectory())
            {
                throw new SecurityException("create upload dir error. dirPath:" + dirPath);
            }
        }
        else if (!dir.mkdirs())
        {
            throw new SecurityException("create upload dir error. dirPath:" + dirPath);
        }
    }
    
    public ResponseEntity<?> getResponseEntity()
    {
        return responseEntity;
    }
    
    public void setResponseEntity(ResponseEntity<?> responseEntity)
    {
        this.responseEntity = responseEntity;
    }
    
    public String getStuName()
    {
        return stuName;
    }
    
    public void setStuName(String stuName)
    {
        this.stuName = stuName;
    }
    
    public File[] getHead1()
    {
        return head1;
    }
    
    public void setHead1(File[] head1)
    {
        this.head1 = head1;
    }
    
    public File[] getHead2()
    {
        return head2;
    }
    
    public void setHead2(File[] head2)
    {
        this.head2 = head2;
    }
    
    public String[] getHead1FileName()
    {
        return head1FileName;
    }
    
    public void setHead1FileName(String[] head1FileName)
    {
        this.head1FileName = head1FileName;
    }
    
    public String[] getHead2FileName()
    {
        return head2FileName;
    }
    
    public void setHead2FileName(String[] head2FileName)
    {
        this.head2FileName = head2FileName;
    }
    
    public String getFileName()
    {
        return fileName;
    }
    
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    
}
