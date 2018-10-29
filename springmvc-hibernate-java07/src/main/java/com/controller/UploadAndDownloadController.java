package com.controller;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.vo.ErrorHanlder;

@Controller
public class UploadAndDownloadController implements ResourceLoaderAware
{
    
    @SuppressWarnings("unused")
    private ResourceLoader resourceLoader;
    
    @RequestMapping(path = "/upload", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    // @ResponseBody
    public ResponseEntity<Object> upload(@RequestParam("stuName") String stuName, MultipartHttpServletRequest request,
            HttpServletResponse response)
    {
        try
        {
            
            // String temp = request.getParameter("stuName");
            
            String uploadDir = request.getServletContext().getRealPath("upload");
            // String uploadDir =
            // resourceLoader.getResource("classpath:/static/upload/").getFile().getCanonicalPath();
            
            Iterator<String> fileNames = request.getFileNames();
            
            while (fileNames.hasNext())
            {
                String fromFileName = fileNames.next();
                
                List<MultipartFile> files = request.getFiles(fromFileName);
                
                for (MultipartFile multipartFile : files)
                {
                    if (multipartFile.isEmpty())
                    {
                        continue;
                    }
                    
                    multipartFile
                            .transferTo(new File(uploadDir + File.separator + multipartFile.getOriginalFilename()));
                }
                
            }
            
            return new ResponseEntity<Object>("上传成功！！！", HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            return new ResponseEntity<Object>(new ErrorHanlder(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        
    }
    
    @RequestMapping(path = "/download", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> download(String fileName, HttpServletRequest request, HttpServletResponse response)
    // public void download(String fileName, HttpServletRequest request,
    // HttpServletResponse response)
    {
        try
        {
            String uploadDir = request.getServletContext().getRealPath("upload");
            // String uploadDir =
            // resourceLoader.getResource("classpath:/static/upload/").getFile().getCanonicalPath();
            String pathname = uploadDir + File.separator + fileName;
            
            byte[] fileBytes = FileUtils.readFileToByteArray(new File(pathname));
            
            MultiValueMap<String, String> headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + fileName);
            
            return new ResponseEntity<Object>(fileBytes, headers, HttpStatus.OK);
            
            // FileInputStream fis = new FileInputStream(pathname);
            //
            // ByteArrayOutputStream bos = new ByteArrayOutputStream();
            //
            // byte[] buf = new byte[1024];
            // int len = -1;
            //
            // while ((len = fis.read(buf)) > 0)
            // {
            // bos.write(buf, 0, len);
            // }
            //
            // byte[] fileBytes = bos.toByteArray();
            //
            // response.setHeader("Content-Disposition",
            // "attarchement;fileName=" + fileName);
            //
            // response.getOutputStream().write(fileBytes);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            return new ResponseEntity<Object>(new ErrorHanlder(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        
    }
    
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader)
    {
        this.resourceLoader = resourceLoader;
        
    }
    
}
