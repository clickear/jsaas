package com.redxun.oa.info.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.JoinType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.IJson;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.Page;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.QueryParam;
import com.redxun.oa.info.entity.InsColType;
import com.redxun.oa.info.entity.InsColumn;
import com.redxun.oa.info.manager.InsColTypeManager;
import com.redxun.oa.info.manager.InsColumnManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.QueryFilterBuilder;

/**
 * [InsColumn]管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/oa/info/insColumn/")
public class InsColumnController extends TenantListController {
	@Resource
	InsColumnManager insColumnManager;
	@Resource
	InsColTypeManager insColTypeManager;

	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uId = request.getParameter("ids");
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				insColumnManager.delete(id);
			}
		}
		return new JsonResult(true, "成功删除！");
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
		String pkId = request.getParameter("pkId");
		InsColumn insColumn = null;
		if (StringUtils.isNotBlank(pkId)) {
			insColumn = insColumnManager.get(pkId);
		} else {
			insColumn = new InsColumn();
		}
		return getPathView(request).addObject("insColumn", insColumn);
	}

	/**
	 * 获得租户的栏目列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getColumns")
	@ResponseBody
	public List<InsColumn> getColumns(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<InsColumn> insColumns = insColumnManager.getAllByTenantId(ContextUtil.getCurrentTenantId());
		return insColumns;
	}
	
	/**
	 * 获得租户的栏目列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getNewsColumns")
	@ResponseBody
	public List<InsColumn> getNewsColumns(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter qf = new QueryFilter();
		qf.addFieldParam("insColType.key", "news");
		qf.addFieldParam("tenantId", ContextUtil.getCurrentTenantId());
		List<InsColumn> insColumns = insColumnManager.getAll(qf);
		return insColumns;
	}

	/**
	 * 在list页面中栏目的编辑按钮
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		// 复制添加
		String forCopy = request.getParameter("forCopy");
		ModelAndView mv = getPathView(request);
		String typeName = "";
		String typeId = "";
		InsColumn insColumn = null;
		if (StringUtils.isNotEmpty(pkId)) {// 判断是否有这个Id的栏目，有就传入这个栏目
			insColumn = insColumnManager.get(pkId);
			typeName = insColumn.getInsColType().getName();// 栏目类型的名字
			typeId = insColumn.getInsColType().getTypeId();// 栏目类型的ID
			if ("true".equals(forCopy)) {
				insColumn.setColId(null);
			}
		} else {// 没有就新建
			insColumn = new InsColumn();
		}
		mv.addObject("typeName", typeName);// 栏目类型的名字
		mv.addObject("typeId", typeId);// 栏目类型的Id
		return mv.addObject("insColumn", insColumn);
	}

	/**
	 * 栏目根据名称的搜索
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchByName")
	@ResponseBody
	public JsonPageResult<InsColumn> searchByName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String colName = request.getParameter("key");
		Page page = QueryFilterBuilder.createPage(request);
		List<InsColumn> list = insColumnManager.getByName(colName, page);
		return new JsonPageResult<InsColumn>(list, page.getTotalItems());
	}

	/**
	 * 新增栏目的页面，在ShowEdit和PersonShowEdit页面点新增栏目
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("newCol")
	public ModelAndView newCol(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String portId = request.getParameter("portId");// 获得当前门户Id
		ModelAndView mv = getPathView(request);
		String typeName = "";
		String typeId = "";
		InsColumn insColumn = new InsColumn();
		mv.addObject("portId", portId);
		mv.addObject("typeName", typeName);// 栏目类型的名字
		mv.addObject("typeId", typeId);// 栏目类型的Id
		return mv.addObject("insColumn", insColumn);
	}

	@RequestMapping("getByIsClose")
	@ResponseBody
	public JsonPageResult<InsColumn> getByIsClose(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		List<InsColumn> list = new ArrayList<InsColumn>();
/*		if (person.equals("yes")) {
			queryFilter.addFieldParam("allowClose", QueryParam.OP_EQUAL, "YES");
			queryFilter.addFieldParam("enabled", QueryParam.OP_EQUAL, "ENABLED");
			list = insColumnManager.getAll(queryFilter);
		} else {*/
			list = insColumnManager.getAll();

		return new JsonPageResult<InsColumn>(list, queryFilter.getPage().getTotalItems());

	}

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return insColumnManager;
	}

}
