package com.controller;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.vo.ErrorHandler;

@Controller
public class UploadAndDownloadController
{
    
    private static final Logger LOG = Logger.getLogger(UploadAndDownloadController.class);
    
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> upload(@RequestParam String stuName, MultipartHttpServletRequest requst,
            HttpServletResponse resp)
    {
        try
        {
            Iterator<String> formFileNames = requst.getFileNames();
            
            String destFileDir = requst.getServletContext().getRealPath("upload");
            
            // TODO 验证目录
            
            while (formFileNames.hasNext())
            {
                String formFileName = formFileNames.next();
                
                List<MultipartFile> files = requst.getFiles(formFileName);
                
                for (MultipartFile file : files)
                {
                    File dest = new File(destFileDir + File.separator + file.getOriginalFilename());
                    file.transferTo(dest);
                }
            }
            
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            LOG.error("upload error. " + e.getMessage(), e);
            
            return new ResponseEntity<Object>(new ErrorHandler("upload error. " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        
    }
    
    @RequestMapping(path = "/{fileName}/download", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> download(@PathVariable String fileName, HttpServletResponse resp,
            HttpServletRequest req)
    {
        try
        {
            InputStream in = req.getServletContext().getResourceAsStream("upload" + File.separator + fileName);
            
            if (null == in)
            {
                throw new SecurityException(fileName + " is  not found.");
            }
            
            byte[] bytes = FileCopyUtils.copyToByteArray(in);
            
            // responseEntity.setContent(bytes);
            // resp.setHeader("Content-Disposition", "attarchment;filename=" +
            // fileName);
            // resp.getOutputStream().write(bytes);
            
            MultiValueMap<String, String> headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attarchment;filename=" + fileName);
            
            return new ResponseEntity<Object>(bytes, headers, HttpStatus.OK);
        }
        catch (Exception e)
        {
            LOG.error("download error. " + e.getMessage(), e);
            
            return new ResponseEntity<Object>(new ErrorHandler("download error. " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        
    }
    
}
