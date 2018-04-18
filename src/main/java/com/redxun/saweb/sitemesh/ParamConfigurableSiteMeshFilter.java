package com.redxun.saweb.sitemesh;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.sitemesh.DecoratorSelector;
import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.sitemesh.webapp.WebAppContext;
  
/**
 * 扩展实现注入基于request参数decorator值进行动态定位装饰器的选择器
 *  @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class ParamConfigurableSiteMeshFilter extends ConfigurableSiteMeshFilter {
	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletResponse response=(HttpServletResponse)servletResponse;
		
		super.doFilter(servletRequest, servletResponse, filterChain);
	}
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        //获取原有默认配置装饰选择器
        DecoratorSelector<WebAppContext> defaultDecoratorSelector = builder.getDecoratorSelector();
        //赋给自定义装饰选择器，则自定义规则未匹配时调用默认选择器获取
        builder.setCustomDecoratorSelector(new ParamDecoratorSelector(defaultDecoratorSelector));
    }
}
