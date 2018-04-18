package com.redxun.saweb.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.redxun.core.database.datasource.DbContextHolder;
import com.redxun.sys.core.entity.SysInst;
import com.redxun.sys.core.manager.SysInstManager;

public class DataSourceFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
		
		String serverName=req.getServerName();
		String dsName=getDsName( serverName);
		
		try {
			DbContextHolder.clearDataSource();
			DbContextHolder.setDataSource(dsName);
			
		}  catch (Exception e) {
			e.printStackTrace();
		}
		
		res.addHeader("Access-Control-Allow-Origin", "*");
		res.addHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With");
		
		chain.doFilter(request, response);
	}
	
	/**
	 * 获取数据源名称。
	 * @param serverName	服务器域名。
	 * @return
	 */
	private String getDsName(String serverName){
		serverName=serverName.toLowerCase();
		List<SysInst> list=SysInstManager.getTenants();
		for(SysInst inst:list){
			String domain=inst.getDomain().toLowerCase();
			if(serverName.indexOf(domain)!=-1){
				return inst.getDsAlias();
			}
		}
		return "";
	}

	@Override
	public void destroy() {
	}
	

}
