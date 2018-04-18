package com.redxun.sys.core.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.engine.FreemarkEngine;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.oa.info.manager.InsNewsManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.SysReport;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.core.manager.SysReportManager;
import com.redxun.sys.core.manager.SysTreeManager;
import com.redxun.sys.core.service.JasperJrxmlConvertService;
import com.redxun.sys.core.service.JasperParamCustomService;


/**
 * 报表管理
 * @author mansan
 * @Email keitch@redxun.cn
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
@Controller
@RequestMapping("/sys/core/sysReport/")
public class SysReportController extends TenantListController {
	@Resource
	SysReportManager sysReportManager;
	
	@Resource
	private SysTreeManager sysTreeManager;
	@Resource
	private InsNewsManager insNewsManager;
	@Resource
	private JasperJrxmlConvertService convertService;
	@Resource
	private FreemarkEngine freemarkEngine;

	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		QueryFilter filter = super.getQueryFilter(request);
		// 查找分类下的模型
		String treeId = request.getParameter("treeId");
		if (StringUtils.isNotEmpty(treeId)) {
			SysTree sysTree = sysTreeManager.get(treeId);

			filter.addFieldParam("sysTree.path", sysTree.getPath());
		}
		return filter;
	}

	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uId = request.getParameter("ids");
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				sysReportManager.delete(id);
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
		SysReport sysReport = null;
		if (StringUtils.isNotEmpty(pkId)) {
			sysReport = sysReportManager.get(pkId);
		} else {
			sysReport = new SysReport();
		}
		return getPathView(request).addObject("sysReport", sysReport);
	}

	/**
	 * 编辑
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		String parentId = request.getParameter("parentId");

		SysReport sysReport = null;
		if (StringUtils.isNotEmpty(pkId)) {
			sysReport = sysReportManager.get(pkId);
		} else {
			sysReport = new SysReport();
			sysReport.setTreeId(parentId);
		}
		return getPathView(request).addObject("sysReport", sysReport);
	}

	/**
	 * 当没有分类tree的时候右边加载一个空的grid防止报错
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("listBlank")
	@ResponseBody
	public List<SysReport> listBlank(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	/**
	 * 报表预览页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("preview")
	public ModelAndView preview(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = ContextUtil.getCurrentTenantId();
		String pkId = request.getParameter("pkId");
		String key = request.getParameter("key");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		SysReport sysReport;
		if (StringUtils.isNotBlank(pkId)) {// 用Id预览报表
			sysReport = sysReportManager.get(pkId);
		} else {// 用Key预览表
			sysReport = sysReportManager.getByKey(key, userId);
		}
		
		String dbId = sysReport.getDsAlias();
		String paramConfig = sysReport.getParamConfig();
		String gridWidget=sysReportManager.transferGridJsonToMiniWidget(paramConfig);
		
		String repId = sysReport.getRepId();
		return getPathView(request).addObject("paramConfig", gridWidget).addObject("repId", repId).addObject("dbId", dbId);
	}
	
	

	/**
	 * 初始化参数页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("designerDlg")
	public ModelAndView designerDlg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");

		SysReport sysReport = sysReportManager.get(pkId);
		ModelAndView mv = getPathView(request);
		if (sysReport != null) {
			String paramConfig = sysReport.getParamConfig();
			mv.addObject("paramConfig", paramConfig);
		}

		return mv;
	}

	@RequestMapping("show")
	public void show(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = request.getParameter("dataJson");// 前台选择参数的Json
		JSONObject jsonObject = JSONObject.parseObject(json);
		String repId=jsonObject.getString("repId");
		SysReport sysReport = sysReportManager.get(repId);
		String dbId=sysReport.getDsAlias();
		
		String tempPage=jsonObject.getString("page");
		String page = StringUtils.isNotBlank(tempPage)?tempPage:"0";//页码若有则有,若无置零
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("repId", repId);
		map.put("dbId", dbId);
		map.put("json", json);
		map.put("page", page);
		map.put("bean", sysReport.getSelfHandleBean());

		if (StringUtils.isNotEmpty(sysReport.getSelfHandleBean())) {
			JasperParamCustomService paramService = (JasperParamCustomService) WebAppUtil.getBean(sysReport.getSelfHandleBean());
			Map<String, Object> params = paramService.convert(map);
			map.putAll(params);
		}

		map.put("request", request);
		map.put("response", response);
		// 设置报表图片上下文路径
		map.put("contextPath", request.getContextPath());

		convertService.show(map);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return sysReportManager;
	}

	/**
	 * 导出报表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("export")
	public void export(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String repId=request.getParameter("repId");
		SysReport sysReport = sysReportManager.get(repId);
		String dbId=sysReport.getDsAlias();
		String json = request.getParameter("dataJson");// 前台选择参数的Json
		String exportType = request.getParameter("exportType");
		String fileType = null;
		if (exportType.equals("pagexls")) {
			fileType = "xls";
		} else {
			fileType = exportType;
		}
		String page = "0";
		if (StringUtils.isNotEmpty(json)) {
			JSONObject jsonObject = JSONObject.parseObject(json);//fromObject(json);
			String tempPage=jsonObject.getString("page");
			if(StringUtils.isNotBlank(tempPage)){
				page=tempPage;
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("repId", repId);
		map.put("dbId", dbId);
		map.put("json", json);
		map.put("page", page);
		map.put("bean", sysReport.getSelfHandleBean());
		map.put("request", request);
		map.put("response", response);
		// 设置报表图片上下文路径
		map.put("contextPath", request.getContextPath());

		if (StringUtils.isNotEmpty(sysReport.getSelfHandleBean())) {
			JasperParamCustomService paramService = (JasperParamCustomService) WebAppUtil.getBean(sysReport.getSelfHandleBean());

			Map<String, Object> params = paramService.convert(map);

			map.putAll(params);
		}

		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmm");
		response.setHeader("Content-Disposition", "attachment;filename=" + sdf.format(curDate) + "." + fileType);
		convertService.export(exportType, map);

	}

	/**
	 * 查看明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("publish")
	public ModelAndView publish(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		SysReport sysReport = null;
		if (StringUtils.isNotEmpty(pkId)) {
			sysReport = sysReportManager.get(pkId);
		} else {
			sysReport = new SysReport();
		}
		return getPathView(request).addObject("sysReport", sysReport);
	}
}
