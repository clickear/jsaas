package com.redxun.hr.core.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.net.aso.q;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.hr.core.entity.HrErrandsRegister;
import com.redxun.hr.core.manager.HrErrandsRegisterManager;

/**
 * [HrErrandsRegister]管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/hr/core/hrErrandsRegister/")
public class HrErrandsRegisterController extends BaseListController {
	@Resource
	HrErrandsRegisterManager hrErrandsRegisterManager;

	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}

	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uId = request.getParameter("ids");
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				hrErrandsRegisterManager.delete(id);
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
		HrErrandsRegister hrErrandsRegister = null;
		if (StringUtils.isNotEmpty(pkId)) {
			hrErrandsRegister = hrErrandsRegisterManager.get(pkId);
		} else {
			hrErrandsRegister = new HrErrandsRegister();
		}
		return getPathView(request).addObject("hrErrandsRegister", hrErrandsRegister);
	}

	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		// 复制添加
		String forCopy = request.getParameter("forCopy");
		HrErrandsRegister hrErrandsRegister = null;
		if (StringUtils.isNotEmpty(pkId)) {
			hrErrandsRegister = hrErrandsRegisterManager.get(pkId);
			if ("true".equals(forCopy)) {
				hrErrandsRegister.setErId(null);
			}
		} else {
			hrErrandsRegister = new HrErrandsRegister();
		}
		return getPathView(request).addObject("hrErrandsRegister", hrErrandsRegister);
	}
	
	@RequestMapping("getByType")
	@ResponseBody
	public JsonPageResult<HrErrandsRegister> getByType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String type=request.getParameter("type");
		List<HrErrandsRegister> hrErrandsRegisters=new ArrayList<HrErrandsRegister>();
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		if(type.equals(HrErrandsRegister.TYPE_VACATION)||type.equals(HrErrandsRegister.TYPE_OVERTIME)||type.equals(HrErrandsRegister.TYPE_TRANS_REST)){
			hrErrandsRegisters=hrErrandsRegisterManager.getByUserIdAndType(ContextUtil.getCurrentUserId(),type,ContextUtil.getCurrentTenantId(),queryFilter);
		}
		return new JsonPageResult<HrErrandsRegister>(hrErrandsRegisters, queryFilter.getPage().getTotalItems());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return hrErrandsRegisterManager;
	}

}
