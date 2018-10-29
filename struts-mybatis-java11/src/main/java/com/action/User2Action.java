package com.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bean.User;
import com.service.IUserService;
import com.vo.UserDTO;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class User2Action
// extends ActionSupport
{
    
    public static final String CHECK_CODE = "CheckCode";
    
    @Autowired
    private IUserService userService;
    
    private UserDTO userParam;
    
    // public String execute() throws Exception
    // {
    // return login();
    // }
    //
    // public void validate()
    // {
    // System.out.println();
    // }
    
    private String login() throws IOException
    {
        
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        
        // 设置数据返回的类型
        // response.setContentType("application/json;charset=utf-8");//
        // 设置返回的数据类型是json的形式
        
        // PrintWriter out = response.getWriter();
        
        // ResponseEntity<?> responseEntity = new ResponseEntity<>();
        
        try
        {
            String checkCode = userParam.getCheckCode();
            String userName = userParam.getUsername();
            String password = userParam.getPassword();
            
            // 验证参数
            // TODO 空判定。。。。。
            
            // 验证验证码
            HttpSession session = request.getSession();
            
            String checkCodeFromsession = (String) session.getAttribute(CHECK_CODE);
            
            // if (!checkCode.equals(checkCodeFromsession))
            if (!checkCode.equals(checkCodeFromsession) && false)
            {
                throw new SecurityException("check code is error.");
            }
            
            // 验证用户信息
            User user = userService.findUserByUsernameAndPassword(userName, password);
            
            if (null == user)
            {
                // throw new SecurityException("username or password is
                // error.");
                
                // response.sendRedirect(request.getContextPath() +
                // "/error.html");
                
                return "error";
            }
            
            // 登录成功。。。进行鉴权
            session.setAttribute(session.getId(), user);
            
            // request.getRequestDispatcher("/page/main.html").forward(request,
            // response);
            
            return "success";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //
            // response.setStatus(400);
            // responseEntity.setErrorMsg("login error. " + e.getMessage());
            
            return "error";
        }
        
    }
    
    public UserDTO getUserParam()
    {
        return userParam;
    }
    
    public void setUserParam(UserDTO userParam)
    {
        this.userParam = userParam;
    }
    
}
