package com.redxun.oa.personal.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.bpm.activiti.service.ActRepService;
import com.redxun.bpm.bm.manager.BpmFormAttManager;
import com.redxun.bpm.bm.manager.BpmFormModelManager;
import com.redxun.bpm.core.entity.BpmSolution;
import com.redxun.bpm.core.manager.BpmDefManager;
import com.redxun.bpm.core.manager.BpmSolFmManager;
import com.redxun.bpm.core.manager.BpmSolFvManager;
import com.redxun.bpm.core.manager.BpmSolVarManager;
import com.redxun.bpm.core.manager.BpmSolutionManager;
import com.redxun.bpm.form.manager.BpmFormViewManager;
import com.redxun.core.json.IJson;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.QueryParam;
import com.redxun.core.query.SortParam;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.core.manager.SysTreeManager;

/**
 * 申请业务流程
 * 
 * @author csx
 */
@Controller
@RequestMapping("/oa/personal/ApplybpmSolution/")
public class ApplyBpmSolutionController extends TenantListController {
	@Resource
	BpmSolutionManager bpmSolutionManager;
	@Resource
	BpmDefManager bpmDefManager;
	@Resource
	BpmSolFmManager bpmSolFmManager;
	@Resource
	BpmFormModelManager bpmFormModelManager;
	@Resource
	IJson iJson;
	@Resource
	BpmFormViewManager bpmFormViewManager;
	@Resource
	ActRepService actRepService;
	@Resource
	BpmSolFvManager bpmSolFvManager;
	@Resource
	SysTreeManager sysTreeManager;
	@Resource
	BpmFormAttManager bpmFormAttManager;
	@Resource
	BpmSolVarManager bpmSolVarManager;

	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		QueryFilter queryFilter = super.getQueryFilter(request);
		// 查找分类下的模型
		String treeId = request.getParameter("treeId");
		if (StringUtils.isNotEmpty(treeId)) {
			SysTree sysTree = sysTreeManager.get(treeId);
			if (sysTree != null) {
				queryFilter.getFieldLogic().getCommands().add(new QueryParam("sysTree.path", QueryParam.OP_LEFT_LIKE, sysTree.getPath()));
			}
		}
		queryFilter.getFieldLogic().getCommands().add(new QueryParam("status", QueryParam.OP_EQUAL, "DEPLOYED"));
		queryFilter.addSortParam("createTime", SortParam.SORT_DESC);
		return queryFilter;
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
		BpmSolution bpmSolution = null;
		if (StringUtils.isNotBlank(pkId)) {
			bpmSolution = bpmSolutionManager.get(pkId);
		} else {
			bpmSolution = new BpmSolution();
		}
		return getPathView(request).addObject("bpmSolution", bpmSolution);
	}

	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		// 复制添加
		String forCopy = request.getParameter("forCopy");
		BpmSolution bpmSolution = null;
		if (StringUtils.isNotEmpty(pkId)) {
			bpmSolution = bpmSolutionManager.get(pkId);
			if ("true".equals(forCopy)) {
				bpmSolution.setSolId(null);
			}
		} else {
			bpmSolution = new BpmSolution();
		}
		return getPathView(request).addObject("bpmSolution", bpmSolution);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return bpmSolutionManager;
	}
}
