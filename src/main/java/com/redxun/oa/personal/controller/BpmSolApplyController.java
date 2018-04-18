package com.redxun.oa.personal.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redxun.bpm.core.entity.BpmSolution;
import com.redxun.bpm.core.manager.BpmSolutionManager;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.util.StringUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.core.manager.SysTreeManager;
import com.redxun.sys.org.manager.OsGroupManager;

/**
 * 流程解决方案申请
 * @author csx
 *
 */
@Controller
@RequestMapping("/oa/personal/bpmSolApply/")
public class BpmSolApplyController extends TenantListController{
	@Resource
	BpmSolutionManager bpmSolutionManager;
	@Resource
	OsGroupManager osGroupManager;
	@Resource
	SysTreeManager sysTreeManager;
	@Override
	public BaseManager getBaseManager() {
		return bpmSolutionManager;
	}
	
	
	/**
	 * 获得我的流程解决方案，多表组合查询，并且进行权限过滤
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("mySolutions")
	@ResponseBody
	public JsonPageResult<BpmSolution> mySolutions(HttpServletRequest request,HttpServletResponse response) throws Exception{
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		String treeId=RequestUtil.getString(request, "treeId");
		if(StringUtil.isNotEmpty(treeId)){
			SysTree sysTree= sysTreeManager.get(treeId);
			if(sysTree!=null){
				String path=sysTree.getPath();
				queryFilter.addLeftLikeFieldParam("TREE_PATH_", path);
			}
		}

		List<BpmSolution> bpmSolutions=bpmSolutionManager.getSolutions(queryFilter,false);
		return new JsonPageResult<BpmSolution>(bpmSolutions,queryFilter.getPage().getTotalItems());
	}
	
	
	
	
}
