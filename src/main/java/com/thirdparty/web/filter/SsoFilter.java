package com.thirdparty.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.redxun.core.util.HttpClientUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.filter.SecurityUtil;

/**
 * 从cookie中获得单点登录的信息
 * @author mansan
 *
 */
public class SsoFilter extends OncePerRequestFilter{
	/**
	 * sso 
	 */
	private String ssoTrackUrl="http://139.199.202.195/cemSys/user/getUserByTrackId";
	/**
	 * sso login page
	 */
	private String ssoLogin="http://139.199.202.195/";
	

	public String getSsoTrackUrl() {
		return ssoTrackUrl;
	}

	public void setSsoTrackUrl(String ssoTrackUrl) {
		this.ssoTrackUrl = ssoTrackUrl;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
        
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "0");
        response.setHeader("Access-Control-Allow-Headers","Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("XDomainRequestAllowed", "1");
		
		//String trackId = RequestUtil.getCookieValue(request, "trackId");
		String trackId=request.getParameter("trackId");
		
		if(StringUtils.isEmpty(trackId)){
			filterChain.doFilter(request, response);
			return;
		}
		
		String oldTrackId=(String)request.getSession().getAttribute("oldtrackId");

		IUser user = ContextUtil.getCurrentUser();
		if(trackId.equals(oldTrackId) && user!=null){
			filterChain.doFilter(request, response);
			return;
		}else{
			//get user info username ,pwd from 
			
			String url=ssoTrackUrl+"?trackId="+trackId;
			try{
				String data=HttpClientUtil.getFromUrl(url,null);
				JSONObject loginInfo=JSON.parseObject(data);
				String result=loginInfo.getString("result");
				
				if("TIMEOUT".equals(result)){
					response.sendRedirect(ssoLogin);
					return;
				}
				
				String userInfo=loginInfo.getString("LOGIN_CURRENT_USER_JSON");
				
				JSONObject userObj=JSON.parseObject(userInfo);
				String userName=userObj.getString("userName");
				String pwd=userObj.getString("password");
				
				SecurityUtil.login(request, userName, pwd, true);
				request.getSession().setAttribute("oldtrackId",trackId);
				filterChain.doFilter(request, response);

			}catch(Exception e){
				e.printStackTrace();
				filterChain.doFilter(request, response);
			}
			
		}
	}
	
	public static void main(String[]args) throws Exception{
		//eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MjA0MTM1ODMwNTksInBheWxvYWQiOiJcImFkbWluXCIifQ.LGPlsUkje6mm0b0K8OAlZAqsDk4JRdsZhgJ_jo-05tI
		//String url="https://192.168.0.122/cemSys/user/getUserByTrackId?trackId=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MjA0MTM1ODMwNTksInBheWxvYWQiOiJcImFkbWluXCIifQ.LGPlsUkje6mm0b0K8OAlZAqsDk4JRdsZhgJ_jo-05tI";
		//String url="https://192.168.0.122/cemSys/user/getUserByTrackId?trackId=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MjA0MTA3MTkzMzUsInBheWxvYWQiOiJcImFkbWluXCIifQ.Ba1z6I65cTTQ0XV2dU8hXq0cCFjtx3hlWk2RPsF1EoI";
//		String url="http://www.baidu.com";
//		String data=HttpClientUtil.getFromUrl(url,null);
//		System.out.print("data:"+data);
		
//		JSONObject loginInfo=JSON.parseObject(data);
//			String result=loginInfo.getString("result");
//			
//			
//			String userInfo=loginInfo.getString("LOGIN_CURRENT_USER_JSON");
//			
//			JSONObject userObj=JSON.parseObject(userInfo);
//			String userName=userObj.getString("userName");
//			String pwd=userObj.getString("password");
//
//			System.out.println("result:"+ result + " userName:"+ userName + "pwd:"+ pwd);
		
//		String pwd="c3cb2a15736a0b05";
//		String as=EncryptUtil.encryptMd5Hex(pwd);
//		System.out.println("as:"+ as);
	}

}
