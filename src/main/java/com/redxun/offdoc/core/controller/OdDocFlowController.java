package com.redxun.offdoc.core.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.bpm.core.manager.BpmSolutionManager;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.SqlQueryFilter;
import com.redxun.offdoc.core.entity.OdDocFlow;
import com.redxun.offdoc.core.manager.OdDocFlowManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.manager.OsDimensionManager;
import com.redxun.sys.org.manager.OsGroupManager;

/**
 * [OdDocFlow]管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/offdoc/core/odDocFlow/")
public class OdDocFlowController extends BaseListController{
    @Resource
    OdDocFlowManager odDocFlowManager;
    @Resource
    OsGroupManager osGroupManager;
    @Resource
    BpmSolutionManager bpmSolutionManager;
    @Resource
    OsDimensionManager osDimensionManager;
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                odDocFlowManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除！");
    }
    
    /**
     * 查看明细
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("get")
    public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String pkId=request.getParameter("pkId");
        OdDocFlow odDocFlow=null;
        if(StringUtils.isNotEmpty(pkId)){
           odDocFlow=odDocFlowManager.get(pkId);
        }else{
        	odDocFlow=new OdDocFlow();
        }
        return getPathView(request).addObject("odDocFlow",odDocFlow);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	OdDocFlow odDocFlow=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		odDocFlow=odDocFlowManager.get(pkId);
    	}else{
    		odDocFlow=new OdDocFlow();
    	}
    	return getPathView(request).addObject("odDocFlow",odDocFlow);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return odDocFlowManager;
	}

	@RequestMapping("depFlowSet")
	public ModelAndView depFlowSet(HttpServletRequest request,HttpServletResponse response){
		OsGroup mainGroup=osGroupManager.getMainDeps(ContextUtil.getCurrentUserId());
		ModelAndView mv=new ModelAndView("offdoc/core/odDocFlowEdit.jsp");
		if(mainGroup!=null){
			String deptId=mainGroup.getGroupId();
			String deptName=mainGroup.getName();
			mv.addObject("depId", deptId).addObject("depName", deptName);
			OdDocFlow odDocFlow=odDocFlowManager.getByGroupId(deptId);
			if(odDocFlow!=null)
			mv.addObject(odDocFlow);
			
		}
		return mv;
		
	}
	
	@RequestMapping("globalFlowSet")
	public ModelAndView globalFlowSet(HttpServletRequest request,HttpServletResponse response){
		
		return null;
		
	}
	/**
	 * 返回这个tenantId所有的部门的收发文流程
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("listSol")
	@ResponseBody
	public List<OdDocFlow> listSol(HttpServletRequest request,HttpServletResponse response){
		String tenantId=ContextUtil.getCurrentTenantId();
		String dimId=osDimensionManager.getByDimKeyTenantId("_ADMIN", tenantId).getDimId();
		SqlQueryFilter sqlQueryFilter=QueryFilterBuilder.createSqlQueryFilter(request);
		List<OdDocFlow> list= odDocFlowManager.getOdDocFlowAndGroup(dimId, tenantId, sqlQueryFilter);
		return list;
	}
	
	/**
     * 修改发文流程数据D=dispatch
     * @param request
     * @param solution
     * @param schemeId
     * @return
     */
    @RequestMapping(value = "saveDOne", method = RequestMethod.POST)
    @ResponseBody
    public String saveDOne(HttpServletRequest request,HttpServletResponse response) {
        String msg = null;
        String schemeId=request.getParameter("schemeId");
        String solId=request.getParameter("solution");
        String depId=request.getParameter("depId");
        OdDocFlow odDocFlow =new OdDocFlow();
        if (StringUtils.isEmpty(schemeId)) {
            odDocFlow.setSchemeId(idGenerator.getSID());
            odDocFlow.setSendSolId(solId);
            odDocFlow.setSendSolName(bpmSolutionManager.get(solId).getName());
            odDocFlow.setDepId(depId);
            odDocFlowManager.create(odDocFlow);
        }
        else{
        	odDocFlow=odDocFlowManager.get(schemeId);
            odDocFlow.setSendSolId(solId);
            odDocFlow.setSendSolName(bpmSolutionManager.get(solId).getName());
            //odDocFlow.setSendSolName();
                  odDocFlowManager.update(odDocFlow);
        }
       
        
        //saveOpMessage(request, msg);
        return odDocFlow.getSchemeId();
    }
    
    /**
     * 修改收文流程数据I=incoming
     * @param request
     * @param solution
     * @param schemeId
     * @return
     */
    @RequestMapping(value = "saveIOne", method = RequestMethod.POST)
    @ResponseBody
    public String saveIOne(HttpServletRequest request,HttpServletResponse response) {
    	String msg = null;
        String schemeId=request.getParameter("schemeId");
        String solId=request.getParameter("solution");
        String depId=request.getParameter("depId");
        OdDocFlow odDocFlow =new OdDocFlow();
        if (StringUtils.isEmpty(schemeId)) {
            odDocFlow.setSchemeId(idGenerator.getSID());
            odDocFlow.setRecSolId(solId);
            odDocFlow.setRecSolName(bpmSolutionManager.get(solId).getName());
            odDocFlow.setDepId(depId);
            odDocFlowManager.create(odDocFlow);
        }
        else{
        	odDocFlow=odDocFlowManager.get(schemeId);
            odDocFlow.setRecSolId(solId);
            odDocFlow.setRecSolName(bpmSolutionManager.get(solId).getName());
                  odDocFlowManager.update(odDocFlow);
        }
       
        
        //saveOpMessage(request, msg);
        return odDocFlow.getSchemeId();
    }
	
}
