package com.redxun.saweb.filter;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redxun.saweb.servlet.GenericResponseWrapper;
/**
 * 
 * @author mansan
 *  @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class JsonpCallbackFilter implements Filter {

  private static Logger log = LoggerFactory.getLogger(JsonpCallbackFilter.class);

  public void init(FilterConfig fConfig) throws ServletException {}

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    Map<String, String[]> parms = httpRequest.getParameterMap();

    if (parms.containsKey("callback")) {
      if (log.isDebugEnabled())
          log.debug("Wrapping response with JSONP callback '" + parms.get("callback")[0] + "'");

      OutputStream out = httpResponse.getOutputStream();

      GenericResponseWrapper wrapper = new GenericResponseWrapper(httpResponse);

      chain.doFilter(request, wrapper);

      //handles the content-size truncation
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
      outputStream.write(new String(parms.get("callback")[0] + "(").getBytes() );
      outputStream.write(wrapper.getData());
      outputStream.write(new String(");").getBytes());
      byte jsonpResponse[] = outputStream.toByteArray( );

      wrapper.setContentType("text/javascript;charset=UTF-8");
      wrapper.setContentLength(jsonpResponse.length);

      out.write(jsonpResponse);

      out.close();
    
    } else {
      chain.doFilter(request, response);
    }
  }

  public void destroy() {}
}

