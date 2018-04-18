package com.redxun.saweb.filter;
import java.io.IOException;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.security.provider.ISecurityDataProvider;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.manager.SysAccountManager;
/**
 * 权限拦载器
 * 
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn） 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class SecurityInterceptorFilter extends OncePerRequestFilter {
	private PathMatcher matcher = new AntPathMatcher();
	private ISecurityDataProvider securityDataProvider;
	@Resource
	private AuthenticationManager authenticationManager;
	
	@Resource
	SysAccountManager sysAccountManager;
	
	public void setSecurityDataProvider(ISecurityDataProvider securityDataProvider) {
		this.securityDataProvider = securityDataProvider;
	}
	
	
	
	protected String getReqPath(HttpServletRequest request){
		String url = request.getRequestURI();
		// 若有contextPath,则切出来
		if (org.springframework.util.StringUtils.hasLength(request.getContextPath())) {
			String contextPath = request.getContextPath();
			int index = url.indexOf(contextPath);
			if (index != -1) {
				url = url.substring(index + contextPath.length());
			}
		}	
		return url;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		ContextUtil.cleanAll();
		
		
		String url=getReqPath(request);

		Authentication auth= SecurityContextHolder.getContext().getAuthentication();//取得认证器
		// 是否包括在匿名访问的地址中
		if ("anonymousUser".equals(auth.getPrincipal().toString())) {
			for (String aUrl : securityDataProvider.getAnonymousUrls()) {
				if (matcher.match(aUrl, url)) {
					chain.doFilter(request, response);
					return;
				}
			}
//			if(url.indexOf("index.do")!=-1){
//				throw new AccessDeniedException("当前请求不允许匿名访问!");
//			}
//			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//			request.getRequestDispatcher("/commons/404.jsp").forward(request,response);
//			return;
			throw new AccessDeniedException("当前请求不允许匿名访问!");
		}
	
		IUser user = ContextUtil.getCurrentUser();
		// 是否为超级管理员
		boolean isSuperUser =false;
		// 是否为管理机构的超级管理员
		if (WebAppUtil.isSaasMgrUser() && user.isSuperAdmin()) {
			isSuperUser = true;
		}
		// 若为超级管理员,则允许访问
		if (isSuperUser) {
			chain.doFilter(request, response);
			return;
		}
		// 是否为租户管理员
		// 允许访问所有的Saas菜单地址
		if (user.isSaaSAdmin()) {
			String instType= ContextUtil.getTenant().getInstType();
			if (StringUtil.isEmpty(instType) 
					|| securityDataProvider.getTenantUrlSet().get(instType).contains(url)) {
				chain.doFilter(request, response);
				return;
			}
		}
		// 公共URL
		for (String pUrl : securityDataProvider.getPublicUrls()) {
			if (matcher.match(pUrl, url)) {
				chain.doFilter(request, response);
				return;
			}
		}
		// 如果不包括在配置的菜单访问地址中,则默认允许访问
		if (!securityDataProvider.getMenuGroupIdsMap().containsKey(url)) {
			chain.doFilter(request, response);
			return;
		}
		Set<String> groupIdSet = securityDataProvider.getMenuGroupIdsMap().get(url);
		boolean isIncludeGroupId = false;
		for (GrantedAuthority au : auth.getAuthorities()) {
			if (groupIdSet!=null&&groupIdSet.contains(au.getAuthority())) {
				isIncludeGroupId = true;
				break;
			}
		}
		if (!isIncludeGroupId) {
			throw new AccessDeniedException("Access is denied! Url:" + url + " User:"
					+ SecurityContextHolder.getContext().getAuthentication().getName());
		}
		// 进行下一个Filter
		chain.doFilter(request, response);
	}
	@Override
	public void afterPropertiesSet() throws ServletException {
		securityDataProvider.reloadSecurityDataCache();
	}
}
