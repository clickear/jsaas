package com.redxun.saweb.security.filter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.redxun.org.api.model.IUser;
import com.redxun.org.api.service.UserService;

/**
 * 登录过滤器
 * @author csx
 *  @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter{
    @Resource
    UserService userService;
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
    		HttpServletResponse response) throws AuthenticationException {

    	if (!request.getMethod().equals("POST")) {  
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());  
        }  
        //检测验证码  
        //checkValidateCode(request);  
          
        String username = obtainUsername(request);  
        String password = obtainPassword(request);  
        //验证用户账号与密码是否对应  
        username = username.trim();  
        
        IUser iUser = userService.getByUsername(username);  
          
        if(iUser == null || !iUser.getPwd().equals(password)) {  
            throw new AuthenticationServiceException("用户名或者密码错误！");   
        }  
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);  
        
        return this.getAuthenticationManager().authenticate(authRequest); 
    }
}
