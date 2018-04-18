package com.redxun.saweb.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.util.CookieUitl;
import com.redxun.core.util.FileUtil;
import com.redxun.core.util.HttpClientUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.WebAppUtil;

/**
 * 功能: 实现单点登录。
 * 	平台A,JSAAS 简称B,在A登录后，可以直接进入到B系统。
 * 
 * 	1.一个平台A登录后会在浏览器端写入一个token的cookie。
 *  2. B 过滤器发送这个token到平台A.
 *  3. A 给出对应的用户信息。
 *  4. B根据这个用户信息实现自动登录。
 *
 * @author ray
 *
 */
public class TokenSsoFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		String token=CookieUitl.getValueByName("token", req); 
		IUser user=ContextUtil.getCurrentUser();
		if(user!=null){
			chain.doFilter(request, response);
			return ;
		}
		if(StringUtil.isNotEmpty(token)){
			JSONObject userJson=  getJson();
			String url="http://121.201.110.228:8012/security-client/rest?method=securityService.checkState";
			String jsonParams="{\"token\":"+token+"}";
			try {
				String json=HttpClientUtil.postJson( url,jsonParams);
				JSONObject obj=JSONObject.parseObject(json);
				if("0".equals(obj.getString("returncode"))){
					JSONObject data=obj.getJSONObject("data");
					String code=data.getString("code");
					String account=userJson.getString(code) +"@redxun.cn";
					
					SecurityUtil.login(req,account, "", true);
					
					chain.doFilter(request, response);
				}
				else{
					response.getWriter().println("查询服务出错");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else{
			chain.doFilter(request, response);
		}
		
	}
	
	private JSONObject getJson(){
		String path=WebAppUtil.getClassPath();
    	path=path+ "config/usermap.json";
    	String json=FileUtil.readFile(path);
    	JSONObject jsonObj=JSONObject.parseObject(json);
    	return jsonObj;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
