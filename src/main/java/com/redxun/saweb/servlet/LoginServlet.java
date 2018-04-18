package com.redxun.saweb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.redxun.core.util.AppBeanUtil;
/**
 * 登录的servlet
 * @author mansan
 *
 */
public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username=request.getParameter("username");
		String password=request.getParameter("password");
                
		UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(username, password);
		token.setDetails(new WebAuthenticationDetails(request));
		
		AuthenticationManager authenticationManager=(AuthenticationManager)AppBeanUtil.getBean("authenticationManager");
		try{
			authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(token);
			response.sendRedirect(request.getContextPath() + "/index.do");
		}catch(Exception ex){
			ex.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/login.jsp?error=true");
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
