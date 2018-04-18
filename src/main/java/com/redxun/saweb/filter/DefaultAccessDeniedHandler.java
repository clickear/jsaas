package com.redxun.saweb.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class DefaultAccessDeniedHandler implements AccessDeniedHandler {
	
	

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){ 
            response.setCharacterEncoding("UTF-8");
            response.addHeader("exceptionAction", "403");
            return ;
        }
		else{
			request.getRequestDispatcher("/commons/403.jsp").forward(request, response);
		}
		
		
		
		
//		 <error-page>
//		    <error-code>403</error-code>
//		    <location>/commons/403.jsp</location>
//		  </error-page>
		
	}

}
