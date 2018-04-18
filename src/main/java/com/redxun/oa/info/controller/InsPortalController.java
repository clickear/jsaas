package com.redxun.oa.info.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.engine.FreemarkEngine;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.oa.info.entity.InsColType;
import com.redxun.oa.info.entity.InsColumn;
import com.redxun.oa.info.entity.InsPortCol;
import com.redxun.oa.info.entity.InsPortal;
import com.redxun.oa.info.entity.InsPortalParams;
import com.redxun.oa.info.entity.PortalColumn;
import com.redxun.oa.info.entity.PortalConfig;
import com.redxun.oa.info.manager.InsColumnManager;
import com.redxun.oa.info.manager.InsPortColManager;
import com.redxun.oa.info.manager.InsPortalManager;
import com.redxun.oa.info.service.PortalConfigService;
import com.redxun.org.api.model.ITenant;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.WebAppUtil;

/**
 * 门户管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/oa/info/insPortal/")
public class InsPortalController extends TenantListController {
	@Resource
	InsPortalManager insPortalManager;
	@Resource
	InsPortColManager insPortColManager;
	@Resource
	InsColumnManager insColumnManager;
	@Resource
	FreemarkEngine freemarkEngine;
	@Resource
	PortalConfigService portalConfigService;

	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uId = request.getParameter("ids");
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				insPortalManager.delete(id);
			}
		}
		return new JsonResult(true, "成功删除！");
	}

	/**
	 * 删除门户中的栏目，在ShowEdit和PersonShowEdit页面中点击每个栏目panel上的x关闭键时触发
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delCol")
	@ResponseBody
	public JsonResult delCol(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String colId = request.getParameter("colId");
		String portId = request.getParameter("portId");
		if (StringUtils.isNotEmpty(colId)) {
			InsPortal insPortal = insPortalManager.get(portId);
			InsPortCol insPortCol = insPortColManager.getByPortCol(portId, colId);// 获得这个中间表
			insPortal.getInsPortCols().remove(insPortCol);// 在portal门户中的中间表set集合中删去这个中间表
			insPortColManager.delByPortCol(portId, colId);// 在中间表中删去这个中间表
		}
		return new JsonResult(true, "成功删除！");
	}
	
	/**
	 * 删除门户中的栏目，在ShowEdit和PersonShowEdit页面中点击每个栏目panel上的x关闭键时触发
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("cleanAll")
	@ResponseBody
	public JsonResult cleanAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String portId = request.getParameter("portId");
		if (StringUtils.isNotEmpty(portId)) {
			InsPortal insPortal = insPortalManager.get(portId);
			insPortal.getInsPortCols().clear();//
			insPortColManager.delByPortal(portId);// 在中间表中删去这个中间表
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
		List<InsPortCol> portcols = insPortColManager.getByPortId(pkId);// 获取该门户的所有栏目的中间表
		InsPortal insPortal = null;
		StringBuffer colName = new StringBuffer();
		ModelAndView mv = getPathView(request);
		for (InsPortCol portcol : portcols) {// 遍历每个栏目，将数据存入StringBuffer
			if (StringUtils.isNotEmpty(portcol.getColId())) {
				InsColumn insColumn = insColumnManager.get(portcol.getColId());
				colName.append(insColumn.getName()).append(",");
			}
		}
		if (colName.length() > 0) {// 删除最后一个逗号
			colName.deleteCharAt(colName.length() - 1);
		}
		mv.addObject("colName", colName.toString());// 传入前台
		if (StringUtils.isNotEmpty(pkId)) {// 是否有这个门户，如果有就传入前台
			insPortal = insPortalManager.get(pkId);
		}
		return mv.addObject("insPortal", insPortal);
	}

	/**
	 * 公司门户的展示页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("show")
	public ModelAndView show(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String key = request.getParameter("key");
		String userId = ContextUtil.getCurrentUserId();
		String tenantId = ContextUtil.getCurrentTenantId();
		InsPortal insPortal = null;
		// 判断现在是个人门户还是公司门户
		if ("company".equals(key)) {// 判断这个租户Id是否拥有COMPANY门户，有则使用COMPANY门户，没有则使用GLOBAL_COMPANY
				insPortal = insPortalManager.getByKey(InsPortal.TYPE_GLOBAL_COMPANY, ITenant.PUBLIC_TENANT_ID);
		} else if ("personal".equals(key)) {// 判断这个租户Id是否拥有PERSONAL门户，有则使用PERSONAL门户，没有则使用GLOBAL_PERSONAL
			insPortal = insPortalManager.getByIdKey(InsPortal.TYPE_PERSONAL, tenantId, userId);
			if (insPortal == null) {
				insPortal = insPortalManager.getByKey(InsPortal.TYPE_GLOBAL_PERSONAL, ITenant.PUBLIC_TENANT_ID);
			}
		}

		ModelAndView mv = getPathView(request);
		List<InsPortCol> portcols = insPortColManager.getByPortId(insPortal.getPortId());// 得到所有门户栏目的中间表
		List<PortalColumn> portalCols = new ArrayList<PortalColumn>();// 传输类,用于页面的显示
		for (InsPortCol portcol : portcols) {// 遍历中间表,得到所有栏目
			InsColumn insColumn = insColumnManager.get(portcol.getColId());// 获得这个栏目
			if (!"DISABLED".equals(insColumn.getEnabled())) {
				InsColType insColType = insColumn.getInsColType();// 获得这个栏目的类型
				// 新建传输类,用于装数据在前台显示
				PortalColumn portalCol = new PortalColumn(insColumn.getName(), portcol.getColNum(), portcol.getSn(), portcol.getHeight(), insColType.getUrl(), insColType.getMoreUrl(), portcol.getColId());

				String url = insColType.getUrl().replace("{pageSize}", portcol.getInsColumn().getNumsOfPage() + "");
				url = url.replace("{colId}", portcol.getColId());
				if (!url.toUpperCase().startsWith("HTTP")) {// 判断是外网地址还是内网地址
					url = request.getContextPath() + url;
				}
				// 将moreUrl中需要更换的栏目Id进行赋值
				String moreUrl = insColType.getMoreUrl().replace("{pageSize}", portcol.getInsColumn().getNumsOfPage() + "");
				moreUrl = moreUrl.replace("{colId}", portcol.getColId());
				if (!moreUrl.toUpperCase().startsWith("HTTP")) {
					moreUrl = request.getContextPath() + moreUrl;
				}

				portalCol.setMoreUrl(moreUrl);

				portalCol.setLoadType(insColType.getLoadType());

				portalCol.setUrl(url);
				portalCol.setIconCls(insColType.getIconCls());
				portalCols.add(portalCol);
			}
		}

		String[] widths = insPortal.getColWidths().split(",");// Portal的宽度和列数
		StringBuffer colWidths = new StringBuffer();
		for (int i = 0; i < widths.length; i++) {
			if (widths[i].contains("%")) {
				widths[i] = "'" + widths[i] + "'";
			}
			colWidths.append(widths[i]).append(",");
		}
		if (colWidths.length() > 0) {
			colWidths.deleteCharAt(colWidths.length() - 1);
		}

		mv.addObject("colWidths", colWidths.toString());
		mv.addObject("portalCols", iJson.toJson(portalCols));
		return mv.addObject("insPortal", insPortal);
	}

	@RequestMapping("getPortalConfigs")
	@ResponseBody
	public Collection<PortalConfig> getPortalConfigs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return portalConfigService.getPortalConfigMap().values();
	}

	@RequestMapping("getPortalHtml")
	public ModelAndView getPortalHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String colId = request.getParameter("colId");
		InsColumn insColumn = insColumnManager.get(colId);
		InsColType insColType = insColumn.getInsColType();

		InsPortalParams param = new InsPortalParams();
		param.setPageSize(insColumn.getNumsOfPage());
		String tenantId = ContextUtil.getCurrentTenantId();
		String userId = ContextUtil.getCurrentUserId();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("colId", colId);
		param.setParams(params);
		param.setTenantId(tenantId);
		param.setUserId(userId);
		String html = null;
		try {
			String templateId = insColType.getTempId();
			PortalConfig portalConfig = portalConfigService.getPortalConfigMap().get(templateId);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("ctxPath", request.getContextPath());
			if(StringUtils.isNotEmpty(portalConfig.getService())){
				String[] services = portalConfig.getService().split("[.]");
				Object serviceBean = (Object) WebAppUtil.getBean(services[0]);
				Method method = serviceBean.getClass().getDeclaredMethod(services[1], InsPortalParams.class);
				// 计算到结果
				Object result = method.invoke(serviceBean, param);
				if (result instanceof Map) {
					model.putAll((Map) result);
				} else {
					model.put("result", result);
				}
			}
			html = freemarkEngine.mergeTemplateIntoString("portal/" + templateId + ".ftl", model);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			html = "模板加载出错！请联系管理员！";
		}
		return getPathView(request).addObject("html", html);
	}


	/**
	 * 公司门户的编辑页面，内容一切同上
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("showEdit")
	public ModelAndView showEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String tenantId = ContextUtil.getCurrentTenantId();
		InsPortal insPortal = insPortalManager.getByKey(InsPortal.TYPE_COMPANY, tenantId);
		if (insPortal == null) {
			InsPortal insPortal1 = insPortalManager.getByKey(InsPortal.TYPE_GLOBAL_COMPANY, ITenant.PUBLIC_TENANT_ID);
			insPortal = new InsPortal();
			insPortal.setPortId(idGenerator.getSID());
			insPortal.setKey("COMPANY");
			insPortal.setName("公司");
			insPortal.setIsDefault("NO");
			insPortal.setTenantId(tenantId);
			insPortal.setColNums(insPortal1.getColNums());
			insPortal.setColWidths(insPortal1.getColWidths());
			insPortalManager.create(insPortal);
		}
		ModelAndView mv = getPathView(request);
		List<InsPortCol> portcols = insPortColManager.getByPortId(insPortal.getPortId());
		List<PortalColumn> portalCols = new ArrayList<PortalColumn>();
		for (InsPortCol portcol : portcols) {
			InsColumn insColumn = insColumnManager.get(portcol.getColId());
			String closeAllow = insColumn.getAllowClose();
			InsColType insColType = insColumn.getInsColType();

			PortalColumn portalCol = new PortalColumn(insColumn.getName(), portcol.getColNum(), portcol.getSn(), portcol.getHeight(), insColType.getUrl(), insColType.getMoreUrl(), portcol.getColId(), closeAllow);
			String url = insColType.getUrl().replace("{pageSize}", portcol.getInsColumn().getNumsOfPage() + "");
			url = url.replace("{colId}", portcol.getColId());
			if (!url.toUpperCase().startsWith("HTTP")) {// 判断是外网地址还是内网地址
				url = request.getContextPath() + url;
			}
			// 将moreUrl中需要更换的栏目Id进行赋值
			String moreUrl = insColType.getMoreUrl().replace("{pageSize}", portcol.getInsColumn().getNumsOfPage() + "");
			moreUrl = moreUrl.replace("{colId}", portcol.getColId());
			if (!moreUrl.toUpperCase().startsWith("HTTP")) {
				moreUrl = request.getContextPath() + moreUrl;
			}
			portalCol.setUrl(url);
			portalCol.setMoreUrl(moreUrl);

			portalCol.setLoadType(insColType.getLoadType());

			portalCol.setUrl(url);
			portalCol.setIconCls(insColType.getIconCls());
			portalCols.add(portalCol);
		}

		String[] widths = insPortal.getColWidths().split(",");
		StringBuffer colWidths = new StringBuffer();
		for (int i = 0; i < widths.length; i++) {
			if (widths[i].contains("%")) {
				widths[i] = "'" + widths[i] + "'";
			}
			colWidths.append(widths[i]).append(",");
		}
		if (colWidths.length() > 0) {
			colWidths.deleteCharAt(colWidths.length() - 1);
		}
		mv.addObject("colWidths", colWidths.toString());

		mv.addObject("portalCols", iJson.toJson(portalCols));

		return mv.addObject("insPortal", insPortal);
	}

	/**
	 * 个人门户的编辑页面，内容一切同上
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("personShowEdit")
	public ModelAndView personShowEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		String tenantId = ContextUtil.getCurrentTenantId();
		InsPortal insPortal = insPortalManager.getByIdKey(InsPortal.TYPE_PERSONAL, tenantId, userId);
		if (insPortal == null) {
			InsPortal insPortal1 = insPortalManager.getByKey(InsPortal.TYPE_GLOBAL_PERSONAL, ITenant.PUBLIC_TENANT_ID);
			insPortal = new InsPortal();
			insPortal.setPortId(idGenerator.getSID());
			insPortal.setKey("PERSONAL");
			insPortal.setName("个人");
			insPortal.setIsDefault("NO");
			insPortal.setUserId(userId);
			insPortal.setColNums(insPortal1.getColNums());
			insPortal.setColWidths(insPortal1.getColWidths());
			insPortalManager.create(insPortal);
		}
		ModelAndView mv = getPathView(request);
		List<InsPortCol> portcols = insPortColManager.getByPortId(insPortal.getPortId());
		List<PortalColumn> portalCols = new ArrayList<PortalColumn>();
		for (InsPortCol portcol : portcols) {
			InsColumn insColumn = insColumnManager.get(portcol.getColId());
			String closeAllow = insColumn.getAllowClose();
			InsColType insColType = insColumn.getInsColType();
			PortalColumn portalCol = new PortalColumn(insColumn.getName(), portcol.getColNum(), portcol.getSn(), portcol.getHeight(), insColType.getUrl(), insColType.getMoreUrl(), portcol.getColId(), closeAllow);
			String url = insColType.getUrl().replace("{pageSize}", portcol.getInsColumn().getNumsOfPage() + "");
			url = url.replace("{colId}", portcol.getColId());
			if (!url.toUpperCase().startsWith("HTTP")) {// 判断是外网地址还是内网地址
				url = request.getContextPath() + url;
			}
			// 将moreUrl中需要更换的栏目Id进行赋值
			String moreUrl = insColType.getMoreUrl().replace("{pageSize}", portcol.getInsColumn().getNumsOfPage() + "");
			moreUrl = moreUrl.replace("{colId}", portcol.getColId());
			if (!moreUrl.toUpperCase().startsWith("HTTP")) {
				moreUrl = request.getContextPath() + moreUrl;
			}
			portalCol.setUrl(url);
			portalCol.setMoreUrl(moreUrl);
			portalCol.setLoadType(insColType.getLoadType());
			
			portalCol.setUrl(url);
			portalCol.setIconCls(insColType.getIconCls());
			
			portalCols.add(portalCol);
		}

		String[] widths = insPortal.getColWidths().split(",");
		StringBuffer colWidths = new StringBuffer();
		for (int i = 0; i < widths.length; i++) {
			if (widths[i].contains("%")) {
				widths[i] = "'" + widths[i] + "'";
			}
			colWidths.append(widths[i]).append(",");
		}
		if (colWidths.length() > 0) {
			colWidths.deleteCharAt(colWidths.length() - 1);
		}
		mv.addObject("colWidths", colWidths.toString());

		mv.addObject("portalCols", iJson.toJson(portalCols));

		return mv.addObject("insPortal", insPortal);
	}

	@RequestMapping("global")
	public ModelAndView global(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String key = request.getParameter("key");
		InsPortal insPortal = null;
		// 判断为全局个人还是全局公司,前台传入数据
		if ("company".equals(key)) {
			insPortal = insPortalManager.getByKey(InsPortal.TYPE_GLOBAL_COMPANY, ITenant.PUBLIC_TENANT_ID);
		} else if ("personal".equals(key)) {
			insPortal = insPortalManager.getByKey(InsPortal.TYPE_GLOBAL_PERSONAL, ITenant.PUBLIC_TENANT_ID);
		} else {
			insPortal = insPortalManager.getByKey(InsPortal.TYPE_GLOBAL_COMPANY, ITenant.PUBLIC_TENANT_ID);
		}

		ModelAndView mv = getPathView(request);
		List<InsPortCol> portcols = insPortColManager.getByPortId(insPortal.getPortId());
		List<PortalColumn> portalCols = new ArrayList<PortalColumn>();
		for (InsPortCol portcol : portcols) {
			InsColumn insColumn = insColumnManager.get(portcol.getColId());
			InsColType insColType = insColumn.getInsColType();
			PortalColumn portalCol = new PortalColumn(insColumn.getName(), portcol.getColNum(), portcol.getSn(), portcol.getHeight(), insColType.getUrl(), insColType.getMoreUrl(), portcol.getColId());
			String url = insColType.getUrl().replace("{pageSize}", portcol.getInsColumn().getNumsOfPage() + "");
			url = url.replace("{colId}", portcol.getColId());
			if (!url.toUpperCase().startsWith("HTTP")) {// 判断是外网地址还是内网地址
				url = request.getContextPath() + url;
			}
			// 将moreUrl中需要更换的栏目Id进行赋值
			String moreUrl = insColType.getMoreUrl().replace("{pageSize}", portcol.getInsColumn().getNumsOfPage() + "");
			moreUrl = moreUrl.replace("{colId}", portcol.getColId());
			if (!moreUrl.toUpperCase().startsWith("HTTP")) {
				moreUrl = request.getContextPath() + moreUrl;
			}
			portalCol.setUrl(url);
			portalCol.setMoreUrl(moreUrl);
			portalCol.setLoadType(insColType.getLoadType());

			portalCol.setUrl(url);
			portalCol.setIconCls(insColType.getIconCls());
			portalCols.add(portalCol);
		}

		String[] widths = insPortal.getColWidths().split(",");
		StringBuffer colWidths = new StringBuffer();
		for (int i = 0; i < widths.length; i++) {
			if (widths[i].contains("%")) {
				widths[i] = "'" + widths[i] + "'";
			}
			colWidths.append(widths[i]).append(",");
		}
		if (colWidths.length() > 0) {
			colWidths.deleteCharAt(colWidths.length() - 1);
		}
		mv.addObject("colWidths", colWidths.toString());

		mv.addObject("portalCols", iJson.toJson(portalCols));
		
		String portalType = "";
		if("GLOBAL-COMPANY".equals(insPortal.getKey())){
			portalType="公共企业门户";
		}else if("GLOBAL-PERSONAL".equals(insPortal.getKey())){
			portalType="公共个人门户";
		}
		return mv.addObject("insPortal", insPortal).addObject("portalType", portalType);
	}

	/**
	 * 点击编辑门户的显示页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("editPort")
	public ModelAndView editPort(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("portId");
		// 复制添加
		// String forCopy=request.getParameter("forCopy");
		ModelAndView mv = getPathView(request);
		InsPortal insPortal = null;

		List<InsPortCol> portcols = insPortColManager.getByPortId(pkId);// 获取该门户的所有栏目的中间表
		StringBuffer colName = new StringBuffer();// 栏目名字
		StringBuffer colId = new StringBuffer();// 栏目Id
		for (InsPortCol portcol : portcols) {// 遍历每个栏目，将数据存入StringBuffer
			if (StringUtils.isNotEmpty(portcol.getColId())) {
				InsColumn insColumn = insColumnManager.get(portcol.getColId());
				colName.append(insColumn.getName()).append(",");
				colId.append(insColumn.getColId()).append(",");
			}
		}
		if (colName.length() > 0) {// 删除最后一个逗号
			colName.deleteCharAt(colName.length() - 1);
			colId.deleteCharAt(colId.length() - 1);
		}
		mv.addObject("colName", colName.toString());// 传入前台
		mv.addObject("colId", colId.toString());
		if (StringUtils.isNotEmpty(pkId)) {// 是否有这个门户，如果有就传入前台
			insPortal = insPortalManager.get(pkId);
		}
		return mv.addObject("insPortal", insPortal);
	}

	/**
	 * 原来list页面中的编辑页面，不知道以后还是否需要，留作备用
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
		InsPortal insPortal = null;

		List<InsPortCol> portcols = insPortColManager.getByPortId(pkId);
		StringBuffer colName = new StringBuffer();
		StringBuffer colId = new StringBuffer();
		for (InsPortCol portcol : portcols) {
			if (StringUtils.isNotEmpty(portcol.getColId())) {
				InsColumn insColumn = insColumnManager.get(portcol.getColId());
				colName.append(insColumn.getName()).append(",");
				colId.append(insColumn.getColId()).append(",");
			}
		}
		if (colName.length() > 0) {
			colName.deleteCharAt(colName.length() - 1);
			colId.deleteCharAt(colId.length() - 1);
		}
		mv.addObject("colName", colName.toString());
		mv.addObject("colId", colId.toString());
		if (StringUtils.isNotEmpty(pkId)) {
			insPortal = insPortalManager.get(pkId);
			if ("true".equals(forCopy)) {
				insPortal.setPortId(null);
			}
		} else {
			insPortal = new InsPortal();
		}
		return mv.addObject("insPortal", insPortal);
	}

	/**
	 * 新增栏目的页面，在ShowEdit和PersonShowEdit页面点新增栏目
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addColumn")
	public ModelAndView addColumn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("portId");
		// 复制添加
		// String forCopy=request.getParameter("forCopy");
		ModelAndView mv = getPathView(request);
		InsPortal insPortal = null;

		List<InsPortCol> portcols = insPortColManager.getByPortId(pkId);// 获取该门户的所有栏目的中间表
		StringBuffer colName = new StringBuffer();// 栏目名字
		StringBuffer colId = new StringBuffer();// 栏目Id
		for (InsPortCol portcol : portcols) {// 遍历每个栏目，将数据存入StringBuffer
			if (StringUtils.isNotEmpty(portcol.getColId())) {
				InsColumn insColumn = insColumnManager.get(portcol.getColId());
				colName.append(insColumn.getName()).append(",");
				colId.append(insColumn.getColId()).append(",");
			}
		}
		if (colName.length() > 0) {// 删除最后一个逗号
			colName.deleteCharAt(colName.length() - 1);
			colId.deleteCharAt(colId.length() - 1);
		}
		mv.addObject("colName", colName.toString());// 传入前台
		mv.addObject("colId", colId.toString());
		if (StringUtils.isNotEmpty(pkId)) {// 是否有这个门户，如果有就传入前台
			insPortal = insPortalManager.get(pkId);
		}
		return mv.addObject("insPortal", insPortal);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return insPortalManager;
	}

}
