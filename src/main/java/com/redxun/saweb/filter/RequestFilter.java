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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.redxun.core.context.HttpServletContext;

/**
 *  需要配置在web.xml中,配置格式如下所示:
 *  <pre>
 *  <filter>
    	<filter-name>requestFilter</filter-name>
    	<filter-class>com.redxun.saweb.filter.RequestFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>requestFilter</filter-name>
        <url-pattern>*.do</url-pattern>
    </filter-mapping>
 *  </pre>
 * @author csx
 *
 */
public class RequestFilter implements Filter{
	
	 private static final Log logger= LogFactory.getLog(RequestFilter.class);
	
	 public void init(FilterConfig filterConfig) throws ServletException { }

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse resp=(HttpServletResponse)response;
		//设置Request至线程变量
		HttpServletContext.setContext((HttpServletRequest) request, resp);
		
		//设置当前语言区域至请求对象中
		request.setAttribute("currentLocale", RequestContextUtils.getLocale((HttpServletRequest)request));
		
		chain.doFilter(request, response);
	}

	public void destroy() {
	}
 
}
