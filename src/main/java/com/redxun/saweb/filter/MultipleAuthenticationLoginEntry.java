package com.redxun.saweb.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.redxun.core.util.CookieUitl;
import com.redxun.core.util.StringUtil;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.SysInstType;


public class MultipleAuthenticationLoginEntry implements
		AuthenticationEntryPoint {

    private String defaultLoginUrl="/login.jsp"; 
    
    private String partnerLoginUrl="/partner/login.jsp";
     
  
    /**
     * 根据输入路径与配置项得到跳转路径分别跳到不同登录页面
     */
    @Override  
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {  
        
    	//如果是AJAX请求异常
    	HttpServletRequest httpRequest = (HttpServletRequest)request;
        if ("XMLHttpRequest".equals(httpRequest.getHeader("X-Requested-With"))){ 
            response.setCharacterEncoding("UTF-8");
            response.addHeader("exceptionaction", "timeout");
            return ;
        }
        //普通请求则跳转至改登陆页面
    	String ctxPath = request.getContextPath();
        String instType=CookieUitl.getValueByName("instType", request);
        if(StringUtil.isNotEmpty(instType) && !SysInstType.INST_TYPE_PLATFORM.equals(instType)){
        	 response.sendRedirect(ctxPath+partnerLoginUrl);
        	 return;
        }
        
        String loginAction=CookieUitl.getValueByName("loginAction", request);
        Map<String,String> actionPageMap=(Map<String,String>)WebAppUtil.getBean("actionPageMap");
		if(StringUtil.isNotEmpty(loginAction) && actionPageMap.containsKey(loginAction)){
			String redirectUrl=actionPageMap.get(loginAction);
			response.sendRedirect(request.getContextPath() +redirectUrl);
			return ;
		}
  
        response.sendRedirect(ctxPath+defaultLoginUrl);  
    }  
  
    public void setDefaultLoginUrl(String defaultLoginUrl) {  
    	
        this.defaultLoginUrl = defaultLoginUrl;  
    }

	public void setPartnerLoginUrl(String partnerLoginUrl) {
		this.partnerLoginUrl = partnerLoginUrl;
	}

	

}