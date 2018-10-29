package com.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bean.User;
import com.service.IUserService;
import com.utils.GenerateCheckCodeUtils;
import com.utils.GenerateCheckCodeUtils.CallBack;
import com.vo.ErrorHanlder;
import com.vo.LogUtils;
import com.vo.UserDTO;

@Controller
// @RestController
public class UserController
{
    @Autowired
    private IUserService userService;
    
    @RequestMapping(path = "/user/generateCheckCode", method = RequestMethod.GET)
    public void generateCheckCode(HttpServletRequest request, HttpServletResponse response)
    {
        OutputStream out = null;
        try
        {
            out = response.getOutputStream();
            // 1 生成验证码
            // 禁止浏览器缓存图片：为了防止验证码不正确时重新生成的验证码不显示问题
            response.setDateHeader("Expries", -1);
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            // 设置浏览器以图片的方式打开发送给浏览器的数据
            response.setContentType("imge/jpeg");
            
            GenerateCheckCodeUtils.generateCheckCode(out, new CallBack()
            {
                @Override
                public void doBack(String checkCode)
                {
                    LogUtils.debug("checkCode:" + checkCode);
                    
                    HttpSession session = request.getSession();
                    session.setAttribute("checkCode", checkCode);
                }
            });
        }
        catch (Exception e)
        {
            try
            {
                out.write(e.getMessage().getBytes());
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }
    }
    
    @RequestMapping(path = "/user/login", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> login(@RequestBody UserDTO userParam, HttpServletRequest request,
            HttpServletResponse response)
    {
        try
        {
            
            String username_ = userParam.getUsername();
            String password_ = userParam.getPassword();
            
            Object checkCode = request.getSession().getAttribute("checkCode");
            
            if (!checkCode.equals(userParam.getCheckCode()))
            {
                // throw new SecurityException("checkCode is error.");
            }
            
            User user = userService.login(username_, password_);
            
            if (null == user)
            {
                throw new SecurityException("username or password is error. ");
            }
            
            return new ResponseEntity<Object>(user, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            return new ResponseEntity<Object>(new ErrorHanlder(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        
    }
    
    // @RequestMapping(path = "/user/login", method = RequestMethod.GET)
    // public String login2(UserDTO userParam, HttpServletRequest request,
    // HttpServletResponse response)
    // {
    // try
    // {
    // String username_ = userParam.getUsername();
    // String password_ = userParam.getPassword();
    //
    // User user = userService.login(username_, password_);
    //
    // if (null == user)
    // {
    // throw new SecurityException("username or password is error. ");
    // }
    //
    // return "forward:/success.html";
    // }
    // catch (Exception e)
    // {
    // e.printStackTrace();
    //
    // return "redirect:/error.html";
    // }
    //
    // }
    
}
