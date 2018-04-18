package com.redxun.oa.pro.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.oa.pro.entity.ProWorkAtt;
import com.redxun.oa.pro.manager.PlanTaskManager;
import com.redxun.oa.pro.manager.ProWorkAttManager;
import com.redxun.oa.pro.manager.ProjectManager;
import com.redxun.oa.pro.manager.ReqMgrManager;

/**
 * 关注管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/oa/pro/proWorkAtt/")
public class ProWorkAttController extends BaseListController{
    @Resource
    ProWorkAttManager proWorkAttManager;
    @Resource
   ProjectManager projectManager;
    @Resource
    ReqMgrManager reqMgrManager;
    @Resource
    PlanTaskManager planTaskManager;
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
	/**
	 * 删除关注
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                proWorkAttManager.delete(id);
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
        ProWorkAtt proWorkAtt=null;
        String name=null;
    if(StringUtils.isNotEmpty(pkId)){
           proWorkAtt=proWorkAttManager.get(pkId);
           String objId=proWorkAtt.getTypePk();
           String Type=proWorkAttManager.get(pkId).getType();
           if("PROJECT".equals(Type)){
       		if(projectManager.get(objId)!=null){
       			name=projectManager.get(objId).getName();
       		}else{name="[缺失]";}
       		
       	}else if("PLAN".equals(Type)){
       		if(planTaskManager.get(objId)!=null){
       			name=planTaskManager.get(objId).getSubject();
       		}else{name="[缺失]";}
       		
       	}else if("REQ".equals(Type)){
       		if(reqMgrManager.get(objId)!=null){
       			name=reqMgrManager.get(objId).getSubject();
       		}else{name="[缺失]";}
       	}
           
           
   }else{
        	proWorkAtt=new ProWorkAtt();
        }
        return getPathView(request).addObject("proWorkAtt",proWorkAtt).addObject("name", name);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	ProWorkAtt proWorkAtt=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		proWorkAtt=proWorkAttManager.get(pkId);
    		if("true".equals(forCopy)){
    			proWorkAtt.setAttId(null);
    		}
    	}else{
    		proWorkAtt=new ProWorkAtt();
    	}
    	return getPathView(request).addObject("proWorkAtt",proWorkAtt);
    }
    
    
    /**
     * 创建关注 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("payAttention")
    @ResponseBody
    public JSONObject payAttention(HttpServletRequest request,HttpServletResponse response){
    	String type=request.getParameter("type");
    	String typePk=request.getParameter("typePk");
    	
    	JSONObject object=new JSONObject();
    	ProWorkAtt proWorkAtt=new ProWorkAtt();
    	proWorkAtt.setUserId(ContextUtil.getCurrentUserId());
    	proWorkAtt.setTenantId(ContextUtil.getCurrentTenantId());
    	proWorkAtt.setCreateBy(ContextUtil.getCurrentUserId());
    	proWorkAtt.setCreateTime(new Date());
    	proWorkAtt.setAttTime(new Date());
    	proWorkAtt.setType(type);
    	proWorkAtt.setTypePk(typePk);
    	proWorkAtt.setNoticeType("ShortMsg");
    	proWorkAttManager.create(proWorkAtt);
    	object.put("success", true);
    	return object;
    }

    /**
     * 检查关注是否重复
     */
    @RequestMapping("checkAttention")
    @ResponseBody
    
    public JSONObject checkaAttention(HttpServletRequest request,HttpServletResponse response){
    	String typePk=request.getParameter("typePk");
    	JSONObject object=new JSONObject();
    	QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
    	queryFilter.addFieldParam("typePk", typePk);
    	queryFilter.addFieldParam("userId", ContextUtil.getCurrentUserId());
    	List<ProWorkAtt> list=proWorkAttManager.getAll(queryFilter);
    	if(list.size()<=0){
    		object.put("success", true);
    	}else{
    		object.put("success", false);
    	}
    	return object;
    }
    
    
    
    @RequestMapping("getAttName")
    @ResponseBody
    public JSONObject getAttName(HttpServletRequest request,HttpServletResponse response){
    	String typePk=request.getParameter("typePk");
    	String attId=request.getParameter("pkId");
        String name="";
        String Type=proWorkAttManager.get(attId).getType();
    	if("PROJECT".equals(Type)){
    		if(projectManager.get(typePk)!=null){
    			name=projectManager.get(typePk).getName();
    		}else{name="[缺失]";}
    		
    	}else if("PLAN".equals(Type)){
    		if(planTaskManager.get(typePk)!=null){
    			name=planTaskManager.get(typePk).getSubject();
    		}else{name="[缺失]";}
    		
    	}else if("REQ".equals(Type)){
    		if(reqMgrManager.get(typePk)!=null){
    			name=reqMgrManager.get(typePk).getSubject();
    		}else{name="[缺失]";}
    	}
    	JSONObject object=new JSONObject();
    	object.put("name", name);
    	
    	return object;
    }
    
	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return proWorkAttManager;
	}

}
