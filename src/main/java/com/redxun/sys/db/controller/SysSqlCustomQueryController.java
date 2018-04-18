package com.redxun.sys.db.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.core.database.datasource.DbContextHolder;
import com.redxun.core.database.model.SqlWhereColumn;
import com.redxun.core.json.JSONUtil;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.Page;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.util.StringUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.sys.db.entity.SysSqlCustomQuery;
import com.redxun.sys.db.manager.SysSqlCustomQueryManager;
/**
 * 自定义查询控制器
 * 
 * @author cjx
 */
@Controller
@RequestMapping("/sys/db/sysSqlCustomQuery/")
public class SysSqlCustomQueryController extends BaseMybatisListController {
	@Resource
	SysSqlCustomQueryManager sysSqlCustomQueryManager;
	
	
	
	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uId = RequestUtil.getString(request, "ids");
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				sysSqlCustomQueryManager.delete(id);
			}
		}
		return new JsonResult(true, "成功删除!");
	}
	/**
	 * 查看明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = RequestUtil.getString(request, "pkId");
		SysSqlCustomQuery sysSqlCustomQuery = null;
		if (StringUtils.isNotEmpty(pkId)) {
			sysSqlCustomQuery = sysSqlCustomQueryManager.get(pkId);
		} else {
			sysSqlCustomQuery = new SysSqlCustomQuery();
		}
		return getPathView(request).addObject("sysSqlCustomQuery", sysSqlCustomQuery);
	}
	
	@RequestMapping("getById")
	@ResponseBody
	public SysSqlCustomQuery getById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = RequestUtil.getString(request, "id");
		SysSqlCustomQuery sysSqlCustomQuery = sysSqlCustomQueryManager.get(pkId);
		return sysSqlCustomQuery;
	}
	
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = RequestUtil.getString(request, "pkId");
		// 复制添加
		String forCopy = request.getParameter("forCopy");
		SysSqlCustomQuery sysSqlCustomQuery = null;
		if (StringUtils.isNotEmpty(pkId)) {
			sysSqlCustomQuery = sysSqlCustomQueryManager.get(pkId);
			if ("true".equals(forCopy)) {
				sysSqlCustomQuery.setId(null);
			}
		} else {
			sysSqlCustomQuery = new SysSqlCustomQuery();
		}
		return getPathView(request).addObject("sysSqlCustomQuery", sysSqlCustomQuery);
	}
	
	@RequestMapping("preview")
	public ModelAndView preview(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = RequestUtil.getString(request, "pkId");
		SysSqlCustomQuery sysSqlCustomQuery = null;
		//JdbcCommonUtil jdbcCommonUtil = new JdbcCommonUtil();
		if (StringUtil.isNotEmpty(pkId)) {

			sysSqlCustomQuery = sysSqlCustomQueryManager.get(pkId);
			JSONArray jsonArray=JSONArray.parseArray(sysSqlCustomQuery.getWhereField());
			if(jsonArray!=null&&jsonArray.size()>0){
				List<SqlWhereColumn> whereColumns=new ArrayList<SqlWhereColumn>();
				for (Object object : jsonArray) {
					SqlWhereColumn whereColumn=(SqlWhereColumn)JSONUtil.json2Bean(object.toString(), SqlWhereColumn.class);
					whereColumns.add(whereColumn);
				}
				request.setAttribute("whereList", whereColumns);
			}
		} else {
			sysSqlCustomQuery = new SysSqlCustomQuery();
		}
		return getPathView(request).addObject("sysSqlCustomQuery", sysSqlCustomQuery);
	}
	
	@RequestMapping("help")
	public ModelAndView help(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = RequestUtil.getString(request, "pkId");
		SysSqlCustomQuery sysSqlCustomQuery = null;
		if (StringUtil.isNotEmpty(pkId)) {
			sysSqlCustomQuery = sysSqlCustomQueryManager.get(pkId);
		} else {
			sysSqlCustomQuery = new SysSqlCustomQuery();
		}
		return getPathView(request).addObject("sysSqlCustomQuery", sysSqlCustomQuery);
	}
	
	/**
	 * 
	 * 查询json
	 * doQuery("user",{user:'aaa',page:0},function(data){
	 * })
	 * 
	 * function doQuery(alias,params,callBack){
	 *  params.alias=alias;
	 *  
	 * }
	 * @Description
	 * @Title queryForJson
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws Exception 参数说明
	 * @return JsonResult 返回类型
	 * @throws
	 */
	@RequestMapping(value = "queryForJson_{alias}")
	@ResponseBody
	public JsonResult queryForJson(HttpServletRequest request, HttpServletResponse response,@PathVariable(value="alias") String alias) throws Exception {
		if(StringUtils.isEmpty(alias)) return new JsonResult<Void>(false, "请输入别名");
		String jsonStr = request.getParameter("params");
		Map<String,Object> params=handParams(jsonStr);
		
		SysSqlCustomQuery sqlCustomQuery=sysSqlCustomQueryManager.getByKey(alias);
		
		String ds=sqlCustomQuery.getDsAlias();
		//切换数据源
		DbContextHolder.setDataSource(ds);
		Page page=null;
		if(sqlCustomQuery.getIsPage()==1){
			Object pageObj= params.get("pageIndex");
			if(pageObj!=null){
				page=new Page(Integer.parseInt(pageObj.toString()) ,Integer.parseInt(sqlCustomQuery.getPageSize()));
			}
			if(null==page){
				page=new Page(0,Integer.parseInt(sqlCustomQuery.getPageSize()));
			}
		}
		List result=sysSqlCustomQueryManager.getData(sqlCustomQuery, params,page);
		
		DbContextHolder.setDefaultDataSource();
		
		return new JsonResult(true,"",result);
		
	}
	
	
	/**
	 * 通过sql查询列名(先通过自定义sql管理的key找出sql)
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = {"/listColumnByKeyForJson"})
	@ResponseBody
	public JsonResult listColumnByKeyForJson(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String key=request.getParameter("key");
		SysSqlCustomQuery sysSqlCustomQuery = sysSqlCustomQueryManager.getByKey(key);
		return new JsonResult(true,"",sysSqlCustomQuery);
	}
	
	@RequestMapping("search")
	@ResponseBody
	public List<SysSqlCustomQuery> search(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("tenantId", ContextUtil.getCurrentTenantId());
		List<SysSqlCustomQuery> querys=sysSqlCustomQueryManager.getAll(queryFilter);
		return querys;
	}
	
	@RequestMapping("getList")
	@ResponseBody
	public List<SysSqlCustomQuery> getList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String tenantId= ContextUtil.getCurrentTenantId();
		List<SysSqlCustomQuery> querys=sysSqlCustomQueryManager.getByTenantId(tenantId);
		return querys;
	}
	
	private Map<String,Object> handParams(String jsonStr){
		Map<String,Object> params=new HashMap<String, Object>();
		if(StringUtil.isEmpty(jsonStr)) return params;
		JSONObject jsonObject=JSONObject.parseObject(jsonStr);
		Set<String> set = jsonObject.keySet();
		for (String key : set) {
			params.put(key, jsonObject.get(key));
		}
		return params;
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return sysSqlCustomQueryManager;
	}
}
