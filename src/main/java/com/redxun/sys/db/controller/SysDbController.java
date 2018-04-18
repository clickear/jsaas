package com.redxun.sys.db.controller;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.database.api.ITableMeta;
import com.redxun.core.database.api.model.Column;
import com.redxun.core.database.api.model.Table;
import com.redxun.core.database.datasource.DbContextHolder;
import com.redxun.core.database.impl.DBMetaImpl;
import com.redxun.core.database.model.DefaultTable;
import com.redxun.core.database.util.MetaDataUtil;
import com.redxun.core.engine.FreemarkEngine;
import com.redxun.core.entity.GridHeader;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.util.StringUtil;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.sys.core.manager.SysDataSourceManager;
import com.redxun.sys.core.util.DbUtil;

/**
 * 
 * <pre>
 * 描述：通过jdbc元数据的api查找表名或列名
 * @author cjx
 * @Email: chshxuan@163.com
 * @Copyright (c) 2014-2016 使用范围：
 * 本源代码受软件著作法保护，请在授权允许范围内使用。
 * </pre>
 */
@Controller
@RequestMapping("/sys/db/sysDb/")
public class SysDbController extends BaseListController {
	
	@Resource
	DBMetaImpl dBMetaImpl;
	@Resource
	ITableMeta ITableMeta;
	@Resource
	FreemarkEngine freemarkEngine;  
	
	
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
	/**
	 * 查找表的对话框
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("tableDialog")
	public ModelAndView tableDialog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ds = request.getParameter("ds");
		request.setAttribute("ds", ds);
		return getPathView(request);
	}
	/**
	 * 通过jdbc元数据的api查找表名
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = {"/findTableList"})
	@ResponseBody
	public List<Table> findTableList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 查找数据源
		String ds=request.getParameter("ds");
		if(StringUtil.isNotEmpty(ds)){
			DbContextHolder.setDataSource(ds);
		}
		String tableName=request.getParameter("tableName");
		List<Table> tables=dBMetaImpl.getObjectsByName(tableName);
		return tables;
	}
	/**
	 * 查找列的对话框
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("columnQueryDialog")
	public ModelAndView columnQueryDialog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ds = request.getParameter("ds");
		String queryType = request.getParameter("queryType");
		String query = request.getParameter("query");
		
		request.setAttribute("ds", ds);
		request.setAttribute("queryType", queryType);
		request.setAttribute("query", query);
		return getPathView(request);
	}
	/**
	 * 查找列的对话框
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("columnWhereDialog")
	public ModelAndView columnWhereDialog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ds = request.getParameter("ds");
		request.setAttribute("ds", ds);
		String tableName = request.getParameter("tableName");
		request.setAttribute("tableName", tableName);
		return getPathView(request);
	}
	/**
	 * 通过jdbc元数据的api查找列名(返回json用于表格)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = {"/findColumnList"})
	@ResponseBody
	public List<GridHeader> findColumnList(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		String query=RequestUtil.getString(request,"query");
		String queryType=RequestUtil.getString(request,"queryType");
		String ds=RequestUtil.getString(request,"ds");
	
		if(StringUtil.isNotEmpty(ds)){
			DbContextHolder.setDataSource(ds);
		}
		String sql=query;
		if("table".equals(queryType)) {
			sql="select * from " + query;
		}
		else if("freeMakerSql".equals(queryType)) {
			sql=freemarkEngine.parseByStringTemplate(new HashMap(), query);
		}
		
		List<GridHeader> list= DbUtil.getGridHeaders(sql);
		return list;
	}
	@Override
	public BaseManager getBaseManager() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
