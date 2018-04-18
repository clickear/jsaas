package com.redxun.saweb.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.redxun.core.util.EncryptUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.WebAppUtil;

/**
 * 手机直接登录。
 * 
 * <pre>
 * 
 * 1.第三方通过跳转到 m.do?token=eeeasddadkk3ii3kk= ，token为 加密后的帐号。
 * 2.此过滤器通过私钥解密token获取account。
 * 3.通过无密码让系统登录，自动跳转到 我的代办页面。
 * </pre>
 * 
 * 
 * @author redxun
 *
 */
public class MobileFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res=(HttpServletResponse) response;
		HttpServletRequest req=(HttpServletRequest) request;
		String url=req.getRequestURI();
		if(url.indexOf("/m.do")!=-1){
			String token=req.getParameter("token").trim();
			// token="AhhR68bNcUQffe5dLZU+zA5mM93ybyd/NXuaKa3xaV8FJVEvT9kPLHlv3I/qoxfzAB0fqAEZeCRZCiBxIRaDKnS8A+7KtQSz0z+4QMEy8o2SDReUcbaHcuuU28xMXYDBEprFLXcaNfmrnpKjE6BE0l0A0nwQvIiXQRkjhxGkyCg=";
//			 String publicKey=WebAppUtil.getProperty("publicKey"); 
			 String account="";
			try {
				 account=EncryptUtil.decrypt(token);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			IUser user=ContextUtil.getCurrentUser();
			if(user==null){
				SecurityUtil.login(req,account, "", true);
			}
			res.sendRedirect(req.getContextPath() +"/mobile/bpm/myToDo.html");
			return;
		}
		chain.doFilter(request, response);
		
		
	}
	
	public static void main(String[] args) throws Exception {
	
//		String privateKey="rO0ABXNyABRqYXZhLnNlY3VyaXR5LktleVJlcL35T7OImqVDAgAETAAJYWxnb3JpdGhtdAASTGphdmEvbGFuZy9TdHJpbmc7WwAHZW5jb2RlZHQAAltCTAAGZm9ybWF0cQB+AAFMAAR0eXBldAAbTGphdmEvc2VjdXJpdHkvS2V5UmVwJFR5cGU7eHB0AANSU0F1cgACW0Ks8xf4BghU4AIAAHhwAAACezCCAncCAQAwDQYJKoZIhvcNAQEBBQAEggJhMIICXQIBAAKBgQDYjtoAU61khFrjB1gdK3EweI4quA4wgcCKVvEcS1bTAk00kiQ67Bj51qrZZ2BFmULuyCX9rh4dhEran6KWRwGK8wmD6o0Pb6KGqiKItSDK4S4lrihGa/wZon4rwpcfO1/lOTfKBTuvyPLi8RQYXExgch4B+Wdch4FCDYPA2xO0DQIDAQABAoGAFPWmhdeTdaIVxdllHtWgi+dvIxVTUkCMqRcHGQz1p1CWtlrapNVLCYtMV+RYfgP6ZW/7tVTP112BfS1sKA1RSbrtIQG7TnVhBLqSjvbhX1aZRNaEMTGzO7ZRz5jVEoULwqqKmQMh2MMPbSsFu8HzEkzQjedMCt1QvVweJdP2UIECQQDurbhlz3pnK5YAzxUYLuh3NhxvZ/GmmVz6v8vfjO2JUfvC/3ppgMh37tFBjUKCQQO3KI+LVn4B/JEoKarqEe3tAkEA6EYqP25EplTfUsiiQDnBe1bc+r6RJzMi8bOPxIBFjC+VVyxC8kQ18MBBiKoeT0ulDx02YY6AbeI3inEiFc8aoQJAH4ntF+b2sbNcuvaiPvPT3AzWbRI7KFyToL6/XebtbHvc3MONlWtjEhYIqLTV2QhmSUmezja7p9+L/taisxNzcQJBAK9iE5Jzo3hoi3wJrKGMOrDz5MWcUSPlM9SHPd4k8N6qKzx4WlBt+sC/mnwj3+EGACsKZr6BCC5wanmpdRA8oiECQQDpI2cPAOE2zdiE5znB0PyS1rIYf0fA46eksuMuYKBfQzT7z0/eP0tyLXhgs4c55HA79HY/dIlcYdfDy88AmO1CdAAGUEtDUyM4fnIAGWphdmEuc2VjdXJpdHkuS2V5UmVwJFR5cGUAAAAAAAAAABIAAHhyAA5qYXZhLmxhbmcuRW51bQAAAAAAAAAAEgAAeHB0AAdQUklWQVRF";
		String token="AhhR68bNcUQffe5dLZU+zA5mM93ybyd/NXuaKa3xaV8FJVEvT9kPLHlv3I/qoxfzAB0fqAEZeCRZCiBxIRaDKnS8A+7KtQSz0z+4QMEy8o2SDReUcbaHcuuU28xMXYDBEprFLXcaNfmrnpKjE6BE0l0A0nwQvIiXQRkjhxGkyCg=";
		String publicKey="rO0ABXNyABRqYXZhLnNlY3VyaXR5LktleVJlcL35T7OImqVDAgAETAAJYWxnb3JpdGhtdAASTGphdmEvbGFuZy9TdHJpbmc7WwAHZW5jb2RlZHQAAltCTAAGZm9ybWF0cQB+AAFMAAR0eXBldAAbTGphdmEvc2VjdXJpdHkvS2V5UmVwJFR5cGU7eHB0AANSU0F1cgACW0Ks8xf4BghU4AIAAHhwAAAAojCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEA2I7aAFOtZIRa4wdYHStxMHiOKrgOMIHAilbxHEtW0wJNNJIkOuwY+daq2WdgRZlC7sgl/a4eHYRK2p+ilkcBivMJg+qND2+ihqoiiLUgyuEuJa4oRmv8GaJ+K8KXHztf5Tk3ygU7r8jy4vEUGFxMYHIeAflnXIeBQg2DwNsTtA0CAwEAAXQABVguNTA5fnIAGWphdmEuc2VjdXJpdHkuS2V5UmVwJFR5cGUAAAAAAAAAABIAAHhyAA5qYXZhLmxhbmcuRW51bQAAAAAAAAAAEgAAeHB0AAZQVUJMSUM="; 
		String account=EncryptUtil.decryptPublicKey(publicKey, token);
		System.out.println(account);
		//		String  account=EncryptUtil.decryptPrivateKey(privateKey, token);
//		System.out.println(account);
		
	}

	@Override
	public void destroy() {

	}
	
	
	

}
