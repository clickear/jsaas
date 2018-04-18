package com.redxun.saweb.security.filter;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;

import com.redxun.saweb.security.metadata.SecurityFilterMetadataSource;

/**
 * 安全拦截器
 * @author csx
 *  @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class PermissionFilter extends  AbstractSecurityInterceptor implements Filter{
    private static final Log logger=LogFactory.getLog(PermissionFilter.class);
    
    private SecurityFilterMetadataSource securityFilterMetadataSource;
    
    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return securityFilterMetadataSource;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       
       /*FilterInvocation fi = new FilterInvocation(request, response, chain);
       Collection<ConfigAttribute> configAttributes = this.obtainSecurityMetadataSource().getAttributes(fi);
       
       if(!configAttributes.contains(SecurityConsts.ROLE_ANONYMOUS)){
           fi.getChain().doFilter(request, response);
       }*/
         SecurityContext securityContext = SecurityContextHolder.getContext();
        
        Authentication auth = securityContext.getAuthentication();
        Collection<GrantedAuthority> auths=(Collection<GrantedAuthority>)auth.getAuthorities();
        for(GrantedAuthority gath:auths){
            logger.info("--------------name:"+ gath.getAuthority());
        }
       
       chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        
    }

	public SecurityFilterMetadataSource getSecurityFilterMetadataSource() {
		return securityFilterMetadataSource;
	}

	public void setSecurityFilterMetadataSource(
			SecurityFilterMetadataSource securityFilterMetadataSource) {
		this.securityFilterMetadataSource = securityFilterMetadataSource;
	}

    
    
}
