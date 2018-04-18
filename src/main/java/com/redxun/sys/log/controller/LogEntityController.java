
package com.redxun.sys.log.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.log.entity.LogEntity;
import com.redxun.sys.log.manager.LogEntityManager;

/**
 * 日志实体控制器
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/sys/log/logEntity/")
public class LogEntityController extends BaseMybatisListController{
    @Resource
    LogEntityManager logEntityManager;
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=RequestUtil.getString(request, "ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                logEntityManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除!");
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
        String pkId=RequestUtil.getString(request, "pkId");
        LogEntity logEntity=null;
        if(StringUtils.isNotEmpty(pkId)){
           logEntity=logEntityManager.get(pkId);
        }else{
        	logEntity=new LogEntity();
        }
        return getPathView(request).addObject("logEntity",logEntity);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	LogEntity logEntity=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		logEntity=logEntityManager.get(pkId);
    		if("true".equals(forCopy)){
    			logEntity.setId(null);
    		}
    	}else{
    		logEntity=new LogEntity();
    	}
    	return getPathView(request).addObject("logEntity",logEntity);
    }
    @RequestMapping("myList")
    @ResponseBody
    public JsonPageResult<LogEntity> myList(HttpServletRequest request,HttpServletResponse response){
    	String tenantId=ContextUtil.getCurrentTenantId();
    	QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
    	queryFilter.addFieldParam("TENANT_ID_", tenantId);
    	List<LogEntity> logEntities=logEntityManager.getAll(queryFilter);
    	JsonPageResult<LogEntity> jsonPageResult=new JsonPageResult<LogEntity>(logEntities, queryFilter.getPage().getTotalItems());
    	return jsonPageResult;
    }
    
    @RequestMapping("getAllList")
    @ResponseBody
    public JsonPageResult<LogEntity> getAllList(HttpServletRequest request,HttpServletResponse response){
    	Boolean withTenantId=RequestUtil.getBoolean(request, "withTenantId");
    	QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
    	String tenantId=ContextUtil.getCurrentTenantId();
    	if(withTenantId){
    		queryFilter.addFieldParam("TENANT_ID_", tenantId);
    	}
    	queryFilter.addFieldParam("ACTION_", "登陆");
    	List<LogEntity> logEntities=logEntityManager.getAll(queryFilter);
    	JsonPageResult<LogEntity> jsonPageResult=new JsonPageResult<LogEntity>(logEntities, queryFilter.getPage().getTotalItems());
    	return jsonPageResult;
    }
    

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return logEntityManager;
	}

}
