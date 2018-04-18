package com.redxun.saweb.security.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.redxun.core.json.JsonResult;
import com.redxun.core.util.StringUtil;

/**
 * XSS安全过滤器。
 * <pre>
 *  这个功能是为了放置XSS攻击。
 *  如果有Xss攻击：
 *  	1.表单提交方式，平台将去到一个提示页面。
 *  	2.AJAX提交方式，弹出提示信息。
 *  可以配置某些不需要检测的URL.
 * </pre>
 * @author ray
 *
 */
public class SecurityFilter  implements Filter {
	
	//防止<a></a>,<a>,<a 类似的标签输入
	private Pattern regex = Pattern.compile("<(\\S*?)[^>]*>.*?</\\1>|<[^>]+>|<\\S+|'", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.DOTALL | Pattern.MULTILINE);
	
	@Resource(name="xssUrl")
	RegMatchers xssMatchers;
	
	@Resource(name="csrfUrl")
	RegMatchers crsfMatchers;
	
	@Resource(name="tokenUrl")
	RegMatchers tokenMatchers;
	
	/**
	 * 跨站token标识
	 */
	public static String CRSF_TOKEN="crsf_token";
	
	

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse res=(HttpServletResponse) response;
		chain.doFilter(request, response);
		//RequestWrapper req=new RequestWrapper(orginReq) ;
		
//		String reqUrl=req.getRequestURI();
//		
//
//		String referer = req.getHeader("Referer");   
//		String serverName = req.getServerName();
//		
//		if(reqUrl.indexOf("/commons/csrf.jsp")!=-1 || reqUrl.indexOf("/commons/xss.jsp")!=-1){
//			chain.doFilter(request, response);
//			return;
//		}
//		
//		
//		
//		setToken(req, reqUrl);
//		
//		//请求不是来自本网站。
//		if(null != referer&&referer.indexOf(serverName) < 0){
//			//不包含URL
//			boolean isIngoreUrl=crsfMatchers.isContainUrl(referer);
//			if(!isIngoreUrl){
//				req.getRequestDispatcher("/commons/csrf.jsp").forward(req, res);
//				return;
//			}
//		}  
//		//页面是否忽略。
//		boolean isXssIngoreUrl=xssMatchers.isContainUrl(reqUrl);
//		if(isXssIngoreUrl){
//			chain.doFilter(request, response);
//		}
//		else{
//			//检测是否有XSS攻击。
//			boolean hasXss= checkXss(req);
//			if(hasXss){
//				handXss(req,response);
//			}
//			else{
//				chain.doFilter(request, response);
//			}
//		}
	}
	
	void setToken(HttpServletRequest req,String url){
		if(!tokenMatchers.isContainUrl(url)) return;
		HttpSession session= req.getSession();
		String uid=UUID.randomUUID().toString();
		session.setAttribute(CRSF_TOKEN, uid);
	}
	

	
	/**
	 * 处理xss.
	 * @param req
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void handXss(HttpServletRequest req,ServletResponse response) throws ServletException, IOException{
		String reqWith=req.getHeader("x-requested-with");
		//非ajax请求。
		if(StringUtil.isEmpty(reqWith)){
			req.getRequestDispatcher("/commons/xss.jsp")
				.forward(req, response);
		}
		else{
			response.setContentType("text/html;charset=utf-8");
			JsonResult<Void> resultMessage= new JsonResult<Void>(false, "检测到XSS攻击，请检是否输入了HTML字符！");
			response.getWriter().print(resultMessage);
		}
	}
	

	
	
	

	@Override
	public void init(FilterConfig config) throws ServletException {
	}
	
	/**
	 * 判断输入是否有XSS注入问题。
	 * @param request
	 * @return
	 */
	private boolean checkXss(HttpServletRequest request){
		Enumeration<?> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String key = params.nextElement().toString();
			String[] vals=request.getParameterValues(key);
			String val=StringUtil.join(vals, "");
			if(StringUtil.isEmpty(val)) continue;
			
			Matcher regexMatcher = regex.matcher(val);
			if(regexMatcher.find()){
				return true;
			}
		}
		return false;
	}

	
	
}
